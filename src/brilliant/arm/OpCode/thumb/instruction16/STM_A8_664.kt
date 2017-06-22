/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction16

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.parseRegister
import brilliant.arm.OpCode.factory.OpUtil.parseRegisterList
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport

class STM_A8_664 : ParseSupport() {

    override fun getOpCode(data: Int): String? {
        return "STM"
    }

    override fun getRn(data: Int): String? {
        return parseRegister(getShiftInt(data, 8, 3)) + "!"
    }

    override fun getRm(data: Int): String? {
        return parseRegisterList(getShiftInt(data, 0, 8), -1)
    }

    protected override val isRmRegisterList: Boolean
        get() = true

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = STM_A8_664()
    }

}
