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

class LDRD_A8_430 : ParseSupport() {

    override fun parse(data: Int): String {

        val sb = StringBuilder("LDRD")
        sb.append(CondFactory.parse(getCond(data))).append(" ")

        val Rt = getShiftInt(data, 12, 4)
        val Rn = getShiftInt(data, 16, 4)
        val Rm = getShiftInt(data, 0, 4)
        val Rt2 = Rt + 1

        val index = getShiftInt(data, 24, 1) == 1
        val add = getShiftInt(data, 23, 1) == 1
        val wback = !index || getShiftInt(data, 21, 1) == 1

        if (getShiftInt(data, 24, 1) == 0 && getShiftInt(data, 21, 1) == 1)
            error(data)

        sb.append(parseRegister(Rt)).append(" , ")
        sb.append(parseRegister(Rt2))

        sb.append(" , [")

        sb.append(parseRegister(Rn))

        if (!index)
            sb.append("]")

        sb.append(" , ")

        if (!add)
            sb.append("-")

        sb.append(parseRegister(Rm))

        if (index)
            sb.append("]")

        if (wback)
            sb.append("!")

        return sb.toString()
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = LDRD_A8_430()
    }

}