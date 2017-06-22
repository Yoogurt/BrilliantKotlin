/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction16

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.isRigisterInRegisterList
import brilliant.arm.OpCode.factory.OpUtil.parseRegister
import brilliant.arm.OpCode.factory.OpUtil.parseRegisterList
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport

class LDM_A8_396 : ParseSupport() {

    override fun getOpCode(data: Int): String? {
        return "LDM"
    }

    override fun getRn(data: Int): String? {
        val Rn = getShiftInt(data, 8, 3)
        val registerList = getShiftInt(data, 0, 8)

        if (!isRigisterInRegisterList(Rn, registerList))
            return parseRegister(Rn) + "!"
        return parseRegister(Rn)
    }

    override fun getRm(data: Int): String? {
        return parseRegisterList(getShiftInt(data, 0, 8),
                getShiftInt(data, 8, 3))
    }

    protected override val isRmRegisterList
        get() = true

    override fun performExecuteCommand(data: Int) {}

    companion object {
        val INSTANCE = LDM_A8_396()
    }

}