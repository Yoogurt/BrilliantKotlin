/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.arm.instruction

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.parseRegister
import brilliant.arm.OpCode.arm.instruction.support.ParseSupport
import brilliant.arm.OpCode.factory.CondFactory
import brilliant.arm.OpCode.factory.TypeFactory

class LDRB_A8_418 : ParseSupport() {

    override fun parse(data: Int): String {

        val sb = StringBuilder("LDR")
        sb.append(CondFactory.parse(getCond(data))).append(" ")

        val Rt = getShiftInt(data, 12, 4)
        val Rn = getShiftInt(data, 16, 4)
        val imm12 = getShiftInt(data, 0, 12)
        val index = getShiftInt(data, 24, 1) == 1
        val add = getShiftInt(data, 23, 1) == 1
        val wback = getShiftInt(data, 21, 1) == 1

        sb.append(parseRegister(Rt)).append(" , [")
        sb.append(parseRegister(Rn))

        if (!index)
            sb.append("] , #")
        else
            sb.append(" , #")

        if (!add)
            sb.append("-")

        sb.append(imm12)

        if (index)
            sb.append("]")

        if (wback)
            sb.append("!")

        return sb.toString()
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = LDRB_A8_418()
    }

}