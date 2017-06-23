package brilliant.elf.content

import brilliant.elf.content.ELF_Constant.DT_RelType.R_ARM_ABS32
import brilliant.elf.content.ELF_Constant.DT_RelType.R_ARM_COPY
import brilliant.elf.content.ELF_Constant.DT_RelType.R_ARM_GLOB_DAT
import brilliant.elf.content.ELF_Constant.DT_RelType.R_ARM_IRELATIVE
import brilliant.elf.content.ELF_Constant.DT_RelType.R_ARM_JUMP_SLOT
import brilliant.elf.content.ELF_Constant.DT_RelType.R_ARM_REL32
import brilliant.elf.content.ELF_Constant.DT_RelType.R_ARM_RELATIVE
import brilliant.elf.content.ELF_Constant.ELFUnit.ELF32_Addr
import brilliant.elf.content.ELF_Constant.ELFUnit.uint32_t
import brilliant.elf.content.ELF_Constant.SHN_Info.SHN_UNDEF
import brilliant.elf.content.ELF_Constant.STB_Info.STB_GLOBAL
import brilliant.elf.content.ELF_Constant.STB_Info.STB_LOCAL
import brilliant.elf.content.ELF_Constant.STB_Info.STB_WEAK
import brilliant.elf.content.ELF_Definition.ELF_R_SYM
import brilliant.elf.content.ELF_Definition.ELF_R_TYPE
import brilliant.elf.content.ELF_Definition.ELF_ST_BIND
import brilliant.elf.content.ELF_Dynamic.Elf_Sym
import brilliant.elf.content.ELF_ProgramHeader.ELF_Phdr
import brilliant.elf.content.ELF_Relocate.Elf_rel
import brilliant.elf.util.ByteUtil
import brilliant.elf.util.Log
import brilliant.elf.vm.OS
import brilliant.elf.vm.OS.Companion.MAP_FIXED
import brilliant.elf.vm.OS.Companion.PAGE_END
import brilliant.elf.vm.OS.Companion.PAGE_START
import java.io.File
import java.io.RandomAccessFile
import java.util.*
import javax.activation.UnsupportedDataTypeException

/**
 * Construct a new Elf decoder which only support arm/32bit

 * @author Yoogurt
 */
class ELF @Throws(Exception::class)
private constructor(file: File, do_load: Boolean) {

    private class ReserveLoadableSegment {
        internal var min_address: Long = 0
        internal var max_address: Long = 0
    }

    private class MapEntry {
        internal var seg_start: Long = 0
        internal var seg_end: Long = 0

        internal var seg_page_start: Long = 0
        internal var seg_page_end: Long = 0

        internal var seg_file_end: Long = 0

        // file offset
        internal var file_start: Long = 0
        internal var file_end: Long = 0

        internal var file_page_start: Long = 0
        internal var file_length: Long = 0
    }

    /* for disassembler */
    class FakeFuncImpl internal constructor(var function: String, var address: Int)

    private val MAP = HashMap<ELF_Phdr, MapEntry>()

    private var mEnable = false

    /* full name of this file */
    private val name: String

    internal lateinit var elf_header: ELF_Header
        private set
    internal lateinit var elf_phdr: ELF_ProgramHeader
        private set
    internal lateinit var elf_dynamic: ELF_Dynamic
        private set
    /* current elf needs file */
    private var needed: Array<ELF?>? = null

    /* elf hash */
    private var nbucket: Int = 0
    private var nchain: Int = 0
    private var bucket: Int = 0
    private var chain: Int = 0
    /* elf hash */

    /* gnu hash */
    private var is_gnu_hash = false

    private var gnu_hash: Long = 0
    private var gnu_nbucket: Int = 0
    private var gnu_maskwords: Int = 0
    private var gnu_shift2: Int = 0
    private var gnu_bloom_filter: Int = 0
    private var gnu_bucket: Int = 0
    private var gnu_chain: Int = 0
    /* gnu hash */

    /* symbol table */
    private var symtab: Int = 0

    /* string table */
    private var strtab: Int = 0

    private var pltgot: Int = 0

    /* initialized function */
    private var init_func: Int = 0
    private var hasInitFunc = false

    private var init_array: Int = 0
    private var init_array_sz: Int = 0
    private var hasInitArray = false
    /* initialized function */

    /* finished function */
    private var fini_func: Int = 0
    private var hasFiniFunc = false

    private var fini_array: Int = 0
    private var fini_array_sz: Int = 0
    private var hasFiniArray = false
    /* initialize function */

    /* elf base address , usually be 0 */
    private var elf_base: Int = 0

    /* elf load offset */
    private var elf_load_bias: Int = 0

    /* takes space in memory */
    private var elf_size: Int = 0

    private var hasDT_SYMBOLIC: Boolean = false

    val isLittleEndian: Boolean
        get() = elf_header.isLittleEndian

    @Throws(Exception::class)
    private constructor(file: String, do_load: Boolean) : this(File(file), do_load) {
        println("\n" + name + " loaded ! base : " + Integer.toHexString(elf_base) + " to : "
                + Integer.toHexString(elf_base + elf_size) + "  load_bias : " + Integer.toHexString(elf_load_bias)
                + "\n\n\n")
    }

    init {

        val raf = RandomAccessFile(file, "r")
        name = file.name

        try {
            findLibrary(raf, do_load)
        } catch (e: Throwable) {
            throw RuntimeException(e)
        }

    }

    @Throws(Exception::class)
    private fun findLibrary(raf: RandomAccessFile, do_load: Boolean) {

        elf_header = ELF_Header(raf)
        /*
		 * big endian will be supported someday rather than now
		 */
        if (!elf_header.isLittleEndian)
            throw UnsupportedDataTypeException("ELFDecoder don't support big endian architecture")

        /*
		 * 64bit will be supported someday rather than now
		 */
        if (!elf_header.is32Bit)
            throw UnsupportedDataTypeException("ELFDecoder don't support except 32 bit architecture")

        elf_phdr = ELF_ProgramHeader(raf, elf_header, false)

        if (!do_load)
            ELF_SectionHeader(raf, elf_header)
        else {
            reserveAddressSpace()
            loadSegments(raf)
        }

        elf_dynamic = ELF_Dynamic(raf, elf_phdr.dynamicSegment, elf_load_bias)

        if (do_load) {
            link_image()

            callConstructors()
            callArrays()
        }
    }

    @Throws(ELFDecodeException::class)
    private fun reserveAddressSpace() {

        val allLoadableSegment = elf_phdr.allLoadableSegment

        val r = phdr_table_get_load_size(allLoadableSegment)

        elf_base = OS.mainImage.mmap(0, (r.max_address - r.min_address).toInt(), 0, null, 0)
        if (elf_base < 0)
            throw RuntimeException("mmap fail while reservse space")

        elf_load_bias = (elf_base - r.min_address).toInt() /*
									 * r.min_address is supposed to be 0 , in
									 * face , it is
									 */
        Log.e("load_bias " + elf_load_bias + "  elf_base " + elf_base + " r.min_address " + r.min_address)
        elf_size = (r.max_address + elf_load_bias - elf_base).toInt()
    }

    @Throws(ELFDecodeException::class)
    private fun phdr_table_get_load_size(loadableSegment: List<ELF_Phdr>): ReserveLoadableSegment {

        var minAddress = Integer.MAX_VALUE
        var maxAddress = Integer.MIN_VALUE

        for (phdr in loadableSegment) {/*
												 * if (phdr->p_vaddr <
												 * min_vaddr) { min_vaddr =
												 * phdr->p_vaddr; // min virtual
												 * address } if (phdr->p_vaddr +
												 * phdr->p_memsz > max_vaddr) {
												 * max_vaddr = phdr->p_vaddr +
												 * phdr->p_memsz; // max virtual
												 * address }
												 */

            val address = ByteUtil.bytes2Int32(phdr.p_vaddr)
            val memsize = ByteUtil.bytes2Int32(phdr.p_memsz)
            if (address < minAddress)
                minAddress = address

            if (address + memsize > maxAddress)
                maxAddress = address + memsize

        }

        val r = ReserveLoadableSegment()
        r.min_address = PAGE_START(minAddress.toLong())
        r.max_address = PAGE_END(maxAddress.toLong())

        Log.e("min_address 0x" + java.lang.Long.toHexString(r.min_address))
        Log.e("max_address 0x" + java.lang.Long.toHexString(r.max_address))

        if (minAddress > maxAddress || maxAddress < 0 || minAddress < 0)
            throw ELFDecodeException("can not parse phdr address")

        return r
    }

    private fun loadSegments(raf: RandomAccessFile) {

        val phs = elf_phdr.allLoadableSegment
        for (ph in phs) {

            val m = MapEntry()

            m.seg_start = ByteUtil.bytes2Int64(ph.p_vaddr) + elf_load_bias
            m.seg_end = m.seg_start + ByteUtil.bytes2Int64(ph.p_memsz)

            m.seg_page_start = PAGE_START(m.seg_start)
            m.seg_page_end = PAGE_END(m.seg_end)

            m.seg_file_end = m.seg_start + ByteUtil.bytes2Int64(ph.p_filesz)

            // file offset
            m.file_start = ByteUtil.bytes2Int64(ph.p_offset)
            m.file_end = m.file_start + ByteUtil.bytes2Int64(ph.p_filesz)

            m.file_page_start = PAGE_START(m.file_start)
            m.file_length = m.file_end - m.file_page_start

            if (0 > OS.mainImage.mmap(m.seg_page_start.toInt(), m.file_length.toInt(), MAP_FIXED, raf,
                    m.file_page_start))
                throw RuntimeException("Unable to mmap segment : " + ph.toString())

            MAP.put(ph, m)
        }

        /*
		 * zero full the remain space , in java it generate automatic and we
		 * don't check elf_phdr is in memory or not
		 */

    }

    private fun link_image() {

        if (elf_dynamic.dT_HASH > 0) {
            /* extract some useful informations */
            nbucket = ByteUtil.bytes2Int32(OS.mainImage.memory,
                    elf_dynamic.dT_HASH + elf_load_bias + uint32_t * 0, uint32_t, elf_header.isLittleEndian) // value
            nchain = ByteUtil.bytes2Int32(OS.mainImage.memory,
                    elf_dynamic.dT_HASH + elf_load_bias + uint32_t * 1, uint32_t, elf_header.isLittleEndian) // value
            bucket = elf_dynamic.dT_HASH + elf_load_bias + 8 // pointer
            chain = elf_dynamic.dT_HASH + elf_load_bias + 8 + (nbucket shl 2) // pointer
        }

        if (elf_dynamic.dT_GNU_HASH > 0) {
            gnu_nbucket = ByteUtil.bytes2Int32(OS.mainImage.memory,
                    elf_dynamic.dT_GNU_HASH + elf_load_bias + uint32_t * 0, uint32_t, isLittleEndian)
            gnu_maskwords = ByteUtil.bytes2Int32(OS.mainImage.memory,
                    elf_dynamic.dT_GNU_HASH + elf_load_bias + uint32_t * 2, uint32_t, isLittleEndian)
            gnu_shift2 = ByteUtil.bytes2Int32(OS.mainImage.memory,
                    elf_dynamic.dT_GNU_HASH + elf_load_bias + uint32_t * 3, uint32_t, isLittleEndian)
            gnu_bloom_filter = elf_dynamic.dT_GNU_HASH + elf_load_bias + 16 // pointer
            gnu_bucket = gnu_bloom_filter + gnu_maskwords // pointer

            gnu_chain = gnu_bucket + gnu_nbucket - ByteUtil.bytes2Int32(OS.mainImage.memory,
                    elf_dynamic.dT_GNU_HASH + elf_load_bias + uint32_t * 1, uint32_t, isLittleEndian) // pointer

            if (!ByteUtil.powerof2(gnu_maskwords))
                throw IllegalArgumentException("invalid maskwords for gnu_hash = 0x"
                        + Integer.toHexString(gnu_maskwords) + " , in \"" + name + "\" expecting power to two")

            --gnu_maskwords

            gnu_hash = gnu_hash(name)
            is_gnu_hash = true
        }

        symtab = elf_dynamic.dT_SYMTAB + elf_load_bias // pointer

        strtab = elf_dynamic.dT_STRTAB + elf_load_bias // pointer

        pltgot = elf_dynamic.dT_PLTGOT + elf_load_bias

        hasDT_SYMBOLIC = elf_dynamic.dT_SYMBOLIC

        if (elf_dynamic.dT_INIT != 0) {
            init_func = elf_dynamic.dT_INIT + elf_load_bias // pointer
            hasInitFunc = true
        }

        if (elf_dynamic.dT_INIT_ARRAY != 0) {
            init_array = elf_dynamic.dT_INIT_ARRAY + elf_load_bias // pointer
            init_array_sz = elf_dynamic.dT_INIT_ARRAYSZ / ELF32_Addr
            hasInitArray = true
        }

        if (elf_dynamic.dT_FINI != 0) {
            fini_func = elf_dynamic.dT_FINI + elf_load_bias // pointer
            hasFiniFunc = true
        }
        if (elf_dynamic.dT_FINI_ARRAY != 0) {
            fini_array = elf_dynamic.dT_FINI_ARRAY + elf_load_bias// pointer
            fini_array_sz = elf_dynamic.dT_FINI_ARRAYSZ / ELF32_Addr
            hasFiniArray = false
        }

        /* verify some parameters */
        if (elf_dynamic.dT_HASH <= 0 && elf_dynamic.dT_GNU_HASH <= 0)
            throw RuntimeException("empty/mission DT_HASH and DT_GNU_HASH , new hash type in the future ?")
        if (strtab == 0)
            throw RuntimeException("empty/missing DT_STRTAB")
        if (symtab == 0)
            throw RuntimeException("empty/missing DT_SYMTAB")

        if (!forDisassmebler) {

            val needed = elf_dynamic.needLibraryName
            this.needed = arrayOfNulls<ELF>(needed.size)

            /* Link elf requires libraries */
            var count = 0
            for (name in needed) {
                var elf: ELF? = null

                for (env in ENV) {
                    elf = dlopen(env + name)
                    if (elf != null)
                        break
                }
                if (elf == null)
                    throw RuntimeException("Unable to load depend library " + name)

                this.needed!![count++] = elf
            }

            soinfo_relocate()

        } else { // extension for disassembler , we don't import necessary
            // library

            mFakeFuncImpl = allocFakeGotImpl(extractFakeFunction())
            fake_soinfo_relocate()

        }

    }

    private var mFakeFuncImpl: List<FakeFuncImpl>? = null

    private fun allocFakeGotImpl(functions: List<String>): List<FakeFuncImpl>? {

        var start: Int = OS.mainImage.mmap(0, functions.size shl 2, 0, null, 0)

        /* each function takes 4B */
        if (start < 0)
            return null

        if (PAGE_START(start.toLong()) != start.toLong())
            throw RuntimeException("FATAL : mmap() in an unaligned address")

        val impl = LinkedList<FakeFuncImpl>()

        for (f in functions) {
            impl.add(FakeFuncImpl(f, start))
            start += 4
        }
        return impl
    }

    private fun soinfo_fake_lookup(name: String): Int {

        for (f in mFakeFuncImpl!!)
            if (f.function == name)
                return f.address

        throw RuntimeException("Unable to find fake function")
    }

    private fun extractFakeFunction(): List<String> {

        val rels = elf_dynamic.relocateSections

        val fake_relocation = ArrayList<String>()

        rels.forEach {
            r ->
            val entries = r.relocateEntry
            entries.forEach {
                rel ->
                /*
				 * ElfW(Addr) reloc = static_cast<ElfW(Addr)>(rel->r_offset +
				 * load_bias);
				 */
                val sym = ELF_R_SYM(rel!!.r_info)
                val type = ELF_R_TYPE(rel.r_info).toInt()
                Log.e("strtab " + strtab)
                Log.e("sym " + sym)
                Log.e("symtab " + symtab)

                val sym_name = ByteUtil.getStringFromMemory(ByteUtil.bytes2Int32(
                        Elf_Sym.reinterpret_cast(OS.mainImage.memory, symtab + Elf_Sym.size() * sym).st_name) + strtab, OS.mainImage)

                if (type == 0)
                    return@forEach

                if (sym != 0) {
                    /* we need to construct a fake function to fill GOT */
                    fake_relocation.add(sym_name)
                }
            }
        }
        return fake_relocation
    }

    /**
     * what is relocation ? relocation fix a pointer which point at somewhere in
     * file , but we need to let it point to memory correct
     */
    fun fake_soinfo_relocate() {

        val rels = elf_dynamic.relocateSections

        rels.forEach {
            r ->
            val entries = r.relocateEntry
            for (rel in entries) {
                /*
				 * ElfW(Addr) reloc = static_cast<ElfW(Addr)>(rel->r_offset +
				 * load_bias);
				 */
                val reloc = ByteUtil.bytes2Int32(rel!!.r_offset) + elf_load_bias
                val sym = ELF_R_SYM(rel.r_info)
                val type = ELF_R_TYPE(rel.r_info).toInt()
                val addend = get_addend(rel, reloc)

                val sym_name = ByteUtil.getStringFromMemory(ByteUtil.bytes2Int32(
                        Elf_Sym.reinterpret_cast(OS.mainImage.memory, symtab + Elf_Sym.size() * sym).st_name) + strtab, OS.mainImage)

                var sym_address = 0

                if (type == 0)
                    continue

                if (sym != 0) {
                    sym_address = soinfo_fake_lookup(sym_name)
                    Log.e("Found fake Sym : " + sym_name + " at : " + Integer.toHexString(sym_address))
                }

                /*---------------------------------------------------------------------------------------*/

                when (type) {
                    R_ARM_GLOB_DAT -> {

                        Log.e("name : " + sym_name + " R_GENERIC_GLB_DAT at "
                                + ByteUtil.bytes2Hex(ByteUtil.int2bytes(reloc)) + " , relocating to 0x"
                                + Integer.toHexString(sym_address) + " , previours values : "
                                + ByteUtil.bytes2Int32(OS.mainImage.memory, reloc, ELF32_Addr, true))

                        System.arraycopy(ByteUtil.int2bytes(sym_address), 0, OS.mainImage.memory, reloc,
                                ELF32_Addr)/*
										 * reinterpret_cast< Elf32_Addr
										 * *>(reloc) = sym_addr;
										 */
                    }
                    R_ARM_JUMP_SLOT -> {

                        Log.e("name : " + sym_name + " R_GENERIC_JUMP_SLOT at "
                                + ByteUtil.bytes2Hex(ByteUtil.int2bytes(reloc)) + " , relocating to 0x"
                                + Integer.toHexString(sym_address) + " , previours values : 0x" + Integer.toHexString(
                                ByteUtil.bytes2Int32(OS.mainImage.memory, reloc, ELF32_Addr, true)))

                        System.arraycopy(ByteUtil.int2bytes(sym_address), 0, OS.mainImage.memory, reloc,
                                ELF32_Addr)
                    }

                    R_ARM_ABS32 -> {
                        Log.e("name : " + sym_name + " R_ARM_ABS32 at " + ByteUtil.bytes2Hex(ByteUtil.int2bytes(reloc))
                                + " , relocating to 0x" + Integer.toHexString(sym_address + addend)
                                + " , previours values : 0x" + Integer.toHexString(
                                ByteUtil.bytes2Int32(OS.mainImage.memory, reloc, ELF32_Addr, true)))
                        System.arraycopy(ByteUtil.int2bytes(sym_address + addend), 0, OS.mainImage.memory, reloc,
                                ELF32_Addr)
                    }

                    R_ARM_REL32 -> {
                        Log.e("name : " + sym_name + " R_ARM_REL32 at " + ByteUtil.bytes2Hex(ByteUtil.int2bytes(reloc))
                                + " , relocating to 0x"
                                + Integer.toHexString(addend + sym_address - ByteUtil.bytes2Int32(rel.r_offset))
                                + " , previours values : " + Integer.toHexString(
                                ByteUtil.bytes2Int32(OS.mainImage.memory, reloc, ELF32_Addr, true)))
                        System.arraycopy(ByteUtil.int2bytes(addend + sym_address - ByteUtil.bytes2Int32(rel.r_offset)), 0,
                                OS.mainImage.memory, reloc, ELF32_Addr)
                    }
                    R_ARM_RELATIVE -> { // *reinterpret_cast<ElfW(Addr)*>(reloc)
                        // = (load_bias + addend);
                        Log.e("local sym : " + sym_name + " reloc : "
                                + ByteUtil.bytes2Hex(OS.mainImage.memory, reloc, 4) + "  become : "
                                + Integer.toHexString(elf_load_bias + addend) + " , previours values : "
                                + Integer.toHexString(
                                ByteUtil.bytes2Int32(OS.mainImage.memory, reloc, ELF32_Addr, true)))

                        System.arraycopy(ByteUtil.int2bytes(elf_load_bias + addend), 0, OS.mainImage.memory,
                                reloc, ELF32_Addr)
                        /* relocate here */

                    }

                    else -> throw RuntimeException("unknown weak reloc type" + type)
                }
                Log.e()
            }
        }
    }

    /**

     * It appears above Android 5.0

     */
    internal fun get_addend(rel: Elf_rel, reloc_addr: Int): Int {

        if (ELF_R_TYPE(rel.r_info).toInt() == R_ARM_RELATIVE || ELF_R_TYPE(rel.r_info).toInt() == R_ARM_IRELATIVE
                || ELF_R_TYPE(rel.r_info).toInt() == R_ARM_ABS32 || ELF_R_TYPE(rel.r_info).toInt() == R_ARM_REL32)
            return ByteUtil.bytes2Int32(OS.mainImage.memory, reloc_addr, ELF32_Addr,
                    elf_header.isLittleEndian) // Extract
        // reloc_addr(pointer)'s
        // value

        return 0 /*
					 * if (ELFW(R_TYPE)(rel->r_info) == R_ARM_RELATIVE ||
					 * ELFW(R_TYPE)(rel->r_info) == R_ARM_IRELATIVE) { return
					 * *reinterpret_cast<ElfW(Addr)*>(reloc_addr); } return 0;
					 */
    }

    /**
     * what is relocation ? relocation fix a pointer which point at somewhere in
     * file , but we need to let it point to memory correct
     */
    fun soinfo_relocate() {

        val rels = elf_dynamic.relocateSections

        var s: Elf_Sym?

        for (r in rels) {

            val entries = r.relocateEntry
            for (rel in entries) {
                /*
				 * ElfW(Addr) reloc = static_cast<ElfW(Addr)>(rel->r_offset +
				 * load_bias);
				 */
                val reloc = ByteUtil.bytes2Int32(rel!!.r_offset) + elf_load_bias
                val sym = ELF_R_SYM(rel.r_info)
                val type = ELF_R_TYPE(rel.r_info).toInt()
                val addend = get_addend(rel, reloc)

                val sym_name = ByteUtil.getStringFromMemory(ByteUtil.bytes2Int32(
                        Elf_Sym.reinterpret_cast(OS.mainImage.memory, symtab + Elf_Sym.size() * sym).st_name) + strtab, OS.mainImage)

                var sym_address = 0

                if (type == 0)
                    continue

                if (sym != 0) {
                    /*
					 * sym > 0 means the symbol are global , not in this file ,
					 * we will search it
					 *
					 * byte[] st_name = new byte[4];
					 * System.arraycopy(OS.getMemory(), symtab + sym
					 * Elf_Sym.size(), st_name, 0, 4); // we get st_name(index)
					 * from symtab
					 */
                    val lsi = arrayOfNulls<ELF>(1)
                    s = find_sym_by_name(this, sym_name, lsi, needed!!)
                    if (null == s) {

                        s = Elf_Sym.reinterpret_cast(OS.mainImage.memory, symtab + sym * Elf_Sym.size())

                        /*
						 * we only allow STB_WEAK which is compiling with key
						 * word "extern" when we can't found symbol
						 */

                        if (ELF_ST_BIND(s.st_info.toInt()) != STB_WEAK)
                            throw RuntimeException("cannot locate symbol \"" + sym_name + "\" referenced by \""
                                    + name + "\"... s.st_info " + Integer.toHexString(ELF_ST_BIND(s.st_info.toInt()))
                                    + " , s at " + Integer.toHexString(symtab + sym * Elf_Sym.size()))

                        when (type) {

                            R_ARM_JUMP_SLOT, R_ARM_GLOB_DAT, R_ARM_ABS32, R_ARM_RELATIVE -> {
                            }

                            R_ARM_COPY -> {

                            }
                            else -> throw RuntimeException("unknown weak reloc type " + ByteUtil.bytes2Hex(rel.r_info))
                        }
                        /*
						 * sym_address == 0 , we don't care STB_WEAK Symbol's
						 * address
						 */
                        Log.e("Got a STB_WEAK Reference : " + sym_name)

                    } else {// we got a definition

                        sym_address = lsi[0]!!.elf_load_bias + ByteUtil.bytes2Int32(s.st_value)

                        Log.e("Found Sym : " + sym_name + " at : " + Integer.toHexString(sym_address))
                    }

                }

                /*---------------------------------------------------------------------------------------*/

                when (type) {
                    R_ARM_GLOB_DAT -> {

                        Log.e("name : " + sym_name + " R_GENERIC_GLB_DAT at "
                                + ByteUtil.bytes2Hex(ByteUtil.int2bytes(reloc)) + " , relocating to 0x"
                                + Integer.toHexString(sym_address) + " , previours values : "
                                + ByteUtil.bytes2Int32(OS.mainImage.memory, reloc, ELF32_Addr, true))

                        System.arraycopy(ByteUtil.int2bytes(sym_address), 0, OS.mainImage.memory, reloc,
                                ELF32_Addr)/*
										 * reinterpret_cast< Elf32_Addr
										 * *>(reloc) = sym_addr;
										 */
                    }
                    R_ARM_JUMP_SLOT -> {

                        Log.e("name : " + sym_name + " R_GENERIC_JUMP_SLOT at "
                                + ByteUtil.bytes2Hex(ByteUtil.int2bytes(reloc)) + " , relocating to 0x"
                                + Integer.toHexString(sym_address) + " , previours values : 0x" + Integer.toHexString(
                                ByteUtil.bytes2Int32(OS.mainImage.memory, reloc, ELF32_Addr, true)))

                        System.arraycopy(ByteUtil.int2bytes(sym_address), 0, OS.mainImage.memory, reloc,
                                ELF32_Addr)
                    }

                    R_ARM_ABS32 -> {
                        Log.e("name : " + sym_name + " R_ARM_ABS32 at " + ByteUtil.bytes2Hex(ByteUtil.int2bytes(reloc))
                                + " , relocating to 0x" + Integer.toHexString(sym_address + addend)
                                + " , previours values : 0x" + Integer.toHexString(
                                ByteUtil.bytes2Int32(OS.mainImage.memory, reloc, ELF32_Addr, true)))
                        System.arraycopy(ByteUtil.int2bytes(sym_address + addend), 0, OS.mainImage.memory, reloc,
                                ELF32_Addr)
                    }

                    R_ARM_REL32 -> {
                        Log.e("name : " + sym_name + " R_ARM_REL32 at " + ByteUtil.bytes2Hex(ByteUtil.int2bytes(reloc))
                                + " , relocating to 0x"
                                + Integer.toHexString(addend + sym_address - ByteUtil.bytes2Int32(rel.r_offset))
                                + " , previours values : " + Integer.toHexString(
                                ByteUtil.bytes2Int32(OS.mainImage.memory, reloc, ELF32_Addr, true)))
                        System.arraycopy(ByteUtil.int2bytes(addend + sym_address - ByteUtil.bytes2Int32(rel.r_offset)), 0,
                                OS.mainImage.memory, reloc, ELF32_Addr)
                    }
                    R_ARM_RELATIVE -> { // *reinterpret_cast<ElfW(Addr)*>(reloc)
                        // = (load_bias + addend);
                        Log.e("local sym : " + sym_name + " reloc : "
                                + ByteUtil.bytes2Hex(OS.mainImage.memory, reloc, 4) + "  become : "
                                + Integer.toHexString(elf_load_bias + addend) + " , previours values : "
                                + Integer.toHexString(
                                ByteUtil.bytes2Int32(OS.mainImage.memory, reloc, ELF32_Addr, true)))

                        System.arraycopy(ByteUtil.int2bytes(elf_load_bias + addend), 0, OS.mainImage.memory,
                                reloc, ELF32_Addr)
                        /* relocate here */

                    }
                    else -> throw RuntimeException("unknown weak reloc type" + type)
                }
                Log.e()
            }
        }
    }

    private fun callFunction(func: Int) {
        Log.e("Calling -> " + ByteUtil.bytes2Hex(ByteUtil.int2bytes(func)))
    }

    private fun callConstructors() {

        if (hasInitFunc) {
            Log.e("Calling Constructor : " + ByteUtil.bytes2Hex(ByteUtil.int2bytes(init_func)))
            callFunction(init_func)
        } else
            Log.e("Constructor DT_INIT no found , skipping ... ")

    }

    private fun callArrays() {

        if (hasInitArray)
            for (i in 0..init_array_sz - 1)
                callFunction(init_array + (i shl 2))
        else
            Log.e("InitArray DT_INIARR no found , skipping ...")

    }

    companion object {

        val ENV = arrayOf("env/")

        /**
         * Global Loaded Object
         */
        private val glo = LinkedHashMap<String, ELF>()

        /* main executable */
        private val somain: ELF?

        /* main executable file , in android , usually is app_process , ignore */
        init {
            somain = null
        }

        var forDisassmebler = true

        fun dlopen(file: String): ELF? {

            if (glo.containsKey(file))
                return glo[file]

            try {

                val elf = ELF(file, true)
                elf.mEnable = true

                glo.put(file, elf)
                return elf

            } catch (e: Throwable) {
                e.printStackTrace()
                return null
            }

        }

        fun dlerror(): String? {
            return null
        }

        fun dlsym(elf: ELF, functionName: String): Int {

            if (!elf.mEnable || forDisassmebler)
                return -1

            val sym = soinfo_elf_lookup(elf, elf_hash(functionName), functionName)

            if (sym != null)
                if (ELF_ST_BIND(sym.st_info.toInt()) == STB_GLOBAL && ByteUtil.bytes2Int32(sym.st_shndx) != 0)
                    return ByteUtil.bytes2Int32(sym.st_value) + elf.elf_load_bias

            return 0
        }

        fun dlcolse() {

            OS.mainImage.reset()
            glo.clear()

        }

        private fun elf_hash(name: String): Long {

            val name_array = name.toCharArray()
            var h: Long = 0
            var g: Long

            for (n in name_array) {
                h = (h shl 4) + n.toLong()
                g = h and 0xf0000000
                h = h xor g
                h = h xor g.ushr(24)
            }
            return h and 0xffffffff
        }

        /*
	 * static Elf32_Sym* soinfo_elf_lookup(soinfo* si, unsigned hash, const
	 * char* name) { Elf32_Sym* symtab = si->symtab; const char* strtab =
	 * si->strtab;
	 *
	 * TRACE_TYPE(LOOKUP, "SEARCH %s in %s@0x%08x %08x %d", name, si->name,
	 * si->base, hash, hash % si->nbucket);
	 *
	 * for (unsigned n = si->bucket[hash % si->nbucket]; n != 0; n =
	 * si->chain[n]) { Elf32_Sym* s = symtab + n; if (strcmp(strtab +
	 * s->st_name, name)) continue;
	 *
	 *
	 * switch(ELF32_ST_BIND(s->st_info)){ case STB_GLOBAL: case STB_WEAK: if
	 * (s->st_shndx == SHN_UNDEF) { continue; }
	 *
	 * TRACE_TYPE(LOOKUP, "FOUND %s in %s (%08x) %d", name, si->name,
	 * s->st_value, s->st_size); return s; } }
	 *
	 * return NULL; }
	 */
        private fun soinfo_elf_lookup(si: ELF?, hash: Long, name: String): Elf_Sym? {

            if (si == null)
                return null;

            val symtab = si.symtab
            val strtab = si.strtab

            var n = ByteUtil.bytes2Int32(OS.mainImage.memory,
                    (si.bucket + hash % si.nbucket * uint32_t).toInt(), uint32_t,
                    si.elf_header.isLittleEndian)

            SymSearch@
            while (n != 0) {

                val s = Elf_Sym.reinterpret_cast(OS.mainImage.memory, symtab + n * Elf_Sym.size())

                if (name != ByteUtil.getStringFromMemory(strtab + ByteUtil.bytes2Int32(s.st_name), OS.mainImage)) {
                    n = ByteUtil.bytes2Int32(OS.mainImage.memory,
                            si.chain + n * uint32_t, uint32_t, si.elf_header.isLittleEndian)
                    continue
                } else {

                    when (ELF_ST_BIND(s.st_info.toInt())) {
                        STB_LOCAL -> Log.e("  Found  $name , but it's local")

                        STB_GLOBAL, STB_WEAK -> {
                            if (ByteUtil.bytes2Int32(s.st_shndx) == SHN_UNDEF)
                                continue@SymSearch

                            return s
                        }
                        else -> Log.e("Unknown Bind Type : " + ByteUtil.byte2Hex(s.st_info))
                    }
                }
                n = ByteUtil.bytes2Int32(OS.mainImage.memory, si.chain + n * uint32_t, uint32_t, si.elf_header.isLittleEndian)
            }
            return null
        }

        private fun find_sym_by_name(si: ELF, name: String, lsi: Array<ELF?>, needed: Array<ELF?>): Elf_Sym? {

            if (si.is_gnu_hash)
                return gnu_lookup(si, name, lsi, needed)
            else
                return elf_lookup(si, name, lsi, needed)
        }

        private fun gnu_lookup(si: ELF, name: String, lsi: Array<ELF?>, needed: Array<ELF?>): Elf_Sym? {
            return null
        }

        private fun elf_lookup(si: ELF?, name: String, lsi: Array<ELF?>, needed: Array<ELF?>): Elf_Sym? {
            val elf_hash = elf_hash(name)
            var s: Elf_Sym? = null

            do {

                if (si != null/* && somain != null */) {// somain is null

                    if (si === somain) {
                        s = soinfo_elf_lookup(si, elf_hash, name)
                        if (s != null) {
                            lsi[0] = si
                            break
                        }
                    } else {

                        if (!si.hasDT_SYMBOLIC) {
                            s = soinfo_elf_lookup(somain, elf_hash, name)
                            if (s != null) {
                                lsi[0] = somain
                                break
                            }
                        } // did not find sym in somain

                        s = soinfo_elf_lookup(si, elf_hash, name)
                        if (s != null) {
                            lsi[0] = si
                            break
                        }

                        if (si.hasDT_SYMBOLIC) {
                            s = soinfo_elf_lookup(somain, elf_hash, name)
                            if (s != null) {
                                lsi[0] = somain
                                break
                            }
                        } // did not find sym in somain
                    }

                    // ignore preload

                    for (i in needed.indices) {

                        s = soinfo_elf_lookup(needed[i], elf_hash, name)
                        if (s != null) {
                            lsi[0] = needed[i]
                            break
                        }
                    }
                }

            } while (false)
            return s
        }

        private fun gnu_hash(name: String): Long {
            var h: Long = 5381
            val array = name.toCharArray()

            for (i in array.indices) {
                h += (h shl 5) + array[i].toByte()
            }

            return h and 0xffffffffL
        }

        @Throws(Throwable::class)
        @JvmStatic fun main(args: Array<String>) {

            ELF("C:\\Users\\monitor\\Desktop\\Decomplied File\\libtest.so", true)

        }
    }

}
