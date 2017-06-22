package brilliant.elf.content

import brilliant.elf.content.ELF_Constant.ELFUnit.ELF32_Addr
import brilliant.elf.content.ELF_Constant.ELFUnit.ELF32_Half
import brilliant.elf.content.ELF_Constant.ELFUnit.ELF32_Off
import brilliant.elf.content.ELF_Constant.ELFUnit.ELF32_Word
import brilliant.elf.content.ELF_Constant.HeaderContent.EI_CALSS
import brilliant.elf.content.ELF_Constant.HeaderContent.EI_DATA
import brilliant.elf.content.ELF_Constant.HeaderContent.EI_NIDENT
import brilliant.elf.content.ELF_Constant.HeaderContent.ELFCLASS32
import brilliant.elf.content.ELF_Constant.HeaderContent.ELFCLASS64
import brilliant.elf.content.ELF_Constant.HeaderContent.ELFDATA2LSB
import brilliant.elf.content.ELF_Constant.HeaderContent.ELFDATA2MSB
import brilliant.elf.content.ELF_Constant.HeaderContent.ELFMagicCode
import brilliant.elf.content.ELF_Constant.HeaderContent.EM_386
import brilliant.elf.content.ELF_Constant.HeaderContent.EM_AARCH64
import brilliant.elf.content.ELF_Constant.HeaderContent.EM_ARM
import brilliant.elf.content.ELF_Constant.HeaderContent.EM_MIPS
import brilliant.elf.content.ELF_Constant.HeaderContent.EM_X86_64
import brilliant.elf.content.ELF_Constant.HeaderContent.ET_CORE
import brilliant.elf.content.ELF_Constant.HeaderContent.ET_DYN
import brilliant.elf.content.ELF_Constant.HeaderContent.ET_EXEC
import brilliant.elf.content.ELF_Constant.HeaderContent.ET_HIPROC
import brilliant.elf.content.ELF_Constant.HeaderContent.ET_LOPROC
import brilliant.elf.content.ELF_Constant.HeaderContent.ET_NONE
import brilliant.elf.content.ELF_Constant.HeaderContent.ET_REL
import brilliant.elf.content.ELF_Constant.HeaderContent.EV_CURRENT

import java.io.RandomAccessFile

import brilliant.elf.util.ByteUtil
import brilliant.elf.util.Log

/**
 * @author Yoogurt
 */
internal class ELF_Header @Throws(Exception::class)
constructor(`is`: RandomAccessFile) {

    /**
     * ELF Identification 00 - 0F(32、64 bit)
     */
    lateinit var e_ident: ByteArray
    /**
     * object file type 10 - 11(32、64 bit)
     */
    lateinit var e_type: ByteArray
    /**
     * target machine 12 - 13(32、64 bit)
     */
    lateinit var e_machine: ByteArray
    /**
     * object file version 14 - 17(32、64 bit)
     */
    lateinit var e_version: ByteArray
    /**
     * virtual entry point , it's doesn't work when ELF is a shared object 18 -
     * 1B (64bit 18 - 1F)
     */
    lateinit var e_entry: ByteArray
    /**
     * program header table offset 1C - 1F (64 bit 20 - 27)
     */
    lateinit var e_phoff: ByteArray
    /**
     * section hreader table offset 20 - 23 (64 bit 28 - 2F)
     */
    lateinit var e_shoff: ByteArray
    /**
     * processor-specific flags 24 - 27 (64 bit 30 - 33)
     */
    lateinit var e_flags: ByteArray
    /**
     * ELF header size(the size of this class) 28 - 29 (64 bit 34 - 35)
     */
    lateinit var e_ehsize: ByteArray
    /**
     * program hreader entry size 2A - 2B (64 bit 36 - 37)
     */
    lateinit var e_phentsize: ByteArray
    /**
     * number of program header entries 2C - 2D (64 bit 38 - 39)
     */
    lateinit var e_phnum: ByteArray
    /**
     * section header entry size 2E - 2F (64 bit 3A - 3B)
     */
    lateinit var e_shentsize: ByteArray
    /**
     * number of section header entries 30 - 31 (64 bit 3C - 3D)
     */
    lateinit var e_shnum: ByteArray
    /**
     * section header table's string table entry offset 32 - 33 (64 bit 3E - 3F)
     */
    lateinit var e_shstrndex: ByteArray

    init {

        Log.e(LogConstant.DIVISION_LINE)
        Log.e(LogConstant.ELF_HEADER)
        Log.e(LogConstant.DIVISION_LINE)

        verifyElfMagicCode(`is`)

        if (is32Bit)
            read32BitHeader(`is`)
        else
            throw ELFDecodeException("64 bit not supported yet")

        checkHeaderParameters()

        readELFHeader()
    }

    @Throws(Exception::class)
    private fun read32BitHeader(`is`: RandomAccessFile) {

        e_type = ByteArray(ELF32_Half)
        `is`.read(e_type)

        e_machine = ByteArray(ELF32_Half)
        `is`.read(e_machine)

        e_version = ByteArray(ELF32_Word)
        `is`.read(e_version)

        e_entry = ByteArray(ELF32_Addr)
        `is`.read(e_entry)

        e_phoff = ByteArray(ELF32_Off)
        `is`.read(e_phoff)

        e_shoff = ByteArray(ELF32_Off)
        `is`.read(e_shoff)

        e_flags = ByteArray(ELF32_Word)
        `is`.read(e_flags)

        e_ehsize = ByteArray(ELF32_Half)
        `is`.read(e_ehsize)

        e_phentsize = ByteArray(ELF32_Half)
        `is`.read(e_phentsize)

        e_phnum = ByteArray(ELF32_Half)
        `is`.read(e_phnum)

        e_shentsize = ByteArray(ELF32_Half)
        `is`.read(e_shentsize)

        e_shnum = ByteArray(ELF32_Half)
        `is`.read(e_shnum)

        e_shstrndex = ByteArray(ELF32_Half)
        `is`.read(e_shstrndex)
    }

    @Throws(Exception::class)
    private fun verifyElfMagicCode(`is`: RandomAccessFile) {

        e_ident = ByteArray(EI_NIDENT)
        `is`.read(e_ident)

        if (!// assert Magic
        // Code correct
        // or not
        ByteUtil.equals(ELFMagicCode, 0, e_ident, 0,
                ELFMagicCode.size))
            throw ELFDecodeException("Not a ELF File")
    }

    @Throws(ELFDecodeException::class)
    private fun checkHeaderParameters() {
        when (e_ident[EI_CALSS].toInt()) {
            ELFCLASS32 -> Log.i("for 32 bit machine")

            ELFCLASS64 -> {
                Log.i("for 64 bit machine")
                throw UnsupportedOperationException(
                        "64 bit elf are not supported to decode")
            }

            else -> throw ELFDecodeException("ELF illegal class file , EI_CLASS = " + ByteUtil.byte2Hex(e_ident[EI_CALSS]))
        }

        when (e_ident[EI_DATA].toInt()) {
            ELFDATA2LSB -> Log.i("for little endian machine")

            ELFDATA2MSB -> {
                Log.i("for big endian machine")
                throw ELFDecodeException("Unknown target endian machine")
            }

            else -> throw ELFDecodeException("Unknown target endian machine")
        }

        if (ByteUtil.bytes2Int32(e_type) != ET_DYN)
            throw ELFDecodeException()

        if (ByteUtil.bytes2Int32(e_version) != EV_CURRENT)
            throw ELFDecodeException("Unknown ELF Version")

        if (ByteUtil.bytes2Int32(e_machine) != EM_ARM)
            throw ELFDecodeException("elf has unexpected e_machine : " + ByteUtil.bytes2Hex(e_machine))
    }

    @Throws(ELFDecodeException::class)
    fun readELFHeader() {

        Log.e()

        when (ByteUtil.bytes2Int32(e_type, isLittleEndian)) {
            ET_NONE -> Log.i("Unknown ELF Type")
            ET_REL -> Log.i("Relocatable ELF File")
            ET_EXEC -> Log.i("Executable ELF File")
            ET_DYN -> Log.i("Shared Object ELF File")
            ET_CORE -> Log.i("Core ELF File")
            ET_HIPROC, ET_LOPROC -> Log.i("Specify Architecture ELF")
            else -> throw ELFDecodeException(
                    "Unknown ELF Type While reading ELF Header")
        }

        when (ByteUtil.bytes2Int32(e_machine, isLittleEndian)) {
            EM_ARM -> Log.i("for ARM CPU(armeabi armeabi-v7a)")
            EM_AARCH64 -> Log.i("for ARM AArch64 CPU(armeabi-v8a , 64 bit processor)")
            EM_MIPS -> Log.e("for MIPS CPU")
            EM_386 -> Log.e("for Intel x86(32 bit processor)")
            EM_X86_64 -> Log.e("for Intel x86(64 bit processor)")
            else -> throw ELFDecodeException(
                    "Unsupport processor Architecture for this ELF !")
        }

        if (ByteUtil.bytes2Int32(e_version, isLittleEndian) != EV_CURRENT)
            throw ELFDecodeException("Unknown ELF Version")

        Log.e()

        Log.e("ELF Enter Entry : " + ByteUtil.bytes2Hex(e_entry))

        Log.e("Program Header Table Offset : " + ByteUtil.bytes2Hex(e_phoff)
                + if (isLittleEndian) " Little Endian" else " Big Endian")
        Log.e("Program Header Table per enitity's size : "
                + ByteUtil.bytes2Int32(e_phentsize, isLittleEndian) + " B")
        Log.e("Program Header Table total enitities : " + ByteUtil.bytes2Int32(e_phnum, isLittleEndian))

        Log.e()

        Log.e("Section Header Table Offset : " + ByteUtil.bytes2Hex(e_shoff)
                + if (isLittleEndian) " Little Endian" else " Big Endian")
        Log.e("Section Header Table per enitity's size : "
                + ByteUtil.bytes2Int32(e_shentsize, isLittleEndian) + " B")
        Log.e("Section Header Table total enitities : " + ByteUtil.bytes2Int32(e_shnum, isLittleEndian))
        Log.e("Section Header Table's \"String Table\" Index : " + ByteUtil.bytes2Hex(e_shstrndex))

        Log.e()

        Log.e("ELF Header Flags : " + ByteUtil.bytes2Hex(e_flags))
        Log.e("ELF Header totally size : "
                + ByteUtil.bytes2Int32(e_ehsize, isLittleEndian) + " B")

    }

    val isSharedObject: Boolean
        get() = ByteUtil.bytes2Int32(e_type, isLittleEndian) == ET_DYN

    val isExeutable: Boolean
        get() = ByteUtil.bytes2Int32(e_type, isLittleEndian) == ET_EXEC

    val is32Bit: Boolean
        get() = e_ident[EI_CALSS].toInt() == ELFCLASS32

    val isLittleEndian: Boolean
        get() = e_ident[EI_DATA].toInt() == ELFDATA2LSB

    val elfEntry: Long
        get() {
            if (is32Bit)
                return ByteUtil.bytes2Int32(e_entry, isLittleEndian).toLong()
            return ByteUtil.bytes2Int64(e_entry, isLittleEndian)
        }

    val programHeaderTableOffset: Long
        get() {
            if (is32Bit)
                return ByteUtil.bytes2Int32(e_phoff, isLittleEndian).toLong()
            return ByteUtil.bytes2Int64(e_phoff, isLittleEndian)
        }

    val programHeaderTableEntrySize: Int
        get() = ByteUtil.bytes2Int32(e_phentsize, isLittleEndian)

    val programHeaderTableNum: Int
        get() = ByteUtil.bytes2Int32(e_phnum, isLittleEndian)

    val sectionHeaderTableOffset: Long
        get() {
            if (is32Bit)
                return ByteUtil.bytes2Int32(e_shoff, isLittleEndian).toLong()
            return ByteUtil.bytes2Int64(e_shoff, isLittleEndian)
        }

    val sectionHeaderTableEntrySize: Int
        get() = ByteUtil.bytes2Int32(e_shentsize, isLittleEndian)

    val sectionHeaderNum: Int
        get() = ByteUtil.bytes2Int32(e_shnum, isLittleEndian)

    val sectionStringIndex: Int
        get() = ByteUtil.bytes2Int32(e_shstrndex, isLittleEndian)

    /**
     * we would like to assume that an elf wouldn't larger than 4GB
     */
    val headerSize: Int
        get() = ByteUtil.bytes2Int32(e_ehsize, isLittleEndian)

    override fun toString(): String {
        return String
                .format("[e_ident = %s\n e_type = %s\n e_machine = %s\n e_version = %s\n e_entry = %s\n e_phoff = %s\n e_shoff = %s\n e_flags = %s\n e_ehsize = %s\n e_phentsize = %s\n e_phnum = %s\n e_shentsize = %s\n e_shnum = %s\n e_shstrndex = %s]",
                        ByteUtil.bytes2Hex(e_ident),
                        ByteUtil.bytes2Hex(e_type),
                        ByteUtil.bytes2Hex(e_machine),
                        ByteUtil.bytes2Hex(e_version),
                        ByteUtil.bytes2Hex(e_entry),
                        ByteUtil.bytes2Hex(e_phoff),
                        ByteUtil.bytes2Hex(e_shoff),
                        ByteUtil.bytes2Hex(e_flags),
                        ByteUtil.bytes2Hex(e_ehsize),
                        ByteUtil.bytes2Hex(e_phentsize),
                        ByteUtil.bytes2Hex(e_phnum),
                        ByteUtil.bytes2Hex(e_shentsize),
                        ByteUtil.bytes2Hex(e_shnum),
                        ByteUtil.bytes2Hex(e_shstrndex))
    }

}