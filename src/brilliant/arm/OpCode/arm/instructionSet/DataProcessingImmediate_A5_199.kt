package brilliant.arm.OpCode.arm.instructionSet

import brilliant.arm.OpCode.factory.OpUtil.assert0
import brilliant.arm.OpCode.factory.OpUtil.assert1
import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.arm.instruction.ADC_A8_300
import brilliant.arm.OpCode.arm.instruction.ADD_A8_308
import brilliant.arm.OpCode.arm.instruction.ADR_A8_322
import brilliant.arm.OpCode.arm.instruction.AND_A8_324
import brilliant.arm.OpCode.arm.instruction.BIC_A8_340
import brilliant.arm.OpCode.arm.instruction.CMN_A8_364
import brilliant.arm.OpCode.arm.instruction.CMP_A8_370
import brilliant.arm.OpCode.arm.instruction.EOR_A8_382
import brilliant.arm.OpCode.arm.instruction.MOV_A8_484
import brilliant.arm.OpCode.arm.instruction.MVN_A8_504
import brilliant.arm.OpCode.arm.instruction.ORR_A8_516
import brilliant.arm.OpCode.arm.instruction.RSB_A8_574
import brilliant.arm.OpCode.arm.instruction.RSC_A8_580
import brilliant.arm.OpCode.arm.instruction.SBC_A8_592
import brilliant.arm.OpCode.arm.instruction.SUB_A8_710
import brilliant.arm.OpCode.arm.instruction.TEQ_A8_738
import brilliant.arm.OpCode.arm.instruction.TST_A8_744
import brilliant.arm.OpCode.arm.instruction.support.ParseSupport

object DataProcessingImmediate_A5_199 {
    fun parse(data: Int): ParseSupport {

        val op = getShiftInt(data, 20, 5)
        val Rn = getShiftInt(data, 16, 4)

        if (assert0(op, 1, 2, 3, 4))
            return AND_A8_324.INSTANCE

        if (assert0(op, 2, 3, 4) && assert1(op, 1))
            return EOR_A8_382.INSTANCE

        if (assert0(op, 1, 3, 4) && assert1(op, 2))
            if (Rn != 15)
                return SUB_A8_710.INSTANCE
            else
                return ADR_A8_322.INSTANCE

        if (assert0(op, 3, 4) && assert1(op, 1, 2))
            return RSB_A8_574.INSTANCE

        if (assert0(op, 1, 2, 4) && assert1(op, 3))
            if (Rn != 15)
                return ADD_A8_308.INSTANCE
            else
                return ADR_A8_322.INSTANCE

        if (assert0(op, 2, 4) && assert1(op, 1, 3))
            return ADC_A8_300.INSTANCE

        if (assert0(op, 1, 4) && assert1(op, 2, 3))
            return SBC_A8_592.INSTANCE

        if (assert0(op, 4) && assert1(1, 2, 3))
            return RSC_A8_580.INSTANCE

        if (assert0(op, 0, 3) && assert1(op, 4))
            return DataProcessingAndMiscellaneousInstructions_A5_196
                    .parse(data)

        when (op) {
            17 -> return TST_A8_744.INSTANCE
            19 -> return TEQ_A8_738.INSTANCE
            21 -> return CMP_A8_370.INSTANCE
            23 -> return CMN_A8_364.INSTANCE
        }

        if (assert0(op, 1, 2) && assert1(op, 3, 4))
            return ORR_A8_516.INSTANCE

        if (assert0(op, 2) && assert1(op, 1, 3, 4))
            return MOV_A8_484.INSTANCE

        if (assert0(op, 1) && assert1(op, 2, 3, 4))
            return BIC_A8_340.INSTANCE

        if (assert1(op, 1, 2, 3, 4))
            return MVN_A8_504.INSTANCE

        throw IllegalArgumentException("Unable to deocde instruction " + Integer.toBinaryString(data))
    }
}
