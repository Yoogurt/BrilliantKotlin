/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.arm.instruction

import brilliant.arm.OpCode.arm.instruction.support.ParseSupport
import brilliant.arm.OpCode.factory.AModeFactory

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt

class LDM_B9_1986 : ParseSupport() {

    override fun getOpCode(data: Int): String {
        return "LDM" + AModeFactory.parse(getShiftInt(data, 24, 1), getShiftInt(data, 23, 1), getShiftInt(data, 16, 4))
    }

    override fun getRn(data: Int): Int {
        return getShiftInt(data, 16, 4)
    }

    override fun isRnwback(data: Int): Boolean {
        return getShiftInt(data, 23, 1) == 1
    }

    override fun getShift(data: Int): Int {
        return getShiftInt(data, 0, 16)
    }

    override fun getCommnet(data: Int): String? {
        return "^"
    }

    override fun shifterRegisterList(): Boolean {
        return true
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = LDM_B9_1986()
    }

}