/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.arm.instruction

import brilliant.arm.OpCode.arm.instruction.support.ParseSupport
import brilliant.arm.OpCode.factory.OpUtil.getShiftInt

class HVC_B9_1984 : ParseSupport() {

    override fun getOpCode(data: Int): String {
        return "HVC"
    }

    override fun getShift(data: Int): Int {
        val imm12 = getShiftInt(data, 8, 12)
        val imm4 = getShiftInt(data, 0, 4)
        return imm12 shl 12 or imm4
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = HVC_B9_1984()
    }

}