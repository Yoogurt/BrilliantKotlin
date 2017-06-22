/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.arm.instruction

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt

import brilliant.arm.OpCode.arm.instruction.support.ParseSupport
import brilliant.arm.OpCode.factory.Remotable
import brilliant.elf.vm.Register

class LDRSB_A8_452 : ParseSupport(), Remotable {

    override fun parse(data: Int): String {
        return LDRSB_A8_450.INSTANCE.parse(data)
    }

    override fun getCommnet(data: Int): String? {
        if (Register.PC <= 0)
            return null

        return "@" + (offset(data) + Register.PC)
    }

    override fun shifterUsed(): Boolean {
        return true
    }

    override fun performExecuteCommand(data: Int) {}

    override fun offset(data: Int): Int {
        val imm8 = getShiftInt(data, 8, 4) shl 4 or getShiftInt(data, 0, 4)
        val add = getShiftInt(data, 23, 1)
        return if (add == 1) imm8 else -imm8
    }

    companion object {

        val INSTANCE = LDRSB_A8_452()
    }

}