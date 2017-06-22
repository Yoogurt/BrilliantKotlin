package brilliant.arm.OpCode.thumb.instructionSet32

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.thumb.instruction32.ADC_A8_302
import brilliant.arm.OpCode.thumb.instruction32.ADD_A8_310
import brilliant.arm.OpCode.thumb.instruction32.AND_A8_326
import brilliant.arm.OpCode.thumb.instruction32.BIC_A8_342
import brilliant.arm.OpCode.thumb.instruction32.CMN_A8_366
import brilliant.arm.OpCode.thumb.instruction32.CMP_A8_372
import brilliant.arm.OpCode.thumb.instruction32.EOR_A8_384
import brilliant.arm.OpCode.thumb.instruction32.MVN_A8_506
import brilliant.arm.OpCode.thumb.instruction32.ORN_A8_514
import brilliant.arm.OpCode.thumb.instruction32.ORR_A8_518
import brilliant.arm.OpCode.thumb.instruction32.PKH_A8_522
import brilliant.arm.OpCode.thumb.instruction32.RSB_A8_576
import brilliant.arm.OpCode.thumb.instruction32.SBC_A8_594
import brilliant.arm.OpCode.thumb.instruction32.SUB_A8_712
import brilliant.arm.OpCode.thumb.instruction32.TEQ_A8_740
import brilliant.arm.OpCode.thumb.instruction32.TST_A8_746
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

internal object DataProcessingA6_243 {
    fun parse(data: Int): ParseSupport {

        val op = getShiftInt(data, 21, 4)
        val Rn = getShiftInt(data, 16, 4)
        val Rd_S = getShiftInt(data, 8, 4) shl 1 or getShiftInt(data, 20, 1)

        if (op == 0)
            if (Rd_S != 31)
                return AND_A8_326.INSTANCE
            else
                return TST_A8_746.INSTANCE

        if (op == 1)
            return BIC_A8_342.INSTANCE

        if (op == 2)
            if (Rn != 15)
                return ORR_A8_518.INSTANCE
            else
                return MoveRegisterA3_244.parse(data)

        if (op == 3)
            if (Rn != 15)
                return ORN_A8_514.INSTANCE
            else
                return MVN_A8_506.INSTANCE

        if (op == 4)
            if (Rd_S != 31)
                return EOR_A8_384.INSTANCE
            else
                return TEQ_A8_740.INSTANCE

        if (op == 6)
            return PKH_A8_522.INSTANCE

        if (op == 8)
            if (Rd_S != 31)
                return ADD_A8_310.INSTANCE
            else
                return CMN_A8_366.INSTANCE

        if (op == 10)
            return ADC_A8_302.INSTANCE

        if (op == 11)
            return SBC_A8_594.INSTANCE

        if (op == 13)
            if (Rd_S != 31)
                return SUB_A8_712.INSTANCE
            else
                return CMP_A8_372.INSTANCE

        if (op == 14)
            return RSB_A8_576.INSTANCE

        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }
}
