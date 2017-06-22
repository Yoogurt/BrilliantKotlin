package brilliant.arm.OpCode.thumb.instructionSet32

import brilliant.arm.OpCode.factory.OpUtil.assert0
import brilliant.arm.OpCode.factory.OpUtil.assert1
import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.thumb.instruction32.BL_A8_348
import brilliant.arm.OpCode.thumb.instruction32.BXJ_A8_354
import brilliant.arm.OpCode.thumb.instruction32.B_A8_334
import brilliant.arm.OpCode.thumb.instruction32.ERET_B9_1982
import brilliant.arm.OpCode.thumb.instruction32.HVC_B9_1984
import brilliant.arm.OpCode.thumb.instruction32.MRS_A8_496
import brilliant.arm.OpCode.thumb.instruction32.MRS_B9_1990
import brilliant.arm.OpCode.thumb.instruction32.MRS_B9_1992
import brilliant.arm.OpCode.thumb.instruction32.MSR_A8_500
import brilliant.arm.OpCode.thumb.instruction32.MSR_B9_1994
import brilliant.arm.OpCode.thumb.instruction32.MSR_B9_1998
import brilliant.arm.OpCode.thumb.instruction32.SMC_B9_2002
import brilliant.arm.OpCode.thumb.instruction32.SUBS_B9_2010
import brilliant.arm.OpCode.thumb.instruction32.UDF_A8_758
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

internal object BranchesA6_235 {
    fun parse(data: Int): ParseSupport {

        val op1 = getShiftInt(data, 12, 3)
        val imm8 = getShiftInt(data, 0, 8)
        val op = getShiftInt(data, 20, 7)
        val op2 = getShiftInt(data, 8, 4)

        if (op1 == 0 || op1 == 2) {
            if (!assert1(op, 3, 4, 5))
                return B_A8_334.INSTANCE

            if (assert1(imm8, 5))
                if (assert0(op, 1, 2, 6) && assert1(op, 3, 4, 5))
                    return MSR_B9_1994.INSTANCE

            if (assert0(imm8, 5)) {
                if (op == 56) {
                    if (assert0(op2, 0, 1))
                        return MSR_A8_500.INSTANCE
                    else if (assert0(op2, 1) && assert1(op2, 0) || assert1(op2, 1))
                        return MSR_B9_1998.INSTANCE
                }

                if (op == 57)
                    return MSR_B9_1998.INSTANCE
            }

            when (op) {
                58 -> return ChangeProcessorStateA6_236.parse(data)

                59 -> return MiscellaneousInstruction_A6_237.parse(data)

                60 -> return BXJ_A8_354.INSTANCE

                29 ->
                    if (imm8 == 0)
                        return ERET_B9_1982.INSTANCE
                    else
                        return SUBS_B9_2010.INSTANCE

                62 ->
                    if (assert0(imm8, 5))
                        return MRS_A8_496.INSTANCE
                    else
                        return MRS_B9_1992.INSTANCE

                63 -> if (assert0(imm8, 5))
                    return MRS_B9_1990.INSTANCE
                else
                    return MRS_B9_1992.INSTANCE
            }
        }

        if (op1 == 0) {
            if (op == 126)
                return HVC_B9_1984.INSTANCE

            if (op == 127)
                return SMC_B9_2002.INSTANCE
        }

        if (op1 == 1 || op1 == 3)
            return B_A8_334.INSTANCE

        if (op1 == 2)
            if (op == 127)
                return UDF_A8_758.INSTANCE

        if (op1 == 4 || op == 6)
            return BL_A8_348.INSTANCE

        if (op1 == 5 || op1 == 7)
            return BL_A8_348.INSTANCE

        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }
}
