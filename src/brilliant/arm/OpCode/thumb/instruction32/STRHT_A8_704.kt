/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction32

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.parseRegister
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

class STRHT_A8_704 : ParseSupport() {

    override fun parse(data: Int): String {

        val sb = StringBuilder("STRBT.W ")
        val Rt = getShiftInt(data, 12, 4)
        val Rn = getShiftInt(data, 16, 4)
        val imm8 = getShiftInt(data, 0, 8)

        sb.append(parseRegister(Rt))
        sb.append(" , [")
        sb.append(parseRegister(Rn))
        sb.append(" , #")
        sb.append(imm8)
        sb.append("]")

        return sb.toString()
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = STRHT_A8_704()
    }

}