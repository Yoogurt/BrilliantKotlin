package brilliant.arm.OpCode.thumb.instructionSet32

import brilliant.arm.OpCode.factory.OpUtil.assert0
import brilliant.arm.OpCode.factory.OpUtil.assert1
import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.thumb.instruction32.ASR_A8_332
import brilliant.arm.OpCode.thumb.instruction32.LSL_A8_470
import brilliant.arm.OpCode.thumb.instruction32.LSR_A8_474
import brilliant.arm.OpCode.thumb.instruction32.ROR_A8_570
import brilliant.arm.OpCode.thumb.instruction32.SXTAB16_A8_726
import brilliant.arm.OpCode.thumb.instruction32.SXTAB_A8_724
import brilliant.arm.OpCode.thumb.instruction32.SXTAH_A8_728
import brilliant.arm.OpCode.thumb.instruction32.SXTB16_A8_732
import brilliant.arm.OpCode.thumb.instruction32.SXTB_A8_730
import brilliant.arm.OpCode.thumb.instruction32.SXTH_A8_734
import brilliant.arm.OpCode.thumb.instruction32.UXTAB16_A8_808
import brilliant.arm.OpCode.thumb.instruction32.UXTAB_A8_806
import brilliant.arm.OpCode.thumb.instruction32.UXTAH_A8_810
import brilliant.arm.OpCode.thumb.instruction32.UXTB16_A8_814
import brilliant.arm.OpCode.thumb.instruction32.UXTB_A8_812
import brilliant.arm.OpCode.thumb.instruction32.UXTH_A8_816
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

internal object DataProcessingA6_245 {
    fun parse(data: Int): ParseSupport {

        val op1 = getShiftInt(data, 20, 4)
        val op2 = getShiftInt(data, 4, 4)
        val Rn = getShiftInt(data, 16, 4)

        if (op1 == 0 || op1 == 1)
            if (op2 == 0)
                return LSL_A8_470.INSTANCE

        if (op1 == 2 || op1 == 3)
            if (op2 == 0)
                return LSR_A8_474.INSTANCE

        if (op1 == 4 || op1 == 5)
            if (op2 == 0)
                return ASR_A8_332.INSTANCE

        if (op1 == 6 || op1 == 15)
            if (op2 == 0)
                return ROR_A8_570.INSTANCE

        if (op1 == 0)
            if (assert1(op2, 3))
                if (Rn != 15)
                    return SXTAH_A8_728.INSTANCE
                else
                    return SXTH_A8_734.INSTANCE

        if (op1 == 1)
            if (assert1(op2, 3))
                if (Rn != 15)
                    return UXTAH_A8_810.INSTANCE
                else
                    return UXTH_A8_816.INSTANCE

        if (op1 == 2)
            if (assert1(op2, 3))
                if (Rn != 15)
                    return SXTAB16_A8_726.INSTANCE
                else
                    return SXTB16_A8_732.INSTANCE

        if (op1 == 3)
            if (assert1(op2, 3))
                if (Rn != 15)
                    return UXTAB16_A8_808.INSTANCE
                else
                    return UXTB16_A8_814.INSTANCE

        if (op1 == 4)
            if (assert1(op2, 3))
                if (Rn != 15)
                    return SXTAB_A8_724.INSTANCE
                else
                    return SXTB_A8_730.INSTANCE

        if (op1 == 5)
            if (assert1(op2, 3))
                if (Rn != 15)
                    return UXTAB_A8_806.INSTANCE
                else
                    return UXTB_A8_812.INSTANCE

        if (assert1(op1, 3)) {
            if (assert0(op2, 2, 3))
                return ParallelAddition_A6_246.parse(data)

            if (assert0(op2, 3) && assert1(op2, 2))
                return ParallelAddition_A6_247.parse(data)

            if (assert0(op1, 2))
                if (assert0(op2, 2) && assert1(op2, 3))
                    return MiscellaneousOperationsA6_248.parse(data)
        }

        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }
}
