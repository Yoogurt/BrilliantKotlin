/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.arm.instruction

import brilliant.arm.OpCode.arm.instruction.support.ParseSupport
import brilliant.elf.vm.Register

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.SPSR
import brilliant.arm.OpCode.factory.OpUtil.CPSR

class MRS_A8_496 : ParseSupport() {

    override fun getRd(data: Int): Int {
        return getShiftInt(data, 12, 4)
    }

    override fun getRm(data: Int): Int {
        val read = getShiftInt(data, 22, 1) == 1

        if (read) {
            val mode = Register.m

            if (mode == Register.CPU_MODE_USER || mode == Register.CPU_MODE_SYSTEM)
                error(data)
            else
                return SPSR
        } else {
            return CPSR
        }

        return 0
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = MRS_A8_496()
    }

}