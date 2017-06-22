/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction16

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.parseRegister
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport

class CMP_A8_372 : ParseSupport() {

    override fun parse(data: Int): String {
        val head = getShiftInt(data, 10, 6)
        if (head == 16)
            return super.parse(data)
        if (head == 17)
            return EncodingT2(data)

        return error(data)
    }

    private fun EncodingT2(data: Int): String {

        val N = getShiftInt(data, 7, 1)
        val Rm = getShiftInt(data, 3, 4)
        val Rn = getShiftInt(data, 0, 3)

        val Rd = N shl 3 or Rn

        val sb = StringBuilder("CMP ")
        sb.append(parseRegister(Rd)).append(" , ")
        sb.append(parseRegister(Rm))

        return sb.toString()
    }

    override fun getOpCode(data: Int): String? {
        return "CMP"
    }

    override fun getRn(data: Int): String? {
        return parseRegister(getShiftInt(data, 0, 3))
    }

    override fun getRm(data: Int): String? {
        return parseRegister(getShiftInt(data, 3, 3))
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = CMP_A8_372()
    }

}
