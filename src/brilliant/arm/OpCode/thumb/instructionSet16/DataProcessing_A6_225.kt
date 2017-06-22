package brilliant.arm.OpCode.thumb.instructionSet16

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.thumb.instruction16.ADC_A8_302
import brilliant.arm.OpCode.thumb.instruction16.AND_A8_326
import brilliant.arm.OpCode.thumb.instruction16.ASR_A8_332
import brilliant.arm.OpCode.thumb.instruction16.BIC_A8_342
import brilliant.arm.OpCode.thumb.instruction16.CMN_A8_366
import brilliant.arm.OpCode.thumb.instruction16.CMP_A8_372
import brilliant.arm.OpCode.thumb.instruction16.EOR_A8_384
import brilliant.arm.OpCode.thumb.instruction16.LSL_A8_470
import brilliant.arm.OpCode.thumb.instruction16.LSR_A8_474
import brilliant.arm.OpCode.thumb.instruction16.MUL_A8_502
import brilliant.arm.OpCode.thumb.instruction16.MVN_A8_506
import brilliant.arm.OpCode.thumb.instruction16.ORR_A8_518
import brilliant.arm.OpCode.thumb.instruction16.ROR_A8_570
import brilliant.arm.OpCode.thumb.instruction16.RSB_A8_574
import brilliant.arm.OpCode.thumb.instruction16.SBC_A8_594
import brilliant.arm.OpCode.thumb.instruction16.TST_A8_746
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport

internal object DataProcessing_A6_225 {

    fun parse(data: Int): ParseSupport {
        val OpCode = getShiftInt(data, 6, 4)

        when (OpCode) {
            0 -> return AND_A8_326.INSTANCE
            1 -> return EOR_A8_384.INSTANCE
            2 -> return LSL_A8_470.INSTANCE
            3 -> return LSR_A8_474.INSTANCE
            4 -> return ASR_A8_332.INSTANCE
            5 -> return ADC_A8_302.INSTANCE
            6 -> return SBC_A8_594.INSTANCE
            7 -> return ROR_A8_570.INSTANCE
            8 -> return TST_A8_746.INSTANCE
            9 -> return RSB_A8_574.INSTANCE
            10 -> return CMP_A8_372.INSTANCE
            11 -> return CMN_A8_366.INSTANCE
            12 -> return ORR_A8_518.INSTANCE
            13 -> return MUL_A8_502.INSTANCE
            14 -> return BIC_A8_342.INSTANCE
            15 -> return MVN_A8_506.INSTANCE
            else -> throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
        }
    }
}
