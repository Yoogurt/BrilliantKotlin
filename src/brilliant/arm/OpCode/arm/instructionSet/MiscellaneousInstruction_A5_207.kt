package brilliant.arm.OpCode.arm.instructionSet

import brilliant.arm.OpCode.factory.OpUtil.assert0
import brilliant.arm.OpCode.factory.OpUtil.assert1
import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.arm.instruction.BKPT_A8_346
import brilliant.arm.OpCode.arm.instruction.BLX_A8_350
import brilliant.arm.OpCode.arm.instruction.BXJ_A8_354
import brilliant.arm.OpCode.arm.instruction.BX_A8_352
import brilliant.arm.OpCode.arm.instruction.CLZ_A8_362
import brilliant.arm.OpCode.arm.instruction.ERET_B9_1982
import brilliant.arm.OpCode.arm.instruction.HVC_B9_1984
import brilliant.arm.OpCode.arm.instruction.MRS_A8_496
import brilliant.arm.OpCode.arm.instruction.MRS_B9_1992
import brilliant.arm.OpCode.arm.instruction.MSR_A8_500
import brilliant.arm.OpCode.arm.instruction.MSR_B9_1994
import brilliant.arm.OpCode.arm.instruction.MSR_B9_1998
import brilliant.arm.OpCode.arm.instruction.SMC_B9_2002
import brilliant.arm.OpCode.arm.instruction.support.ParseSupport

object MiscellaneousInstruction_A5_207 {

    fun parse(data: Int): ParseSupport {

        val op2 = getShiftInt(data, 4, 3)
        val B = getShiftInt(data, 9, 1)
        val op = getShiftInt(data, 21, 2)
        val op1 = getShiftInt(data, 16, 4)

        when (op2) {
            0 -> {
                if (B == 1) {
                    if (assert0(op, 0))
                        return MRS_B9_1992.INSTANCE
                    else if (assert1(op, 0))
                        return MSR_B9_1994.INSTANCE
                }
                if (B == 0) {
                    if (assert0(op, 0))
                        return MRS_A8_496.INSTANCE
                    if (op == 1) {
                        if (assert0(op1, 0, 1))
                            return MSR_A8_500.INSTANCE
                        if (assert0(op1, 1) && assert1(op1, 0) || assert1(op1, 1))
                            return MSR_B9_1998.INSTANCE
                    }
                    if (op == 3)
                        return MSR_B9_1998.INSTANCE
                }
            }
            1 -> {
                if (op == 1)
                    return BX_A8_352.INSTANCE
                if (op == 3)
                    return CLZ_A8_362.INSTANCE
            }
            2 -> if (op == 1)
                return BXJ_A8_354.INSTANCE
            3 -> if (op == 1)
                return BLX_A8_350.INSTANCE

            5 -> return StauratingAdditionAndSubtraction_A5_202.parse(data)

            6 -> if (op == 3)
                return ERET_B9_1982.INSTANCE
            7 -> when (op) {
                1 -> return BKPT_A8_346.INSTANCE
                2 -> return HVC_B9_1984.INSTANCE
                3 -> return SMC_B9_2002.INSTANCE
            }
        }

        throw IllegalArgumentException("Unable to decode insruction " + Integer.toBinaryString(data))
    }
}
