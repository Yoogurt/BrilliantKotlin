package brilliant.arm.OpCode.arm.instructionSet

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.arm.instruction.SMLABB_A8_620
import brilliant.arm.OpCode.arm.instruction.SMLALBB_A8_626
import brilliant.arm.OpCode.arm.instruction.SMLAWB_A8_630
import brilliant.arm.OpCode.arm.instruction.SMULBB_A8_644
import brilliant.arm.OpCode.arm.instruction.SMULWB_A8_648
import brilliant.arm.OpCode.arm.instruction.support.ParseSupport

object HalfwordAndMultiplyAccumulate_A5_203 {
    fun parse(data: Int): ParseSupport {

        val op1 = getShiftInt(data, 21, 2)
        val op = getShiftInt(data, 5, 1)

        when (op1) {
            0 -> return SMLABB_A8_620.INSTANCE
            1 -> {
                if (op == 0)
                    return SMLAWB_A8_630.INSTANCE
                if (op == 1)
                    return SMULWB_A8_648.INSTANCE
            }

            2 -> return SMLALBB_A8_626.INSTANCE
            3 -> return SMULBB_A8_644.INSTANCE
        }

        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }
}
