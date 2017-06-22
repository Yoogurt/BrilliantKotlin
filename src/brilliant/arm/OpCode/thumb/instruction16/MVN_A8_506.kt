/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction16

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.parseRegister
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport

class MVN_A8_506 : ParseSupport() {

    override fun getOpCode(data: Int): String? {
        return "MVNS"
    }

    override fun getRn(data: Int): String? {
        return parseRegister(getShiftInt(data, 0, 3))
    }

    override fun getRm(data: Int): String? {
        return parseRegister(getShiftInt(data, 3, 3))
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = MVN_A8_506()
    }
}