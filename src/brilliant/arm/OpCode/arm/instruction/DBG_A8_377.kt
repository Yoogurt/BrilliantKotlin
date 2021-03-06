/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.arm.instruction

import brilliant.arm.OpCode.arm.instruction.support.ParseSupport
import brilliant.arm.OpCode.factory.OpUtil.getShiftInt

class DBG_A8_377 : ParseSupport() {

    override fun getOpCode(data: Int): String {
        return "DBG"
    }

    override fun getShift(data: Int): Int {
        return getShiftInt(data, 0, 4)
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = DBG_A8_377()
    }

}