/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction32

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.parseRegister
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

class PUSH_A8_538 : ParseSupport() {

    override fun parse(data: Int): String {

        val type = getShiftInt(data, 27, 5)
        if (type == 29)
            return super.parse(data)
        else
            return EncodingT3(data) // push one register
    }

    private fun EncodingT3(data: Int): String {
        val sb = StringBuilder("PUSH.W")
        sb.append(" ")
        sb.append(parseRegister(getShiftInt(data, 12, 4)))
        return sb.toString()
    }

    override fun getOpCode(data: Int): String? {
        return "PUSH"
    }

    override fun getShift(data: Int): Int {
        return getShiftInt(data, 0, 16)
    }

    override fun shifterRegisterList(): Boolean {
        return true
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = PUSH_A8_538()
    }

}