/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction32

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.parseRegister
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

class LDRSB_A8_452 : ParseSupport() {

    override fun parse(data: Int): String {

        val sb = StringBuilder("LDRSB.W ")
        val Rt = getShiftInt(data, 12, 4)
        val imm12 = getShiftInt(data, 0, 12)
        val add = getShiftInt(data, 23, 1) == 1
        sb.append(parseRegister(Rt))
        sb.append(" , [PC , #")
        if (!add)
            sb.append("-")
        sb.append(imm12)
        sb.append("]")

        return sb.toString()

    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = LDRSB_A8_452()
    }

}