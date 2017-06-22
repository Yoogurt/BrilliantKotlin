/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction32

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.signExtend
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

class B_A8_334 : ParseSupport() {

    override fun parse(data: Int): String {
        val type = getShiftInt(data, 12, 1)

        if (type == 0)
            return EncodingT3(data)
        else
            return EncodingT4(data)
    }

    fun EncodingT3(data: Int): String {
        return super.parse(data)
    }

    fun EncodingT4(data: Int): String {

        val sb = StringBuilder("B.W #")
        val S = getShiftInt(data, 26, 1)
        val I1 = (getShiftInt(data, 13, 1) xor S).inv() and 1
        val I2 = (getShiftInt(data, 11, 1) xor S).inv() and 1
        val imm10 = getShiftInt(data, 16, 10)
        val imm11 = getShiftInt(data, 0, 11)
        sb.append(signExtend(S shl 24 or (I1 shl 23) or (I2 shl 22) or (imm10 shl 12) or (imm11 shl 1), 25))

        return sb.toString()
    }

    override fun getOpCode(data: Int): String? {
        return "B"
    }

    override fun getCond(data: Int): Int {
        return getShiftInt(data, 22, 4)
    }

    override fun getShift(data: Int): Int {
        val S = getShiftInt(data, 26, 1)
        val J1 = getShiftInt(data, 13, 1)
        val J2 = getShiftInt(data, 11, 1)
        val imm6 = getShiftInt(data, 16, 6)
        val imm11 = getShiftInt(data, 0, 11)
        return signExtend(S shl 20 or (J2 shl 19) or (J1 shl 18) or (imm6 shl 12) or (imm11 shl 1), 21)
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = B_A8_334()
    }

}