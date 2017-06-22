package brilliant.arm.OpCode.thumb.instructionSet32

import brilliant.arm.OpCode.factory.OpUtil.assert0
import brilliant.arm.OpCode.factory.OpUtil.assert1
import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.thumb.instruction32.LDRT_A8_466
import brilliant.arm.OpCode.thumb.instruction32.LDR_A8_406
import brilliant.arm.OpCode.thumb.instruction32.LDR_A8_410
import brilliant.arm.OpCode.thumb.instruction32.LDR_A8_412
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

internal object LoadWordA6_239 {
    fun parse(data: Int): ParseSupport {

        val op1 = getShiftInt(data, 23, 2)
        val op2 = getShiftInt(data, 6, 6)
        val Rn = getShiftInt(data, 16, 4)

        if (op1 == 0)
            if (op2 == 0)
                if (Rn != 15)
                    return LDR_A8_412.INSTANCE

        if (op1 == 0) {
            if (assert1(op2, 2, 5))
                if (Rn != 15)
                    return LDR_A8_406.INSTANCE

            if (assert0(op2, 2, 3) && assert1(op2, 4, 5))
                return LDR_A8_406.INSTANCE
        }

        if (op1 == 1)
            if (Rn != 15)
                return LDR_A8_406.INSTANCE

        if (op1 == 0)
            if (assert0(op2, 2) && assert1(op2, 3, 4, 5))
                if (Rn != 15)
                    return LDRT_A8_466.INSTANCE

        if (op1 == 0 || op1 == 1)
            return LDR_A8_410.INSTANCE

        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }
}
