/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction32

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

class SDIV_A8_600 : ParseSupport() {

    override fun getOpCode(data: Int): String? {
        return "SDIV"
    }

    override fun getRd(data: Int): Int {
        return getShiftInt(data, 8, 4)
    }

    override fun getRn(data: Int): Int {
        return getShiftInt(data, 16, 4)
    }

    override fun getRm(data: Int): Int {
        return getShiftInt(data, 0, 4)
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = SDIV_A8_600()
    }

}