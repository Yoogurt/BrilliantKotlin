/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.arm.instruction

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt

import brilliant.arm.OpCode.arm.instruction.support.ParseSupport

class LDREXH_A8_438 : ParseSupport() {

    override fun getRd(data: Int): Int {
        return getShiftInt(data, 12, 4)
    }

    override fun getRn(data: Int): Int {
        return getShiftInt(data, 16, 4)
    }

    protected override val isRnMemory: Boolean
        get() = true

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = LDREXH_A8_438()
    }

}