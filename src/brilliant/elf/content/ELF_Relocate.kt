package brilliant.elf.content

import brilliant.elf.content.ELF_Constant.DT_RelType.R_ARM_GLOB_DAT
import brilliant.elf.content.ELF_Constant.DT_RelType.R_ARM_JUMP_SLOT
import brilliant.elf.content.ELF_Constant.DT_RelType.R_ARM_RELATIVE
import brilliant.elf.content.ELF_Constant.ELFUnit.ELF32_Addr
import brilliant.elf.content.ELF_Constant.ELFUnit.ELF32_Sword
import brilliant.elf.content.ELF_Constant.ELFUnit.ELF32_Word
import brilliant.elf.content.ELF_Definition.ELF_R_TYPE
import brilliant.elf.util.ByteUtil
import brilliant.elf.util.Log
import brilliant.elf.vm.OS
import java.io.IOException
import java.util.*

internal class ELF_Relocate @Throws(IOException::class)
constructor(offset: Int, size: Int, private val mSelf: ELF_Dynamic, val isRela: Boolean // are we Rela ?
) {

    open class Elf_rel : Comparable<Elf_rel> {

        lateinit var r_offset: ByteArray

        lateinit var r_info: ByteArray

        override fun compareTo(o: Elf_rel): Int {
            return if (brilliant.elf.content.ELF_Definition.ELF_R_SYM(r_info) > brilliant.elf.content.ELF_Definition.ELF_R_SYM(o.r_info)) 1 else -1
        }

    }

    class Elf_rela : Elf_rel() {
        lateinit var r_addend: ByteArray
    }

    lateinit var relocateEntry: Array<Elf_rel?>
        private set

    init {

        if (!isRela)
            readElf_Rel(offset, size ushr 3)
        else
            readElf_Rela(offset, size ushr 3)

        Arrays.sort(relocateEntry)
        printElf_rel()
    }

    @Throws(IOException::class)
    private fun readElf_Rela(offset: Int, size: Int) {

        relocateEntry = arrayOfNulls<Elf_rel>(size)

        for (i in 0..size - 1)
            relocateEntry[i] = generateElfRelocateA32(offset + mSelf.elf_load_bias + 8 * i)

    }

    @Throws(IOException::class)
    private fun readElf_Rel(offset: Int, size: Int) {

        relocateEntry = arrayOfNulls<Elf_rel>(size)

        for (i in 0..size - 1)
            relocateEntry[i] = generateElfRelocate32(offset + mSelf.elf_load_bias + 8 * i)

    }

    @Throws(IOException::class)
    private fun printElf_rel() {

        relocateEntry.forEach {
            rel ->

            val r_info = ELF_R_TYPE(rel!!.r_info)

            val sym = brilliant.elf.content.ELF_Definition.ELF_R_SYM(rel.r_info)

            when (r_info.toInt()) {
                R_ARM_GLOB_DAT -> {
                    Log.e("       relocation section r_offset : " + ByteUtil.bytes2Hex(rel.r_offset)
                            + " r_info : R_ARM_GLOB_DAT " + " , sym : " + sym
                            + if (sym > 0) " , symbol name : " + mSelf.getSymInStrTab(sym)!! else "")
                    Log.e(LogConstant.DIVISION_LINE)
                }

                R_ARM_RELATIVE -> {
                    Log.e("       relocation section r_offset : " + ByteUtil.bytes2Hex(rel.r_offset)
                            + " r_info : R_ARM_RELATIVE" + " , sym : " + sym
                            + if (sym > 0) " , symbol name : " + mSelf.getSymInStrTab(sym)!! else "")
                    Log.e(LogConstant.DIVISION_LINE)
                }

                R_ARM_JUMP_SLOT -> {
                    Log.e("       relocation section r_offset : " + ByteUtil.bytes2Hex(rel.r_offset)
                            + " r_info : R_ARM_JUMP_SLOT" + " , sym : " + sym
                            + if (sym > 0) " , symbol name : " + mSelf.getSymInStrTab(sym)!! else "")
                    Log.e(LogConstant.DIVISION_LINE)
                }

                else -> {
                }
            }

        }
        Log.e("Found " + relocateEntry.size + " Relocate Info")
    }

    @Throws(IOException::class)
    private fun generateElfRelocate32(offset: Int): Elf_rel {

        val relocate = Elf_rel()
        relocate.r_offset = ByteArray(ELF32_Addr)
        relocate.r_info = ByteArray(ELF32_Word)

        System.arraycopy(OS.mainImage.memory, offset, relocate.r_offset, 0, ELF32_Addr)
        System.arraycopy(OS.mainImage.memory, offset + ELF32_Addr, relocate.r_info, 0, ELF32_Word)

        return relocate
    }

    @Throws(IOException::class)
    private fun generateElfRelocateA32(offset: Int): Elf_rela {

        val relocate = Elf_rela()
        relocate.r_offset = ByteArray(ELF32_Addr)
        relocate.r_info = ByteArray(ELF32_Word)
        relocate.r_addend = ByteArray(ELF32_Sword)

        System.arraycopy(OS.mainImage.memory, offset, relocate.r_offset, 0, ELF32_Addr)
        System.arraycopy(OS.mainImage.memory, offset + ELF32_Addr, relocate.r_info, 0, ELF32_Word)
        System.arraycopy(OS.mainImage.memory, offset + ELF32_Addr + ELF32_Word, relocate.r_addend, 0,
                ELF32_Sword)

        return relocate
    }

}
