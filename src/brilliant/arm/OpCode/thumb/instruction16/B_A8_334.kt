/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction16

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.signExtend
import brilliant.arm.OpCode.factory.CondFactory
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport
import brilliant.elf.vm.Register

class B_A8_334 : ParseSupport() {

    override fun parse(data: Int): String {

        var head = getShiftInt(data, 12, 4)
        if (head == 13)
            return decodeEncodingT1(data)
        else {
            head = getShiftInt(data, 11, 5)
            if (head == 28)
                return decodeEncodingT2(data)
        }
        return error(data)
    }

    private fun decodeEncodingT1(data: Int): String {
        val cond = getShiftInt(data, 8, 4)

        if (cond == 14)
            throw UnsupportedOperationException("UDF no implements")

        if (cond == 15)
            throw UnsupportedOperationException("SVC no implements")

        val sb = StringBuilder("B")

        sb.append(CondFactory.parse(cond))

        sb.append(" #")
        val imm8 = signExtend(getShiftInt(data, 0, 8), 8) shl 1

        sb.append(imm8)

        if (Register.PC >= 0)
            sb.append(" @").append(Integer.toHexString(Register.PC + imm8))

        return sb.toString()

    }

    private fun decodeEncodingT2(data: Int): String {
        val sb = StringBuilder("B")

        sb.append(" #")

        val imm11 = signExtend(getShiftInt(data, 0, 11), 11) shl 1

        sb.append(imm11)

        if (Register.PC >= 0)
            sb.append(" @").append(Integer.toHexString(Register.PC + imm11))

        return sb.toString()
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {
        val INSTANCE = B_A8_334()
    }
}
