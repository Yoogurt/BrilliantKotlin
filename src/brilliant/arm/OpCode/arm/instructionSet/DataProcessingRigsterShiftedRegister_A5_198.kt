package brilliant.arm.OpCode.arm.instructionSet

import brilliant.arm.OpCode.factory.OpUtil.assert0
import brilliant.arm.OpCode.factory.OpUtil.assert1
import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.arm.instruction.ADC_A8_304
import brilliant.arm.OpCode.arm.instruction.ADD_A8_314
import brilliant.arm.OpCode.arm.instruction.AND_A8_328
import brilliant.arm.OpCode.arm.instruction.ASR_A8_332
import brilliant.arm.OpCode.arm.instruction.BIC_A8_344
import brilliant.arm.OpCode.arm.instruction.CMN_A8_368
import brilliant.arm.OpCode.arm.instruction.CMP_A8_374
import brilliant.arm.OpCode.arm.instruction.EOR_A8_386
import brilliant.arm.OpCode.arm.instruction.LSL_A8_470
import brilliant.arm.OpCode.arm.instruction.LSR_A8_474
import brilliant.arm.OpCode.arm.instruction.MVN_A8_508
import brilliant.arm.OpCode.arm.instruction.ORR_A8_520
import brilliant.arm.OpCode.arm.instruction.ROR_A8_570
import brilliant.arm.OpCode.arm.instruction.RSB_A8_578
import brilliant.arm.OpCode.arm.instruction.RSC_A8_584
import brilliant.arm.OpCode.arm.instruction.SBC_A8_596
import brilliant.arm.OpCode.arm.instruction.SUB_A8_714
import brilliant.arm.OpCode.arm.instruction.TEQ_A8_742
import brilliant.arm.OpCode.arm.instruction.TST_A8_748
import brilliant.arm.OpCode.arm.instruction.support.ParseSupport

object DataProcessingRigsterShiftedRegister_A5_198 {
    fun parse(data: Int): ParseSupport {

        val op1 = getShiftInt(data, 20, 5)
        val op2 = getShiftInt(data, 5, 2)

        if (assert0(op1, 1, 2, 3, 4))
            return AND_A8_328.INSTANCE

        if (assert0(op1, 2, 3, 4) && assert1(op1, 1))
            return EOR_A8_386.INSTANCE

        if (assert0(op1, 1, 3, 4) && assert1(op1, 2))
            return SUB_A8_714.INSTANCE

        if (assert0(op1, 3, 4) && assert1(op1, 1, 2))
            return RSB_A8_578.INSTANCE

        if (assert0(op1, 1, 2, 4) && assert1(op1, 3))
            return ADD_A8_314.INSTANCE

        if (assert0(op1, 2, 4) && assert1(op1, 1, 3))
            return ADC_A8_304.INSTANCE

        if (assert0(op1, 1, 4) && assert1(op1, 2, 3))
            return SBC_A8_596.INSTANCE

        if (assert0(op1, 4) && assert1(op1, 1, 2, 3))
            return RSC_A8_584.INSTANCE

        if (assert0(op1, 0, 3) && assert1(op1, 4))
            return DataProcessingAndMiscellaneousInstructions_A5_196
                    .parse(data)

        when (op1) {
            17 -> return TST_A8_748.INSTANCE
            19 -> return TEQ_A8_742.INSTANCE
            21 -> return CMP_A8_374.INSTANCE
            23 -> return CMN_A8_368.INSTANCE
        }

        if (assert0(op1, 1, 2) && assert1(op1, 3, 4))
            return ORR_A8_520.INSTANCE

        if (assert0(op1, 2) && assert1(op1, 1, 3, 4))
            when (op2) {
                0 -> return LSL_A8_470.INSTANCE
                1 -> return LSR_A8_474.INSTANCE
                2 -> return ASR_A8_332.INSTANCE
                3 -> return ROR_A8_570.INSTANCE
            }

        if (assert0(op1, 1) && assert1(op1, 2, 3, 4))
            return BIC_A8_344.INSTANCE

        if (assert1(op1, 1, 2, 3, 4))
            return MVN_A8_508.INSTANCE

        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }
}
