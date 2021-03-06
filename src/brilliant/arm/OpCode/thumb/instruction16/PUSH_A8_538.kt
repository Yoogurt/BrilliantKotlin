/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction16

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.parseRegisterList
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport

class PUSH_A8_538 : ParseSupport() {

    override fun parse(data: Int): String {

        val M = getShiftInt(data, 8, 1)
        val registerList = getShiftInt(data, 0, 8)

        val sb = StringBuilder("PUSH ")

        sb.append("{")
        if (registerList != 0) {
            sb.append(parseRegisterList(registerList, -1))
        }
        if (M == 1) {
            if (registerList != 0)
                sb.append(" , ")
            sb.append("LR")
        }
        sb.append("}")

        return sb.toString()
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = PUSH_A8_538()
    }

}
