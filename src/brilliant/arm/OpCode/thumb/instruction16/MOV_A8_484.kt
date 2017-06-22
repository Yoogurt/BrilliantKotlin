/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction16

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.parseRegister
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport

class MOV_A8_484 : ParseSupport() {

    override fun getOpCode(data: Int): String? {
        return "MOVS"
    }

    override fun getRn(data: Int): String? {
        return parseRegister(getShiftInt(data, 8, 3))
    }

    override fun getRm(data: Int): String? {
        return "#" + getShiftInt(data, 0, 8)
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = MOV_A8_484()
    }

}