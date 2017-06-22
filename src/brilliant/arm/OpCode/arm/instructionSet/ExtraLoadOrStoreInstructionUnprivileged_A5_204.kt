package brilliant.arm.OpCode.arm.instructionSet

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.arm.instruction.LDRHT_A8_448
import brilliant.arm.OpCode.arm.instruction.LDRSBT_A8_456
import brilliant.arm.OpCode.arm.instruction.LDRSHT_A8_464
import brilliant.arm.OpCode.arm.instruction.STRHT_A8_704
import brilliant.arm.OpCode.arm.instruction.support.ParseSupport

object ExtraLoadOrStoreInstructionUnprivileged_A5_204 {
    fun parse(data: Int): ParseSupport {

        val op2 = getShiftInt(data, 5, 2)
        val op = getShiftInt(data, 20, 1)

        when (op2) {
            1 -> {
                if (op == 0)
                    return STRHT_A8_704.INSTANCE
                if (op == 1)
                    return LDRHT_A8_448.INSTANCE
            }

            2 -> if (op == 1)
                return LDRSBT_A8_456.INSTANCE

            3 -> if (op == 1)
                return LDRSHT_A8_464.INSTANCE
        }

        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }
}
