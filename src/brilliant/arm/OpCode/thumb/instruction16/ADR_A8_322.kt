/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction16

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.parseRegister
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport

class ADR_A8_322 : ParseSupport() {

    override fun getOpCode(data: Int): String? {
        return "ADR"
    }

    override fun getRn(data: Int): String? {
        return parseRegister(getShiftInt(data, 8, 3))
    }

    override fun getRm(data: Int): String? {
        return "" + (getShiftInt(data, 0, 8) shl 2)
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {
        val INSTANCE = ADR_A8_322()
    }
}
