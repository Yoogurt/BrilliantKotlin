package brilliant.elf.content

import java.io.IOException
import java.io.RandomAccessFile

import brilliant.elf.content.ELF_SectionHeader.ELF_Shdr
import brilliant.elf.util.ByteUtil

/**
 * @author Yoogurt SectionHeaders are useless when we parse a dynamic library or
 * *         executable Debug Only
 */
@Deprecated("")
internal class ELF_Section @Throws(IOException::class)
constructor(private val raf: RandomAccessFile, header: ELF_Header, mHeader: ELF_Shdr) {

    var header: ELF_Shdr
        protected set
    var parent_header : ELF_Header
    protected set

    protected var s_data: ByteArray

    init {
        this.header = mHeader
        this.parent_header = header
        s_data = ByteArray(ByteUtil.bytes2Int32(mHeader.sh_size!! , parent_header.isLittleEndian))
        loadDataFromStream()
    }

    @Throws(IOException::class)
    private fun loadDataFromStream() {
        raf.seek(ByteUtil.bytes2Int32(header.sh_offset!!,
                header.sh_offset!!.size , parent_header.isLittleEndian).toLong())
        raf.read(s_data)
    }

    override fun toString(): String {
        return String(s_data)
    }

    fun getStringAtIndex(index: ByteArray): String {
        var index_int32 = ByteUtil.bytes2Int32(index , parent_header.isLittleEndian)
        val sb = StringBuilder()

        while (true) {
            if (s_data[index_int32].toInt() != 0)
                sb.append(s_data[index_int32].toChar())
            else
                break
            index_int32++
        }

        return sb.toString()
    }
}
