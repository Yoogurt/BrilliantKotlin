/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.arm.instruction

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.arm.instruction.support.ParseSupport

class ASR_A8_332 : ParseSupport() {

    override fun getOpCode(data: Int): String {
        return "ASR"
    }

    override fun getRd(data: Int): Int {
        return getShiftInt(data, 12, 4)
    }

    override fun getRn(data: Int): Int {
        return getShiftInt(data, 0, 4)
    }

    override fun getRm(data: Int): Int {
        return getShiftInt(data, 8, 4)
    }

    override fun getS(data: Int): Int {
        return getShiftInt(data, 20, 1)
    }

    override fun getType(data: Int): Int {
        return -1
    }

    override fun getShift(data: Int): Int {
        return 0
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = ASR_A8_332()
    }

}