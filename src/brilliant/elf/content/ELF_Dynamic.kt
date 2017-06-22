package brilliant.elf.content

import brilliant.elf.content.ELF_Constant.ELFUnit.ELF32_Addr
import brilliant.elf.content.ELF_Constant.ELFUnit.ELF32_Half
import brilliant.elf.content.ELF_Constant.ELFUnit.ELF32_Word
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_ANDROID_REL
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_ANDROID_RELSZ
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_DEBUG
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_FINI
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_FINI_ARRAY
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_FINI_ARRAYSZ
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_FLAGS
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_FLAGS_1
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_GNU_HASH
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_HASH
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_HIPROC
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_INIT
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_INIT_ARRAY
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_INIT_ARRAYSZ
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_JMPREL
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_LOPROC
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_NEEDED
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_NULL
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_PLTGOT
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_PLTREL
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_PLTRELSZ
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_REL
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_RELA
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_RELAENT
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_RELASZ
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_RELCOUNT
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_RELENT
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_RELSZ
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_RPATH
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_SONAME
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_STRSZ
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_STRTAB
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_SYMBOLIC
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_SYMENT
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_SYMTAB
import brilliant.elf.content.ELF_Constant.PT_Dynamic.DT_TEXTREL
import brilliant.elf.content.ELF_Constant.ProgramHeaderContent.PT_DYNAMIC

import java.io.IOException
import java.io.RandomAccessFile
import java.util.ArrayList

import brilliant.elf.content.ELF_ProgramHeader.ELF_Phdr
import brilliant.elf.support.CastSupport
import brilliant.elf.util.ByteUtil
import brilliant.elf.util.Log
import brilliant.elf.vm.OS

internal class ELF_Dynamic
/**
 * we decode this in file nor memory
 */
@Throws(IOException::class)
constructor(raf: RandomAccessFile, mSelf: ELF_Phdr, var elf_load_bias: Int) {

    internal class Elf_Dyn {
        lateinit var d_val: ByteArray
        lateinit var d_un: ByteArray
    }

    internal class Elf_Sym : CastSupport() {
        lateinit var st_name: ByteArray /* index into string table 4B */
        lateinit var st_value: ByteArray /* 4B */
        lateinit var st_size: ByteArray /* 4B */
        var st_info: Byte = 0 /* 1B */
        var st_other: Byte = 0 /* 1B */
        lateinit var st_shndx: ByteArray /* 2B */

        companion object {

            @JvmOverloads fun reinterpret_cast(data: ByteArray, startIndex: Int = 0): Elf_Sym {
                val thz = Elf_Sym()

                thz.st_name = ByteArray(ELF32_Word)
                thz.st_value = ByteArray(ELF32_Addr)
                thz.st_size = ByteArray(ELF32_Word)
                thz.st_shndx = ByteArray(ELF32_Half)

                System.arraycopy(data, startIndex, thz.st_name, 0, ELF32_Word)
                System.arraycopy(data, startIndex + ELF32_Word, thz.st_value, 0, ELF32_Addr)
                System.arraycopy(data, startIndex + ELF32_Word + ELF32_Addr, thz.st_size, 0, ELF32_Word)
                thz.st_info = data[startIndex + ELF32_Word + ELF32_Addr + ELF32_Word]
                thz.st_other = data[startIndex + ELF32_Word + ELF32_Addr + ELF32_Word + 1]
                System.arraycopy(data, startIndex + ELF32_Word + ELF32_Addr + ELF32_Word + 2, thz.st_shndx, 0, ELF32_Half)

                return thz
            }

            fun size(): Int {
                return 0x10
            }
        }
    }

    private val mInternalDynamics = ArrayList<Elf_Dyn>()

    var dT_STRTAB = 0
        private set

    var dT_SYMTAB = 0
        private set

    var dT_INIT = 0
        private set
    var dT_INIT_ARRAY = 0
        private set
    var dT_INIT_ARRAYSZ = 0
        private set

    var dT_FINI = 0
        private set
    var dT_FINI_ARRAY = 0
        private set
    var dT_FINI_ARRAYSZ = 0
        private set

    var dT_HASH = 0
        private set
    var dT_GNU_HASH = 0
        private set

    private val mNeededDynamicLibraryPtr = ArrayList<Int>()
    private val mNeededDynamicLibrary = ArrayList<String>()
    private val mSelf: ELF_ProgramHeader.ELF_Phdr

    private var mSoName = 0
    private var mDynamicLibraryName: String? = null

    var dT_REL = 0
        private set
    var dT_RELSZ = 0
        private set

    private val mRela = 0
    private var mRelaSz = 0

    var dT_ANDROID_REL = 0
        private set

    private var mPltRel = 0 // in linker , this call PltRel
    private var mPltRelSz = 0

    var dT_PLTGOT = 0
        private set

    private var mDT_TEXTREL = 0

    var dT_SYMBOLIC = false
        private set

    private val mRelocateSections = ArrayList<ELF_Relocate>()

    init {

        this.mSelf = mSelf

        if (mSelf.programHeader.elfHeader.is32Bit)
            loadDynamicSegment32(raf)
        else
            loadDynamicSegment64()

        loadRelocateSection(raf)
    }

    @Throws(IOException::class)
    private fun loadDynamicSegment32(raf: RandomAccessFile) {

        if (ByteUtil.bytes2Int32(mSelf.p_type) != PT_DYNAMIC)
            throw IllegalArgumentException(
                    "Attempt to decode Dynamic Segment with a not PT_DYNAMIC Program Header")

        val dynamicCount = ByteUtil.bytes2Int32(mSelf.p_filesz) / 8

        val prePosition = raf.filePointer

        raf.seek(ByteUtil.bytes2Int64(mSelf.p_offset))

        for (i in 0..dynamicCount - 1) {

            val dynamic = generateElfDynamicEntry32()

            raf.read(dynamic.d_un!!)
            raf.read(dynamic.d_val!!)

            if (!parseDynamicEntry(dynamic, raf)) {
                mInternalDynamics.add(dynamic)
                break
            }

            mInternalDynamics.add(dynamic)
        }

        loadNeedLibraryName(raf) // we load string after string table was found
        obtainSoName(raf)

        Log.e("   " + LogConstant.DIVISION_LINE)
        Log.e("   " + mInternalDynamics.size + " DT_DYNAMIC Found")
        raf.seek(prePosition)
    }

    private fun loadDynamicSegment64() {
        throw UnsupportedOperationException("not implements")
    }

    private fun generateElfDynamicEntry32(): Elf_Dyn {
        val newDynamic = Elf_Dyn()

        newDynamic.d_un = ByteArray(ELF32_Word)
        newDynamic.d_val = ByteArray(ELF32_Addr)

        return newDynamic
    }

    @Throws(IOException::class)
    private fun loadNeedLibraryName(raf: RandomAccessFile) {
        for (`val` in mNeededDynamicLibraryPtr) {

            val name = getStrTabIndexString(`val`)
            storeNeededDynamicLibraryName(name)
            Log.e("   " + LogConstant.DIVISION_LINE)
            Log.e("   " + "Need Dynamic Library : " + name + " , val 0x" + Integer.toHexString(`val`))
        }
    }

    @Throws(IOException::class)
    private fun obtainSoName(raf: RandomAccessFile) {

        mDynamicLibraryName = getStrTabIndexString(mSoName)
        Log.e("   " + LogConstant.DIVISION_LINE)
        Log.e("   " + "My Dynamic Library : " + mDynamicLibraryName + " , at 0x" + Integer.toHexString(mSoName))

    }

    @Throws(IOException::class)
    private fun parseDynamicEntry(dynamic: Elf_Dyn, raf: RandomAccessFile): Boolean {

        when (ByteUtil.bytes2Int32(dynamic.d_un!!)) {
            DT_NULL -> return false
            DT_NEEDED // elf necessary library
            -> mNeededDynamicLibraryPtr.add(getVal(dynamic.d_val))
            DT_PLTRELSZ -> {
                readDT_PLTRELSZ(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_PLTRELSZ " + +getVal(dynamic.d_val))
            }
            DT_PLTGOT -> {
                readDT_PLTGOT(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_PLTGOT at " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_HASH -> {
                readDT_HASH(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_HASH at " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_STRTAB -> {
                readDT_STRTAB(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_STRTAB at " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_SYMTAB -> {
                readDT_SYMTAB(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_SYMTAB at " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_RELA -> {
                readDT_RELA(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_RELA at " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_RELASZ -> {
                mRelaSz = getVal(dynamic.d_val)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_RELASZ : " + +getVal(dynamic.d_val))
            }
            DT_RELAENT -> {
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_RELAENT at " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_STRSZ -> {
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_STRSZ : " + +getVal(dynamic.d_val))
            }
            DT_SYMENT -> {
                assertSYMENT(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_SYMENT : " + +getVal(dynamic.d_val))
            }
            DT_INIT -> {
                readDT_INIT(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_INIT at " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_FINI -> {
                readDT_FINI(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_FINI at " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_SONAME -> mSoName = ByteUtil.bytes2Int32(dynamic.d_val)
            DT_RPATH -> {
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_RPATH at " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_SYMBOLIC -> {
                readDT_SYMBOLIC(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_SYMBOLIC at " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_REL -> {
                readDT_REL(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_REL at " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_RELSZ -> {
                dT_RELSZ = getVal(dynamic.d_val).toInt()
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_RELSZ : " + getVal(dynamic.d_val))
            }
            DT_RELENT -> {
                assertRELENT(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_RELENT : " + +getVal(dynamic.d_val))
            }
            DT_PLTREL -> {
                verifyPltRel(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_PLTREL " + getVal(dynamic.d_val))
            }
            DT_DEBUG -> {
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_DEBUG at " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_TEXTREL -> {
                readDT_TEXTREL(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_TEXTREL at " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_JMPREL -> {
                readDT_JMPREL(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_JMPREL at " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_LOPROC -> {
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_LOPROC at " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_HIPROC -> {
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_HIPROC at " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_INIT_ARRAY -> {
                readDT_INIT_ARRAY(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_INIT_ARRAY at " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_FINI_ARRAY -> {
                readDT_FINI_ARRAY(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_FINI_ARRAY at " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_RELCOUNT -> {
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_RELCOUNT : " + +getVal(dynamic.d_val) + " (ignore)")
            }
            DT_FINI_ARRAYSZ -> {
                readDT_FINI_ARRAYSZ(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_FINI_ARRAYSZ : " + getVal(dynamic.d_val))
            }
            DT_INIT_ARRAYSZ -> {
                readDT_INIT_ARRAYSZ(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_INIT_ARRAYSZ : " + getVal(dynamic.d_val))
            }
            DT_FLAGS_1 -> {
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_FLAGS_1 : " + getVal(dynamic.d_val))
            }
            DT_FLAGS -> {
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_FLAGS : " + getVal(dynamic.d_val))
            }
            DT_GNU_HASH -> {
                readDT_GNU_HASH(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_GNU_HASH : " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_ANDROID_REL -> {
                readDT_ANDROID_REL(dynamic)
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_ANDROID_REL : " + ByteUtil.bytes2Hex(dynamic.d_val))
            }
            DT_ANDROID_RELSZ -> {
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "DT_ANDROID_RELSZ : " + getVal(dynamic.d_val))
            }

            else -> {
                Log.e("   " + LogConstant.DIVISION_LINE)
                Log.e("   " + "Unknown DT type " + ByteUtil.bytes2Hex(dynamic.d_un))
            }
        }
        return true
    }

    private fun readDT_PLTGOT(dynamic: Elf_Dyn) {
        if (dT_PLTGOT == 0)
            dT_PLTGOT = getVal(dynamic.d_val)
        else
            throw IllegalStateException("DT_PLTGOT appear over once")
    }

    private fun readDT_PLTRELSZ(dynamic: Elf_Dyn) {
        if (mPltRel == 0)
            mPltRelSz = getVal(dynamic.d_val)
        else
            throw IllegalStateException("DT_PLTRELSZ appear over once")
    }

    private fun verifyPltRel(dynamic: Elf_Dyn) {
        if (getVal(dynamic.d_val) != DT_REL)
            throw RuntimeException("Unsupported DT_PLTREL")
    }

    private fun readDT_TEXTREL(dynamic: Elf_Dyn) {
        if (mDT_TEXTREL == 0)
            mDT_TEXTREL = getVal(dynamic.d_val)
        else
            throw IllegalStateException("DT_TEXTREL appear over once")
    }

    private fun readDT_SYMBOLIC(dynamic: Elf_Dyn) {
        if (!dT_SYMBOLIC)
            dT_SYMBOLIC = true
        else
            throw IllegalStateException("DT_SYMBOLIC appear over once")
    }

    private fun readDT_RELA(dynamic: Elf_Dyn) {
        throw RuntimeException("Unsupported DT_RELA")
    }

    private fun readDT_JMPREL(dynamic: Elf_Dyn) {
        if (mPltRel == 0)
            mPltRel = getVal(dynamic.d_val).toInt()
        else
            throw IllegalStateException("DT_JMPREL appear over once")
    }

    private fun readDT_REL(dynamic: Elf_Dyn) {
        if (dT_REL == 0)
            dT_REL = getVal(dynamic.d_val).toInt()
        else
            throw IllegalStateException("DT_REL appear over once")
    }

    private fun readDT_ANDROID_REL(dynamic: Elf_Dyn) {
        if (dT_ANDROID_REL == 0)
            dT_ANDROID_REL = getVal(dynamic.d_val).toInt()
        else
            throw IllegalStateException("DT_ANDROID_REL appear over once")
    }

    private fun readDT_GNU_HASH(dynamic: Elf_Dyn) {
        if (dT_GNU_HASH == 0)
            dT_GNU_HASH = ByteUtil.bytes2Int32(dynamic.d_val)
        else
            throw IllegalStateException("DT_GNU_HASH appear over once")
    }

    private fun assertSYMENT(dynamic: Elf_Dyn) {
        if (ByteUtil.bytes2Int32(dynamic.d_val) != 0x10)
        // Elf_Sym takes 0x10B
            throw AssertionError("assert fail , SYMENT != 0x10")
    }

    private fun assertRELENT(dynamic: Elf_Dyn) {
        if (ByteUtil.bytes2Int32(dynamic.d_val) != 0x8)
        // Elf_Sym takes 0x10B
            throw AssertionError("assert fail , SYMENT != 0x10")
    }

    private fun readDT_HASH(dynamic: Elf_Dyn) {
        if (dT_HASH == 0)
            dT_HASH = ByteUtil.bytes2Int32(dynamic.d_val)
        else
            throw IllegalStateException("DT_HASH appear over once")
    }

    private fun readDT_INIT_ARRAY(dynamic: Elf_Dyn) {
        if (dT_INIT_ARRAY == 0)
            dT_INIT_ARRAY = ByteUtil.bytes2Int32(dynamic.d_val)
        else
            throw IllegalStateException("mInitArray appear over once")
    }

    private fun readDT_INIT(dynamic: Elf_Dyn) {
        if (dT_INIT == 0)
            dT_INIT = ByteUtil.bytes2Int32(dynamic.d_val)
        else
            throw IllegalStateException("DT_INIT appear over once")
    }

    private fun readDT_SYMTAB(dynamic: Elf_Dyn) {
        if (dT_SYMTAB == 0)
            dT_SYMTAB = ByteUtil.bytes2Int32(dynamic.d_val)
        else
            throw IllegalStateException("DT_SYMTAB appear over once")
    }

    private fun readDT_STRTAB(dynamic: Elf_Dyn) {
        if (dT_STRTAB == 0)
            dT_STRTAB = ByteUtil.bytes2Int32(dynamic.d_val)
        else
            throw IllegalStateException("DT_STRTAB appear over once")
    }

    private fun readDT_FINI(dynamic: Elf_Dyn) {
        if (dT_FINI == 0)
            dT_FINI = ByteUtil.bytes2Int32(dynamic.d_val)
        else
            throw IllegalStateException("DT_FINI appear over once")
    }

    private fun readDT_FINI_ARRAY(dynamic: Elf_Dyn) {
        if (dT_FINI_ARRAY == 0)
            dT_FINI_ARRAY = ByteUtil.bytes2Int32(dynamic.d_val)
        else
            throw IllegalStateException("DT_FINI_ARRAY appear over once")
    }

    private fun readDT_FINI_ARRAYSZ(dynamic: Elf_Dyn) {
        if (dT_FINI_ARRAYSZ == 0)
            dT_FINI_ARRAYSZ = ByteUtil.bytes2Int32(dynamic.d_val)
        else
            throw IllegalStateException("DT_FINI_ARRAYSZ appear over once")
    }

    private fun readDT_INIT_ARRAYSZ(dynamic: Elf_Dyn) {
        if (dT_INIT_ARRAYSZ == 0)
            dT_INIT_ARRAYSZ = ByteUtil.bytes2Int32(dynamic.d_val)
        else
            throw IllegalStateException("DT_INIT_ARRAYSZ appear over once")
    }

    @Throws(IOException::class)
    private fun getStrTabIndexString(index: Int): String {
        if (dT_STRTAB == 0)
            throw IllegalStateException("Unable to find Library Name")

        val position = dT_STRTAB + elf_load_bias + index

        return ByteUtil.getStringFromMemory(position, OS.mainImage)
    }

    @Throws(IOException::class)
    fun getSymInStrTab(sym: Int): String? {
        if (dT_STRTAB == 0 || dT_SYMTAB == 0 || sym <= 0)
            return null

        val st_name = ByteArray(4)

        try {
            System.arraycopy(OS.mainImage.memory, dT_SYMTAB + elf_load_bias + 0x10 * sym, st_name, 0, 4)

            return getStrTabIndexString(ByteUtil.bytes2Int32(st_name))
        } catch (e: Exception) {
            Log.e("sym " + sym)
            throw IllegalStateException(e)
        }

    }

    val relocateSections: List<ELF_Relocate>
        get() = mRelocateSections

    val needLibraryName: List<String>
        get() = mNeededDynamicLibrary

    private fun storeNeededDynamicLibraryName(name: String) {
        mNeededDynamicLibrary.add(name)
    }

    private fun getVal(data: ByteArray?): Int {
        return ByteUtil.bytes2Int32(data!!)
    }

    @Throws(IOException::class)
    private fun loadRelocateSection(raf: RandomAccessFile) {
        if (dT_REL != 0)
            mRelocateSections.add(ELF_Relocate(dT_REL, dT_RELSZ, this, false))
        if (mPltRel != 0)
        // we don't check size
            mRelocateSections.add(ELF_Relocate(mPltRel, mPltRelSz, this, false))
        if (mRela != 0)
            mRelocateSections.add(ELF_Relocate(mRela, mRelaSz, this, true))

        if (mRelocateSections.size == 0)
            Log.e("\n no relocation Section Detected !\n")
    }
}
