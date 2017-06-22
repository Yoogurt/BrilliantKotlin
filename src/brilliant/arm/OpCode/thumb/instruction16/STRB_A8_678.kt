/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction16

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.parseRegister
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport

class STRB_A8_678 : ParseSupport() {

    override fun parse(data: Int): String {

        val sb = StringBuilder("STRB ")
        val Rt = getShiftInt(data, 0, 3)
        val Rn = getShiftInt(data, 3, 3)
        val imm5 = getShiftInt(data, 6, 5)
        sb.append(parseRegister(Rt))
        sb.append(" , [")

        sb.append(parseRegister(Rn))
        sb.append(" , #")
        sb.append(imm5)

        sb.append("]")

        return sb.toString()

    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = STRB_A8_678()
    }

}