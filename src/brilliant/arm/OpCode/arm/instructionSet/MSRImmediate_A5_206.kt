package brilliant.arm.OpCode.arm.instructionSet

import brilliant.arm.OpCode.factory.OpUtil.assert0
import brilliant.arm.OpCode.factory.OpUtil.assert1
import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.arm.instruction.DBG_A8_377
import brilliant.arm.OpCode.arm.instruction.MSR_A8_498
import brilliant.arm.OpCode.arm.instruction.MSR_B8_1996
import brilliant.arm.OpCode.arm.instruction.NOP_A8_510
import brilliant.arm.OpCode.arm.instruction.SEV_A8_606
import brilliant.arm.OpCode.arm.instruction.WFE_A8_1104
import brilliant.arm.OpCode.arm.instruction.WFI_A8_1106
import brilliant.arm.OpCode.arm.instruction.YIELD_A8_1108
import brilliant.arm.OpCode.arm.instruction.support.ParseSupport

object MSRImmediate_A5_206 {
    fun parse(data: Int): ParseSupport {

        val op = getShiftInt(data, 22, 1)
        val op1 = getShiftInt(data, 16, 4)
        val op2 = getShiftInt(data, 0, 8)

        if (op == 0) {
            if (op1 == 0) {
                when (op2) {
                    0 -> return NOP_A8_510.INSTANCE
                    1 -> return YIELD_A8_1108.INSTANCE
                    2 -> return WFE_A8_1104.INSTANCE
                    3 -> return WFI_A8_1106.INSTANCE
                    4 -> return SEV_A8_606.INSTANCE
                }

                if (assert1(op2, 4, 5, 6, 7))
                    return DBG_A8_377.INSTANCE
            }

            if (op1 == 4 || assert0(op1, 0, 1) && assert1(op1, 3))
                return MSR_A8_498.INSTANCE

            if (assert0(op1, 1) && assert1(op1, 0) || assert1(op1, 1))
                return MSR_B8_1996.INSTANCE
        }

        if (op == 1)
            return MSR_B8_1996.INSTANCE

        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }
}
