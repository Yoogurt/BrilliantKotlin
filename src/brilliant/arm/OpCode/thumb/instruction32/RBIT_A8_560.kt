/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction32

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

class RBIT_A8_560 : ParseSupport() {

    override fun getOpCode(data: Int): String? {
        return "RBIT"
    }

    override fun getRd(data: Int): Int {
        return getShiftInt(data, 8, 4)
    }

    override fun getRm(data: Int): Int {

        if (getShiftInt(data, 16, 4) != getShiftInt(data, 0, 4))
            error(data)

        return getShiftInt(data, 16, 4)
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = RBIT_A8_560()
    }

}