/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction16

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.parseRegister
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport

class LDR_A8_406 : ParseSupport() {

    override fun parse(data: Int): String {

        val head = getShiftInt(data, 11, 5)
        if (head == 13)
            return EncodingT1(data)
        if (head == 19)
            return super.parse(data)

        return error(data)
    }

    private fun EncodingT1(data: Int): String {
        val imm5 = getShiftInt(data, 6, 5)
        val Rn = getShiftInt(data, 3, 3)
        val Rt = getShiftInt(data, 0, 3)
        val sb = StringBuilder("LDR ")
        sb.append(parseRegister(Rt)).append(" , [").append(parseRegister(Rn))
        if (imm5 != 0)
            sb.append(" , #").append(imm5 shl 2)

        sb.append("]")

        return sb.toString()
    }

    override fun getOpCode(data: Int): String? {
        return "LDR"
    }

    override fun getRn(data: Int): String? {
        return parseRegister(getShiftInt(data, 8, 3))
    }

    override fun getRm(data: Int): String? {
        val imm8 = getShiftInt(data, 0, 8)
        val sb = StringBuilder()
        sb.append("[SP")
        if (imm8 != 0)
            sb.append(" , #").append(imm8 shl 2)

        sb.append("]")
        return sb.toString()
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = LDR_A8_406()
    }

}
