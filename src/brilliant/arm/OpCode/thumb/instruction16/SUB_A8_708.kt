/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction16

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.parseRegister
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport

class SUB_A8_708 : ParseSupport() {

    override fun parse(data: Int): String {

        var head = getShiftInt(data, 11, 5)
        if (head == 7)
            return super.parse(data)
        head = getShiftInt(data, 9, 7)
        if (head == 15)
            return EncodingT1(data)

        return error(data)
    }

    private fun EncodingT1(data: Int): String {
        val Rd = getShiftInt(data, 0, 3)
        val Rn = getShiftInt(data, 3, 3)
        val imm3 = getShiftInt(data, 6, 3)

        val sb = StringBuilder("SUBS ")
        sb.append(parseRegister(Rd)).append(" , ").append(parseRegister(Rn))
                .append(" , #").append(imm3)
        return sb.toString()
    }

    override fun getOpCode(data: Int): String? {
        return "SUBS"
    }

    override fun getRn(data: Int): String? {
        return parseRegister(getShiftInt(data, 8, 3))
    }

    override fun getRm(data: Int): String? {
        return "#" + getShiftInt(data, 0, 8)
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = SUB_A8_708()
    }

}