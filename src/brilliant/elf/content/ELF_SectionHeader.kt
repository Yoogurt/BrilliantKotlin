package brilliant.elf.content

import brilliant.elf.content.ELF_Constant.ELFUnit.ELF32_Addr
import brilliant.elf.content.ELF_Constant.ELFUnit.ELF32_Off
import brilliant.elf.content.ELF_Constant.ELFUnit.ELF32_Word
import brilliant.elf.content.ELF_Constant.ELFUnit.ELF64_Addr
import brilliant.elf.content.ELF_Constant.ELFUnit.ELF64_Off
import brilliant.elf.content.ELF_Constant.ELFUnit.ELF64_Word
import brilliant.elf.content.ELF_Constant.ELFUnit.ELF64_Xword
import brilliant.elf.content.ELF_Constant.SectionHeaderContent.SHT_DYMSYM
import brilliant.elf.content.ELF_Constant.SectionHeaderContent.SHT_DYNAMIC
import brilliant.elf.content.ELF_Constant.SectionHeaderContent.SHT_HASH
import brilliant.elf.content.ELF_Constant.SectionHeaderContent.SHT_NOBITS
import brilliant.elf.content.ELF_Constant.SectionHeaderContent.SHT_NOTE
import brilliant.elf.content.ELF_Constant.SectionHeaderContent.SHT_NULL
import brilliant.elf.content.ELF_Constant.SectionHeaderContent.SHT_NUM
import brilliant.elf.content.ELF_Constant.SectionHeaderContent.SHT_PROGBITS
import brilliant.elf.content.ELF_Constant.SectionHeaderContent.SHT_REL
import brilliant.elf.content.ELF_Constant.SectionHeaderContent.SHT_RELA
import brilliant.elf.content.ELF_Constant.SectionHeaderContent.SHT_SHLIB
import brilliant.elf.content.ELF_Constant.SectionHeaderContent.SHT_STRTAB
import brilliant.elf.content.ELF_Constant.SectionHeaderContent.SHT_SYMTAB
import brilliant.elf.content.LogConstant.DIVISION_LINE
import brilliant.elf.content.LogConstant.ELF_SECTION_TABLE

import java.io.IOException
import java.io.RandomAccessFile

import brilliant.elf.util.ByteUtil
import brilliant.elf.util.Log

/**
 * @author Yoogurt
 * *
 * *         SectionHeaders are useless when we parse a dynamic library or
 * *         executable. for Debug Only
 */
@Deprecated("")
internal class ELF_SectionHeader @Throws(IOException::class)
constructor(raf: RandomAccessFile, val elfHeader: ELF_Header) {

    inner class ELF_Shdr {

        /**
         * name - index into section header string table section
         */
        lateinit internal var sh_name: ByteArray

        /**
         * type
         */
        lateinit internal var sh_type: ByteArray

        /**
         * flags
         */
        lateinit internal var sh_flags: ByteArray

        /**
         * address
         */
        lateinit internal var sh_addr: ByteArray

        /**
         * file offset
         */
        lateinit internal var sh_offset: ByteArray

        /**
         * section size
         */
        lateinit internal var sh_size: ByteArray

        /**
         * section header table index link
         */
        lateinit internal var sh_link: ByteArray

        /**
         * extra information
         */
        lateinit internal var sh_info: ByteArray

        /**
         * address alignment
         */
        lateinit internal var sh_addralign: ByteArray

        /**
         * section entry size
         */
        lateinit internal var sh_entsize: ByteArray

        /**
         * name of this section
         */
        lateinit internal var section: ELF_Section

        /**

         */
        var name: String? = null

        val memoryOffset: Long
            get() = ByteUtil.bytes2Int64(sh_addr)

        val memorySize: Long
            get() = ByteUtil.bytes2Int32(sh_size).toLong()

        val sectionOffset: Long
            get() = ByteUtil.bytes2Int64(sh_offset)

        val sectionSize: Int
            get() = ByteUtil.bytes2Int32(sh_size)
    }

    lateinit var allDecodedSectionHeader: Array<ELF_Shdr?>
        private set
    private var mStringSectionHeaderIndex: Int = 0

    init {

        Log.e(ELF_SECTION_TABLE)

        locateSectionHeaderOffset(raf)

        if (elfHeader.is32Bit)
            readSectionHeader32(raf)
        else
            readSectionHeader64(raf)

        namedSection()

        printSectionHeader()

    }

    @Throws(IOException::class)
    private fun readSectionHeader32(raf: RandomAccessFile) {

        val mSectionHeaderCount = elfHeader.sectionHeaderNum
        allDecodedSectionHeader = arrayOfNulls<ELF_Shdr>(mSectionHeaderCount)

        mStringSectionHeaderIndex = elfHeader.sectionStringIndex

        for (m in 0..mSectionHeaderCount - 1) {

            val mS = genrateSectionHeaderStructure32()
            readSectionHeaderStructure(raf, mS)

            allDecodedSectionHeader[m] = mS

            loadSectionFromSectionHeader(raf, mS)

            if (allDecodedSectionHeader!![mStringSectionHeaderIndex] === mS)
                Log.e("This is a Section contains other Section's Name")
        }
    }

    @Throws(IOException::class)
    private fun readSectionHeader64(raf: RandomAccessFile) {

        val mSectionHeaderCount = elfHeader.sectionHeaderNum
        allDecodedSectionHeader = arrayOfNulls<ELF_Shdr>(mSectionHeaderCount)

        mStringSectionHeaderIndex = elfHeader.sectionStringIndex

        for (m in 0..mSectionHeaderCount - 1) {

            val mS = genrateSectionHeaderStructure64()
            readSectionHeaderStructure(raf, mS)

            allDecodedSectionHeader[m] = mS

            loadSectionFromSectionHeader(raf, mS)
        }
    }

    private fun genrateSectionHeaderStructure32(): ELF_Shdr {

        val mS = ELF_Shdr()

        mS.sh_name = ByteArray(ELF32_Word)

        mS.sh_type = ByteArray(ELF32_Word)

        mS.sh_flags = ByteArray(ELF32_Word)

        mS.sh_addr = ByteArray(ELF32_Addr)

        mS.sh_offset = ByteArray(ELF32_Off)

        mS.sh_size = ByteArray(ELF32_Word)

        mS.sh_link = ByteArray(ELF32_Word)

        mS.sh_info = ByteArray(ELF32_Word)

        mS.sh_addralign = ByteArray(ELF32_Word)

        mS.sh_entsize = ByteArray(ELF32_Word)

        return mS

    }

    private fun printSectionHeader() {

        for (mS in allDecodedSectionHeader) {

            Log.e(DIVISION_LINE)

            when (ByteUtil.bytes2Int32(mS!!.sh_type, elfHeader.isLittleEndian)) {

                SHT_NULL/* 0 */ -> Log.e("This Section is Unvalueable")

                SHT_PROGBITS/* 1 */ -> Log.e("Section : " + mS.name + " , type : SHT_PROGBITS "
                        + ", at " + ByteUtil.bytes2Hex(mS.sh_offset)
                        + " , size : " + ByteUtil.bytes2Int32(mS.sh_size))

                SHT_SYMTAB/* 2 */ -> Log.e("Section : " + mS.name
                        + " , type : SHT_SYMTAB , at "
                        + ByteUtil.bytes2Hex(mS.sh_offset) + " , size : "
                        + ByteUtil.bytes2Int32(mS.sh_size))

                SHT_STRTAB/* 3 */ -> {
                    Log.e("Section : " + mS.name
                            + " , type : SHT_STRTAB , at "
                            + ByteUtil.bytes2Hex(mS.sh_offset) + " , size : "
                            + ByteUtil.bytes2Int32(mS.sh_size))

                    if (allDecodedSectionHeader!![mStringSectionHeaderIndex] === mS)
                        Log.e("This is a Section contains other Section's Name")
                }

                SHT_RELA/* 4 */ -> Log.e("Section : " + mS.name + " , type : SHT_RELA , at "
                        + ByteUtil.bytes2Hex(mS.sh_offset) + " , size : "
                        + ByteUtil.bytes2Int32(mS.sh_size))

                SHT_HASH/* 5 */ -> Log.e("Section : " + mS.name + " , type : SHT_HASH , at "
                        + ByteUtil.bytes2Hex(mS.sh_offset) + " , size : "
                        + ByteUtil.bytes2Int32(mS.sh_size))

                SHT_DYNAMIC/* 6 */ -> Log.e("Section : " + mS.name
                        + " , type : SHT_DYNAMIC , at "
                        + ByteUtil.bytes2Hex(mS.sh_offset) + " , size : "
                        + ByteUtil.bytes2Int32(mS.sh_size))

                SHT_NOTE/* 7 */ -> Log.e("Section : " + mS.name + " , type : SHT_NOTE , at "
                        + ByteUtil.bytes2Hex(mS.sh_offset) + " , size : "
                        + ByteUtil.bytes2Int32(mS.sh_size))

                SHT_REL/* 9 */ -> Log.e("Section : " + mS.name + " , type : SHT_REL , at "
                        + ByteUtil.bytes2Hex(mS.sh_offset) + " , size : "
                        + ByteUtil.bytes2Int32(mS.sh_size))

                SHT_SHLIB/* 10 */ -> Log.e("Section : " + mS.name + " , type : SHT_SHLIB , at "
                        + ByteUtil.bytes2Hex(mS.sh_offset) + " , size : "
                        + ByteUtil.bytes2Int32(mS.sh_size))

                SHT_DYMSYM/* 11 */ -> Log.e("Section : " + mS.name
                        + " , type : SHT_DYMSYM , at "
                        + ByteUtil.bytes2Hex(mS.sh_offset) + " , size : "
                        + ByteUtil.bytes2Int32(mS.sh_size))

                SHT_NUM/* 12 */ -> Log.e("Section : " + mS.name + " , type : SHT_NUM , at "
                        + ByteUtil.bytes2Hex(mS.sh_offset) + " , size : "
                        + ByteUtil.bytes2Int32(mS.sh_size))

                SHT_NOBITS/* 8 */ -> Log.e("Section : " + mS.name
                        + " , type : SHT_NOBITS , at "
                        + ByteUtil.bytes2Hex(mS.sh_offset) + " , size : "
                        + ByteUtil.bytes2Int32(mS.sh_size))
                else -> Log.e("Section : " + mS.name
                        + " , type : Unknown Section Header Type ! at "
                        + ByteUtil.bytes2Hex(mS.sh_offset) + " , size : "
                        + ByteUtil.bytes2Int32(mS.sh_size))
            }
        }

    }

    @Throws(IOException::class)
    private fun readSectionHeaderStructure(raf: RandomAccessFile, mS: ELF_Shdr) {

        raf.read(mS.sh_name!!)
        raf.read(mS.sh_type!!)
        raf.read(mS.sh_flags!!)
        raf.read(mS.sh_addr!!)
        raf.read(mS.sh_offset!!)
        raf.read(mS.sh_size!!)
        raf.read(mS.sh_link!!)
        raf.read(mS.sh_info!!)
        raf.read(mS.sh_addralign!!)
        raf.read(mS.sh_entsize!!)

    }

    private fun genrateSectionHeaderStructure64(): ELF_Shdr {

        val mS = ELF_Shdr()

        mS.sh_name = ByteArray(ELF64_Word)

        mS.sh_type = ByteArray(ELF64_Word)

        mS.sh_flags = ByteArray(ELF64_Xword)

        mS.sh_addr = ByteArray(ELF64_Addr)

        mS.sh_offset = ByteArray(ELF64_Off)

        mS.sh_size = ByteArray(ELF64_Xword)

        mS.sh_link = ByteArray(ELF64_Word)

        mS.sh_info = ByteArray(ELF64_Word)

        mS.sh_addralign = ByteArray(ELF64_Xword)

        mS.sh_entsize = ByteArray(ELF64_Xword)

        return mS

    }

    private fun namedSection() {

        val mStringSection = allDecodedSectionHeader!![mStringSectionHeaderIndex]

        for (mS in allDecodedSectionHeader!!)
            mS!!.name = mStringSection!!.section!!.getStringAtIndex(mS.sh_name)
    }

    @Throws(IOException::class)
    private fun locateSectionHeaderOffset(raf: RandomAccessFile) {
        val mSectionOffset = elfHeader.sectionHeaderTableOffset
        raf.seek(mSectionOffset)
    }

    private fun loadSectionFromSectionHeader(raf: RandomAccessFile, mS: ELF_Shdr) {
        try {
            val index = raf.filePointer
            mS.section = ELF_Section(raf, elfHeader, mS)
            raf.seek(index)
        } catch (e: IOException) {
            e.printStackTrace()
            throw RuntimeException("Unable to decode Section")
        }

    }

}
