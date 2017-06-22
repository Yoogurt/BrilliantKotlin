package brilliant.arm.OpCode.thumb.instructionSet32

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.thumb.instruction32.MLA_A8_480
import brilliant.arm.OpCode.thumb.instruction32.MLS_A8_482
import brilliant.arm.OpCode.thumb.instruction32.MUL_A8_502
import brilliant.arm.OpCode.thumb.instruction32.SMLABB_A8_620
import brilliant.arm.OpCode.thumb.instruction32.SMLAD_A8_622
import brilliant.arm.OpCode.thumb.instruction32.SMLAWB_A8_630
import brilliant.arm.OpCode.thumb.instruction32.SMLSD_A8_632
import brilliant.arm.OpCode.thumb.instruction32.SMMLA_A8_636
import brilliant.arm.OpCode.thumb.instruction32.SMMLS_A8_638
import brilliant.arm.OpCode.thumb.instruction32.SMMUL_A8_640
import brilliant.arm.OpCode.thumb.instruction32.SMUAD_A8_642
import brilliant.arm.OpCode.thumb.instruction32.SMULBB_A8_644
import brilliant.arm.OpCode.thumb.instruction32.SMULWB_A8_648
import brilliant.arm.OpCode.thumb.instruction32.SMUSD_A8_650
import brilliant.arm.OpCode.thumb.instruction32.USAD8_A8_792
import brilliant.arm.OpCode.thumb.instruction32.USADA8_A8_794
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

internal object MultiplyA6_249 {
    fun parse(data: Int): ParseSupport {

        val op1 = getShiftInt(data, 20, 3)
        val op2 = getShiftInt(data, 4, 2)
        val Ra = getShiftInt(data, 12, 4)

        if (op1 == 0) {
            if (op2 == 0)
                if (Ra != 15)
                    return MLA_A8_480.INSTANCE
                else
                    return MUL_A8_502.INSTANCE

            if (op2 == 1)
                return MLS_A8_482.INSTANCE
        }

        if (op1 == 1)
            if (Ra != 15)
                return SMLABB_A8_620.INSTANCE
            else
                return SMULBB_A8_644.INSTANCE

        if (op1 == 2)
            if (op2 == 0 || op2 == 1)
                if (Ra != 15)
                    return SMLAD_A8_622.INSTANCE
                else
                    return SMUAD_A8_642.INSTANCE

        if (op1 == 3)
            if (op2 == 0 || op2 == 1)
                if (Ra != 15)
                    return SMLAWB_A8_630.INSTANCE
                else
                    return SMULWB_A8_648.INSTANCE

        if (op1 == 4)
            if (op2 == 0 || op2 == 1)
                if (Ra != 15)
                    return SMLSD_A8_632.INSTANCE
                else
                    return SMUSD_A8_650.INSTANCE

        if (op1 == 5)
            if (op2 == 0 || op2 == 1)
                if (Ra != 15)
                    return SMMLA_A8_636.INSTANCE
                else
                    return SMMUL_A8_640.INSTANCE

        if (op1 == 6)
            if (op2 == 0 || op2 == 0)
                return SMMLS_A8_638.INSTANCE

        if (op1 == 7)
            if (op2 == 0)
                if (Ra != 15)
                    return USADA8_A8_794.INSTANCE
                else
                    return USAD8_A8_792.INSTANCE

        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }
}
