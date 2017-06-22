package brilliant.arm.OpCode.thumb.instructionSet32

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.thumb.instruction32.LDMDB_A8_402
import brilliant.arm.OpCode.thumb.instruction32.LDM_A8_396
import brilliant.arm.OpCode.thumb.instruction32.POP_A8_534
import brilliant.arm.OpCode.thumb.instruction32.PUSH_A8_538
import brilliant.arm.OpCode.thumb.instruction32.RFE_B9_2000
import brilliant.arm.OpCode.thumb.instruction32.SRS_B9_2004
import brilliant.arm.OpCode.thumb.instruction32.STMDB_A8_668
import brilliant.arm.OpCode.thumb.instruction32.STM_A8_664
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

internal object LoadStoreMultipleA6_237 {
    fun parse(data: Int): ParseSupport {

        val op = getShiftInt(data, 23, 2)
        val L = getShiftInt(data, 20, 1)
        val W_Rn = getShiftInt(data, 21, 1) shl 4 or getShiftInt(data, 16, 4)

        if (op == 0)
            if (L == 0)
                return SRS_B9_2004.INSTANCE
            else if (L == 1)
                return RFE_B9_2000.INSTANCE

        if (op == 1)
            if (L == 0)
                return STM_A8_664.INSTANCE
            else {
                if (W_Rn != 29)
                    return LDM_A8_396.INSTANCE
                else
                    return POP_A8_534.INSTANCE
            }

        if (op == 2)
            if (L == 0) {
                if (W_Rn != 29)
                    return STMDB_A8_668.INSTANCE
                else
                    return PUSH_A8_538.INSTANCE
            } else if (L == 1)
                return LDMDB_A8_402.INSTANCE

        if (op == 3)
            if (L == 0)
                return SRS_B9_2004.INSTANCE
            else if (L == 1)
                return RFE_B9_2000.INSTANCE

        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }
}
