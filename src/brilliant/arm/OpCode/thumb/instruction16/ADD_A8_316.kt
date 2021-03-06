/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction16

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.parseRegister
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport

class ADD_A8_316 : ParseSupport() {

    override fun parse(data: Int): String {

        if (getShiftInt(data, 11, 5) == 21)
            return EncodingT1(data)
        if (getShiftInt(data, 7, 9) == 352)
            return EncodingT2(data)

        return error(data)
    }

    private fun EncodingT2(data: Int): String {
        val imm7 = getShiftInt(data, 0, 7)

        val sb = StringBuilder("ADD SP , SP , #")
        sb.append(imm7 shl 2)
        return sb.toString()
    }

    private fun EncodingT1(data: Int): String {
        val Rd = getShiftInt(data, 8, 3)
        val imm8 = getShiftInt(data, 0, 8)

        val sb = StringBuilder("ADD ")
        sb.append(parseRegister(Rd)).append(" , SP , #")

        sb.append(imm8 shl 2)

        return sb.toString()
    }

    override fun getOpCode(data: Int): String? {
        return null
    }

    override fun getRn(data: Int): String? {
        return null
    }

    override fun getRm(data: Int): String? {
        return null
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = ADD_A8_316()
    }

}
