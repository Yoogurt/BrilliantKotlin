package brilliant.elf.content

import brilliant.elf.content.ELF_Constant.ELFUnit.ELF32_Addr
import brilliant.elf.content.ELF_Constant.ELFUnit.ELF32_Off
import brilliant.elf.content.ELF_Constant.ELFUnit.ELF32_Word
import brilliant.elf.content.ELF_Constant.ProgramHeaderContent.PT_DYNAMIC
import brilliant.elf.content.ELF_Constant.ProgramHeaderContent.PT_GUN_STACK
import brilliant.elf.content.ELF_Constant.ProgramHeaderContent.PT_INTERP
import brilliant.elf.content.ELF_Constant.ProgramHeaderContent.PT_LOAD
import brilliant.elf.content.ELF_Constant.ProgramHeaderContent.PT_NOTE
import brilliant.elf.content.ELF_Constant.ProgramHeaderContent.PT_NULL
import brilliant.elf.content.ELF_Constant.ProgramHeaderContent.PT_PHDR
import brilliant.elf.content.ELF_Constant.ProgramHeaderContent.PT_SHLIB

import java.io.IOException
import java.io.RandomAccessFile
import java.util.ArrayList

import brilliant.elf.support.CastSupport
import brilliant.elf.util.ByteUtil
import brilliant.elf.util.Log

internal class ELF_ProgramHeader @Throws(Exception::class)
constructor(`is`: RandomAccessFile, val elfHeader: ELF_Header,
            check: Boolean) {

    internal class ELF_Phdr(val programHeader: ELF_ProgramHeader, val id: Int) : CastSupport() {

        /**
         * segment type
         */
        lateinit var p_type: ByteArray

        /**
         * segment offset
         */
        lateinit var p_offset: ByteArray

        /**
         * virtual address of segment
         */
        lateinit var p_vaddr: ByteArray

        /**
         * physical address
         */
        lateinit var p_paddr: ByteArray

        /**
         * number of bytes in file for segment
         */
        lateinit var p_filesz: ByteArray

        /**
         * number of bytes in memory for segment
         */
        lateinit var p_memsz: ByteArray

        /**
         * flag
         */
        lateinit var p_flags: ByteArray

        /**
         * memory alignment
         */
        lateinit var p_align: ByteArray

        val memoryOffset: Long
            get() = ByteUtil.bytes2Int64(p_vaddr)

        val memorySize: Long
            get() = ByteUtil.bytes2Int32(p_memsz).toLong()

        val programOffset: Long
            get() = ByteUtil.bytes2Int64(p_offset)

        val programSize: Int
            get() = ByteUtil.bytes2Int32(p_filesz)

        companion object {

            @JvmOverloads fun reinterpret_cast(data: ByteArray, startIndex: Int = 0): ELF_Phdr {
                throw UnsupportedOperationException()
            }

            fun size(): Int {
                return 32
            }
        }
    }

    lateinit var allDecodedProgramHeader: Array<ELF_Phdr?>
        private set

    private val mLoadableSegment = ArrayList<ELF_Phdr>()

    lateinit var dynamicSegment: ELF_Phdr
        private set

    init {

        Log.e(LogConstant.DIVISION_LINE)
        Log.e(LogConstant.ELF_PROGRAM_TABLE)
        Log.e(LogConstant.DIVISION_LINE)

        verifyPhdrNum(elfHeader)

        `is`.seek(elfHeader.programHeaderTableOffset)

        if (elfHeader.is32Bit)
            read32ProgramHeader(`is`)
    }

    @Throws(ELFDecodeException::class)
    private fun verifyPhdrNum(header: ELF_Header) {

        val phdr_num_ = header.programHeaderTableNum

        if (phdr_num_ < 1 || phdr_num_ > 65536 / ELF_Phdr.size())
            throw ELFDecodeException("Elf_Phdr larger than 64 KB")
    }

    @Throws(Exception::class)
    private fun read32ProgramHeader(`is`: RandomAccessFile) {

        val mProgramHeaderCount = elfHeader.programHeaderTableNum
        allDecodedProgramHeader = arrayOfNulls<ELF_Phdr>(mProgramHeaderCount)

        `is`.seek(elfHeader.programHeaderTableOffset)

        for (m in 0..mProgramHeaderCount - 1) {

            val ph = generate32ProgramHeaderStruture(m)
            read32ProgramHeaderInternal(ph, `is`)

            allDecodedProgramHeader[m] = ph

            when (ByteUtil.bytes2Int32(ph.p_type, elfHeader.isLittleEndian)) {
                PT_NULL -> Log.e("Program Header $m type : PT_NULL")

                PT_LOAD -> {
                    Log.e("Program Header $m type : PT_LOAD")
                    addLoadableSegment(ph)
                }

                PT_DYNAMIC -> {
                    dynamicSegment = ph
                    Log.e("Program Header $m type : PT_DYNAMIC")
                }

                PT_INTERP -> {
                    Log.e("Program Header $m type : PT_INTERP")
                    verifyInterpretorSegment(ph, `is`)
                }

                PT_NOTE -> Log.e("Program Header $m type : PT_NOTE")

                PT_SHLIB -> Log.e("Program Header $m type : PT_SHLIB")

                PT_PHDR -> Log.e("Program Header $m type : PT_PHDR")

                PT_GUN_STACK -> Log.e("Program Header $m type : PT_GUN_STACK")

                else -> Log.e("Unknown Program Header "
                        + m
                        + " type : "
                        + ByteUtil.bytes2Int32(ph.p_type,
                        elfHeader.isLittleEndian))
            }

            logSegmentInfo(ph)
            Log.e(LogConstant.DIVISION_LINE)

        }

    }

    private fun addLoadableSegment(ph: ELF_Phdr) {
        mLoadableSegment.add(ph)
    }

    private fun verifyInterpretorSegment(ph: ELF_Phdr, raf: RandomAccessFile) {

        try {
            val prePosition = raf.filePointer
            raf.seek(ByteUtil.bytes2Int64(ph.p_offset))
            Log.e("Interpretor : " + ByteUtil.getStringFromBytes(raf))
            raf.seek(prePosition)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @Throws(IOException::class)
    private fun read32ProgramHeaderInternal(ph: ELF_Phdr, `is`: RandomAccessFile) {

        `is`.read(ph.p_type!!)
        `is`.read(ph.p_offset!!)
        `is`.read(ph.p_vaddr!!)
        `is`.read(ph.p_paddr!!)
        `is`.read(ph.p_filesz!!)
        `is`.read(ph.p_memsz!!)
        `is`.read(ph.p_flags!!)
        `is`.read(ph.p_align!!)

    }

    private fun logSegmentInfo(ph: ELF_Phdr) {

        Log.e("Segment p_offset :  " + ByteUtil.bytes2Hex(ph.p_offset))
        Log.e("Segment p_vaddr  : " + ByteUtil.bytes2Hex(ph.p_vaddr))
        Log.e("Segment p_memsz : " + ByteUtil.bytes2Hex(ph.p_memsz))

        Log.e("Segment p_filesz : " + ByteUtil.decHexSizeFormat32(ph.p_filesz,
                elfHeader.isLittleEndian))
        if (elfHeader.isSharedObject || elfHeader.isExeutable)
            Log.e("Segment will mmap directly at memory : " + ByteUtil.bytes2Hex(ph.p_paddr))

        // Util.assertAlign(Util.bytes2Int64(ph.p_align));
    }

    private fun generate32ProgramHeaderStruture(id: Int): ELF_Phdr {

        val ph = ELF_Phdr(this, id)
        ph.p_type = ByteArray(ELF32_Word)// 4
        ph.p_offset = ByteArray(ELF32_Off)// 4
        ph.p_vaddr = ByteArray(ELF32_Addr)// 4
        ph.p_paddr = ByteArray(ELF32_Addr)// 4
        ph.p_filesz = ByteArray(ELF32_Word)// 4
        ph.p_memsz = ByteArray(ELF32_Word)// 4
        ph.p_flags = ByteArray(ELF32_Word)// 4
        ph.p_align = ByteArray(ELF32_Word)// 4

        return ph

    }

    fun getProgramHeaderBySegmentPosition(position: Int): ELF_Phdr? {

        for (mT in allDecodedProgramHeader!!) {
            if (ByteUtil.bytes2Int32(mT!!.p_offset, elfHeader.isLittleEndian) == position) {
                return mT
            }
        }

        return null
    }

    fun getProgramHeaderBySegmentPosition(position: Long): ELF_Phdr? {

        for (mT in allDecodedProgramHeader!!) {
            if (ByteUtil.bytes2Int64(mT!!.p_offset) == position) {
                return mT
            }
        }

        return null
    }

    val allLoadableSegment: List<ELF_Phdr>
        get() = mLoadableSegment

}
