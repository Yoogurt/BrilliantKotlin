package brilliant.arm.OpCode.arm.instructionSet

import brilliant.arm.OpCode.factory.OpUtil.assert0
import brilliant.arm.OpCode.factory.OpUtil.assert1
import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.arm.instruction.LDRBT_A8_424
import brilliant.arm.OpCode.arm.instruction.LDRB_A8_418
import brilliant.arm.OpCode.arm.instruction.LDRB_A8_420
import brilliant.arm.OpCode.arm.instruction.LDRB_A8_422
import brilliant.arm.OpCode.arm.instruction.LDRT_A8_466
import brilliant.arm.OpCode.arm.instruction.LDR_A8_408
import brilliant.arm.OpCode.arm.instruction.LDR_A8_410
import brilliant.arm.OpCode.arm.instruction.LDR_A8_414
import brilliant.arm.OpCode.arm.instruction.STRBT_A8_684
import brilliant.arm.OpCode.arm.instruction.STRB_A8_680
import brilliant.arm.OpCode.arm.instruction.STRB_A8_682
import brilliant.arm.OpCode.arm.instruction.STRT_A8_706
import brilliant.arm.OpCode.arm.instruction.STR_A8_674
import brilliant.arm.OpCode.arm.instruction.STR_A8_676
import brilliant.arm.OpCode.arm.instruction.support.ParseSupport

object LoadAndStore_A5_208 {
    fun parse(data: Int): ParseSupport {

        val A = getShiftInt(data, 25, 1)
        val op1 = getShiftInt(data, 20, 5)
        val B = getShiftInt(data, 4, 1)
        val Rn = getShiftInt(data, 16, 4)

        if (A == 0) {

            if (assert0(op1, 0, 2))
                if (!(assert0(op1, 0, 2, 4) && assert1(op1, 1)))
                    return STR_A8_674.INSTANCE
                else
                    return STRT_A8_706.INSTANCE

            if (assert0(op1, 2) && assert1(op1, 0))
                if (!(assert0(op1, 2, 4) && assert1(op1, 0, 1))) {
                    if (Rn != 15)
                        return LDR_A8_408.INSTANCE
                    else
                        return LDR_A8_410.INSTANCE
                } else
                    return LDRT_A8_466.INSTANCE

            if (assert0(op1, 0) && assert1(op1, 2))
                if (!(assert0(op1, 0, 4) && assert1(op1, 1, 2)))
                    return STRB_A8_680.INSTANCE
                else
                    return STRBT_A8_684.INSTANCE

            if (assert1(op1, 0, 2))
                if (!(assert0(op1, 4) && assert1(op1, 0, 1, 2))) {
                    if (Rn != 15)
                        return LDRB_A8_418.INSTANCE
                    else
                        return LDRB_A8_420.INSTANCE
                } else
                    return LDRBT_A8_424.INSTANCE
        }

        if (A == 1) {

            if (assert0(op1, 0, 2))
                if (!(assert0(op1, 0, 2, 4) && assert1(op1, 1))) {
                    if (B == 0)
                        return STR_A8_676.INSTANCE
                } else if (B == 0)
                    return STRT_A8_706.INSTANCE

            if (assert0(op1, 2) && assert1(op1, 0))
                if (!(assert0(op1, 2, 4) && assert1(op1, 0, 1))) {
                    if (B == 0)
                        return LDR_A8_414.INSTANCE
                } else if (B == 0)
                    return LDRT_A8_466.INSTANCE

            if (assert0(op1, 0) && assert1(op1, 2))
                if (!(assert0(op1, 0, 4) && assert1(op1, 1, 2))) {
                    if (B == 0)
                        return STRB_A8_682.INSTANCE
                } else if (B == 0)
                    return STRBT_A8_684.INSTANCE

            if (assert1(op1, 0, 2))
                if (!(assert0(op1, 4) && assert1(op1, 0, 1, 2))) {
                    if (B == 0)
                        return LDRB_A8_422.INSTANCE
                } else if (B == 0)
                    return LDRBT_A8_424.INSTANCE
        }

        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }
}
