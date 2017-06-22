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

class LDRBT_A8_424 : ParseSupport() {

    override fun parse(data: Int): String {
        val A = getShiftInt(data, 25, 1)
        if (A == 0)
            return EncodingA1(data)
        return EncodingA2(data)
    }

    private fun EncodingA2(data: Int): String {

        val sb = StringBuilder("LDRBT")
        sb.append(CondFactory.parse(getCond(data)))
        sb.append(" ")

        val Rt = getShiftInt(data, 12, 4)
        val Rn = getShiftInt(data, 16, 4)
        val imm5 = getShiftInt(data, 7, 5)
        val type = getShiftInt(data, 5, 2)
        val Rm = getShiftInt(data, 0, 4)
        val add = getShiftInt(data, 23, 1) == 1

        sb.append(parseRegister(Rt))
        sb.append(" , [")
        sb.append(parseRegister(Rn))
        sb.append("] , ")

        if (!add)
            sb.append("-")
        sb.append(parseRegister(Rm))
        sb.append(" , ")
        sb.append(TypeFactory.parse(type))
        sb.append(" #")
        sb.append(imm5)

        return sb.toString()
    }

    private fun EncodingA1(data: Int): String {
        val add = getShiftInt(data, 23, 1) == 1
        val Rn = getShiftInt(data, 16, 4)
        val Rt = getShiftInt(data, 12, 4)
        val imm12 = getShiftInt(data, 0, 12)

        val sb = StringBuilder("LDRBT")
        sb.append(CondFactory.parse(getCond(data)))

        sb.append(" ")
        sb.append(parseRegister(Rt))
        sb.append(" , [")
        sb.append(parseRegister(Rn))
        sb.append("] , #")

        if (!add)
            sb.append("-")

        sb.append(imm12)
        return sb.toString()
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = LDRBT_A8_424()
    }

}