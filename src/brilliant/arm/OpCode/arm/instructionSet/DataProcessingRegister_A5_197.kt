package brilliant.arm.OpCode.arm.instructionSet

import brilliant.arm.OpCode.factory.OpUtil.assert0
import brilliant.arm.OpCode.factory.OpUtil.assert1
import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.arm.instruction.ADC_A8_302
import brilliant.arm.OpCode.arm.instruction.ADD_A8_312
import brilliant.arm.OpCode.arm.instruction.AND_A8_326
import brilliant.arm.OpCode.arm.instruction.ASR_A8_330
import brilliant.arm.OpCode.arm.instruction.BIC_A8_342
import brilliant.arm.OpCode.arm.instruction.CMN_A8_366
import brilliant.arm.OpCode.arm.instruction.CMP_A8_372
import brilliant.arm.OpCode.arm.instruction.EOR_A8_384
import brilliant.arm.OpCode.arm.instruction.LSL_A8_468
import brilliant.arm.OpCode.arm.instruction.LSR_A8_472
import brilliant.arm.OpCode.arm.instruction.MOV_A8_488
import brilliant.arm.OpCode.arm.instruction.MVN_A8_506
import brilliant.arm.OpCode.arm.instruction.ORR_A8_518
import brilliant.arm.OpCode.arm.instruction.ROR_A8_568
import brilliant.arm.OpCode.arm.instruction.RRX_A8_572
import brilliant.arm.OpCode.arm.instruction.RSB_A8_576
import brilliant.arm.OpCode.arm.instruction.RSC_A8_582
import brilliant.arm.OpCode.arm.instruction.SBC_A8_594
import brilliant.arm.OpCode.arm.instruction.SUB_A8_712
import brilliant.arm.OpCode.arm.instruction.TEQ_A8_740
import brilliant.arm.OpCode.arm.instruction.TST_A8_746
import brilliant.arm.OpCode.arm.instruction.support.ParseSupport

object DataProcessingRegister_A5_197 {

    fun parse(data: Int): ParseSupport {

        val op = getShiftInt(data, 20, 5)
        val op2 = getShiftInt(data, 5, 2)
        val imm5 = getShiftInt(data, 7, 5)

        if (assert0(op, 1, 2, 3, 4))
            return AND_A8_326.INSTANCE

        if (assert0(op, 2, 3, 4) && assert1(op, 1))
            return EOR_A8_384.INSTANCE

        if (assert0(op, 1, 3, 4) && assert1(op, 2))
            return SUB_A8_712.INSTANCE

        if (assert0(op, 3, 4) && assert1(op, 1, 2))
            return RSB_A8_576.INSTANCE

        if (assert0(op, 1, 2, 4) && assert1(op, 3))
            return ADD_A8_312.INSTANCE

        if (assert0(op, 2, 4) && assert1(op, 1, 3))
            return ADC_A8_302.INSTANCE

        if (assert0(op, 1, 4) && assert1(op, 2, 3))
            return SBC_A8_594.INSTANCE

        if (assert0(op, 4) && assert1(op, 1, 2, 3))
            return RSC_A8_582.INSTANCE

        if (assert0(op, 0, 3) && assert1(op, 4))
            return DataProcessingAndMiscellaneousInstructions_A5_196
                    .parse(data)

        when (op) {
            17 -> return TST_A8_746.INSTANCE
            19 -> return TEQ_A8_740.INSTANCE
            21 -> return CMP_A8_372.INSTANCE
            23 -> return CMN_A8_366.INSTANCE
        }

        if (assert0(op, 1, 2) && assert1(op, 3, 4))
            return ORR_A8_518.INSTANCE

        if (assert0(op, 2))
            if (op2 == 0)
                if (imm5 == 0)
                    return MOV_A8_488.INSTANCE
                else
                    return LSL_A8_468.INSTANCE
            else if (op2 == 1)
                return LSR_A8_472.INSTANCE
            else if (op2 == 2)
                return ASR_A8_330.INSTANCE
            else {
                if (imm5 == 0)
                    return RRX_A8_572.INSTANCE
                else
                    return ROR_A8_568.INSTANCE
            }

        if (assert0(op, 1) && assert1(op, 2, 3, 4))
            return BIC_A8_342.INSTANCE

        if (assert1(op, 1, 2, 3, 4))
            return MVN_A8_506.INSTANCE

        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }

}
