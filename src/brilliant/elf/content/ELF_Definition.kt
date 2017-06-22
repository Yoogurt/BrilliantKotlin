package brilliant.elf.content

import brilliant.elf.util.ByteUtil

internal object ELF_Definition {

    fun ELF_ST_BIND(i: Int): Int {
        return i shr 4
    }

    fun ELF_ST_TYPE(i: Int): Int {
        return i and 0xf
    }

    fun ELF_ST_INFO(b: Int, t: Int): Int {
        return (b shl 4) + (t and 0xf)
    }

    fun ELF_R_TYPE(i: ByteArray): Byte {
        return i[0]
    }

    fun ELF_R_TYPE(i: Int): Byte {
        return i.toByte()
    }

    fun ELF_R_SYM(i: ByteArray): Int {
        return ByteUtil.bytes2Int32(i) shr 8
    }

    fun ELF_R_INFO(s: Int, t: Int): Int {
        return (s shl 8) + t.toByte()
    }
}
