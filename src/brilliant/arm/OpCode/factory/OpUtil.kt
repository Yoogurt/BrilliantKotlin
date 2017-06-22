package brilliant.arm.OpCode.factory

object OpUtil {

    val R0 = 0x0
    val R1 = 0x1
    val R2 = 0x2
    val R3 = 0x3
    val R4 = 0x4
    val R5 = 0x5
    val R6 = 0x6
    val R7 = 0x7
    val R8 = 0x8
    val R9 = 0x9
    val R10 = 0xA
    val R11 = 0xB
    val R12 = 0xC
    val SP = 0xD
    val LR = 0xE
    val PC = 0xF

    val APSR = 0x10
    val CPSR = 0x11
    val SPSR = 0x12

    /**
     * transform data into binary data and check the specific index is 0 or not

     * @return true means all indexes are 0 , false otherwise
     */
    fun assert0(data: Int, vararg index: Int): Boolean {

        for (mIndex in index)
            if (data.ushr(mIndex) and 1 == 1)
                return false

        return true
    }

    /**
     * transform data into binary data and check the specific index is 1 or not

     * @return true means all indexes are 1 , false otherwise
     */
    fun assert1(data: Int, vararg index: Int): Boolean {
        for (mIndex in index)
            if (data.ushr(mIndex) and 1 == 0)
                return false

        return true
    }

    /**
     * cut the data down form "form" , the rest binary data length will be
     * "length"

     * for instance : getShiftInt(0b1010110 , 3 , 2)

     * 0b 1 0 1 0 1 1 0 from ^ here to ^ to here

     * result 0b10 = 2
     */
    fun getShiftInt(data: Int, from: Int, length: Int): Int {
        return data.ushr(from) and (1 shl length) - 1
    }

    /**
     * R13 -> SP R14 -> LR R15 -> PC
     */
    fun parseRegister(no: Int): String {

        if (no < 13 && no >= 0)
            return "R" + no

        when (no) {
            SP -> return "SP"
            LR -> return "LR"
            PC -> return "PC"
            APSR -> return "APSR"
            CPSR -> return "CPSR"
            SPSR -> return "SPCR"
            else -> throw IllegalArgumentException("Unable to decode register " + no)
        }
    }

    /**
     * transform no into binary , and check the each index is 1 or not 1
     * represents the R(index) register are specific

     * for instance : 0b 1 0 1 0 1 0 mean R5 R3 R1
     */
    fun parseRegisterList(data: Int, discard: Int): String {

        val sb = StringBuilder()

        var i = 0
        while (data.ushr(i) > 0) {
            if (data.ushr(i) and 1 == 1 && discard != i)
                sb.append(parseRegister(i)).append(" , ")
            i++
        }

        if (sb.length > 0)
            sb.setLength(sb.length - 3)
        return sb.toString()
    }

    fun isRigisterInRegisterList(target: Int, registerList: Int): Boolean {

        return 1 == registerList.ushr(target) and 1

    }

    fun signExtend(data: Int, length: Int): Int {
        var data = data

        val sign = 1 and data.ushr(length - 1)

        data = data xor (sign shl length - 1)

        if (sign == 1)
            data = data or (-1 xor (1 shl length - 1) - 1)

        return data
    }

    fun align(data: Int, alignment: Int): Int {
        return data / alignment * alignment
    }

    fun armExpandImm(imm12: Int): Int {
        var imm12 = imm12
        imm12 = imm12 and 0xfff

        val rotation = imm12.ushr(8)
        val result = imm12 and 0xff

        return result.ushr(rotation shl 1) or (result shl (32 - rotation shl 1))
    }

    fun thumbExpandImm(imm12: Int): Int {
        var imm12 = imm12
        imm12 = imm12 and 0xfff
        val high5 = imm12.ushr(7)

        if (high5 == 0 || high5 == 1)
            return imm12 and 0xff
        if (high5 == 2 || high5 == 3)
            return imm12 and 0xff shl 16 or (imm12 and 0xff)
        if (high5 == 4 || high5 == 5)
            return imm12 and 0xff shl 24 or (imm12 and 0xff shl 8)
        if (high5 == 6 || high5 == 7)
            return imm12 and 0xff shl 24 or (imm12 and 0xff shl 16) or (imm12 and 0xff shl 8) or (imm12 and 0xff)

        val base = 8
        imm12 = imm12 and 0xff or 0x80 shl 24
        return imm12.ushr(high5 - base)
    }
}
