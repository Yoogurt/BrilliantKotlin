/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction16

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.parseRegister
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport

class MOV_A8_486 : ParseSupport() {

    override fun parse(data: Int): String {

        var head = getShiftInt(data, 8, 8)
        if (head == 70)
            return EncodingT1(data)

        head = getShiftInt(data, 6, 10)
        if (head == 0)
            return EncodingT2(data)

        return error(data)
    }

    private fun EncodingT1(data: Int): String {
        val D = getShiftInt(data, 7, 1)
        val Rm = getShiftInt(data, 3, 4)
        var Rd = getShiftInt(data, 0, 3)

        Rd = D shl 3 or Rd
        val sb = StringBuilder("MOV ")
        sb.append(parseRegister(Rd)).append(" , ").append(parseRegister(Rm))

        if (Rd == Rm)
            sb.append("  (NOP)")

        return sb.toString()
    }

    private fun EncodingT2(data: Int): String {
        val Rm = getShiftInt(data, 3, 3)
        val Rd = getShiftInt(data, 0, 3)
        val sb = StringBuilder("MOV ")
        sb.append(parseRegister(Rd)).append(" , ").append(parseRegister(Rm))

        if (Rd == Rm)
            sb.append("  (NOP)")

        return sb.toString()
    }

    override fun getOpCode(data: Int): String? {
        return null
    }

    override fun getRn(data: Int): String? {
        return null
    }

    override fun getRm(data: Int): String? {
        return null
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = MOV_A8_486()
    }

}