package brilliant.arm.OpCode.thumb.instructionSet32

import brilliant.arm.OpCode.factory.OpUtil.assert0
import brilliant.arm.OpCode.factory.OpUtil.assert1
import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.thumb.instruction32.LDRHT_A8_448
import brilliant.arm.OpCode.thumb.instruction32.LDRH_A8_440
import brilliant.arm.OpCode.thumb.instruction32.LDRH_A8_444
import brilliant.arm.OpCode.thumb.instruction32.LDRH_A8_446
import brilliant.arm.OpCode.thumb.instruction32.LDRSHT_A8_464
import brilliant.arm.OpCode.thumb.instruction32.LDRSH_A8_458
import brilliant.arm.OpCode.thumb.instruction32.LDRSH_A8_460
import brilliant.arm.OpCode.thumb.instruction32.LDRSH_A8_462
import brilliant.arm.OpCode.thumb.instruction32.NOP
import brilliant.arm.OpCode.thumb.instruction32.PLD_A8_524
import brilliant.arm.OpCode.thumb.instruction32.PLD_A8_526
import brilliant.arm.OpCode.thumb.instruction32.PLD_A8_528
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

internal object LoadHalfwordA6_240 {
    fun parse(data: Int): ParseSupport {

        val op1 = getShiftInt(data, 23, 2)
        val op2 = getShiftInt(data, 6, 6)
        val Rn = getShiftInt(data, 16, 4)
        val Rt = getShiftInt(data, 12, 4)

        if (op1 == 0 || op1 == 1)
            if (Rn == 15)
                if (Rt != 15)
                    return LDRH_A8_444.INSTANCE
                else
                    return PLD_A8_526.INSTANCE

        if (op1 == 0) {
            if (assert1(op2, 2, 5))
                if (Rn != 15)
                    return LDRH_A8_440.INSTANCE
            if (assert0(op2, 2, 3) && assert1(op2, 4, 5))
                if (Rn != 15)
                    if (Rt != 15)
                        return LDRH_A8_440.INSTANCE
        }

        if (op1 == 1)
            if (Rn != 15)
                if (Rt != 15)
                    return LDRH_A8_440.INSTANCE

        if (op1 == 0) {
            if (op2 == 0)
                if (Rn != 15)
                    if (Rt != 15)
                        return LDRH_A8_446.INSTANCE

            if (assert0(op2, 2) && assert1(op2, 3, 4, 5))
                if (Rn != 15)
                    return LDRHT_A8_448.INSTANCE

            if (op2 == 0)
                if (Rn != 15)
                    if (Rt != 15)
                        return PLD_A8_528.INSTANCE

            if (assert0(op2, 2, 3) && assert1(op2, 4, 5))
                return PLD_A8_524.INSTANCE
        }

        if (op1 == 1)
            if (Rn != 15)
                if (Rt == 15)
                    return PLD_A8_524.INSTANCE

        if (op1 == 2) {
            if (assert1(op2, 2, 5))
                if (Rn != 15)
                    return LDRSH_A8_458.INSTANCE
            if (assert0(op2, 2, 3) && assert1(op2, 4, 5))
                return LDRSH_A8_458.INSTANCE
        }

        if (op1 == 3)
            if (Rn != 15)
                if (Rt != 15)
                    return LDRSH_A8_458.INSTANCE

        if (op1 == 2 || op1 == 3)
            if (Rn != 15)
                if (Rt != 15)
                    return LDRSH_A8_460.INSTANCE

        if (op1 == 2) {
            if (op2 == 0)
                if (Rn != 15)
                    if (Rt != 15)
                        return LDRSH_A8_462.INSTANCE

            if (assert0(op2, 2) && assert1(op2, 3, 4, 5))
                if (Rn != 15)
                    return LDRSHT_A8_464.INSTANCE
        }

        if (op1 == 2) {
            if (op2 == 0)
                if (Rn != 15)
                    if (Rt == 15)
                        return NOP.INSTANCE

            if (assert0(op2, 2) && assert1(op2, 3, 4, 5))
                if (Rn != 15)
                    return NOP.INSTANCE
        }

        if (op1 == 2 || op1 == 3)
            if (Rn == 15)
                if (Rt == 15)
                    return NOP.INSTANCE

        if (op1 == 3)
            if (Rn != 15)
                if (Rt == 15)
                    return NOP.INSTANCE

        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }
}
