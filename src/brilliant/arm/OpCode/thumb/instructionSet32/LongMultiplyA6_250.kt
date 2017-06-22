package brilliant.arm.OpCode.thumb.instructionSet32

import brilliant.arm.OpCode.factory.OpUtil.assert0
import brilliant.arm.OpCode.factory.OpUtil.assert1
import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.thumb.instruction32.SDIV_A8_600
import brilliant.arm.OpCode.thumb.instruction32.SMLALD_A8_628
import brilliant.arm.OpCode.thumb.instruction32.SMLAL_A8_624
import brilliant.arm.OpCode.thumb.instruction32.SMLAL_A8_626
import brilliant.arm.OpCode.thumb.instruction32.SMLSLD_A8_634
import brilliant.arm.OpCode.thumb.instruction32.SMULL_A8_646
import brilliant.arm.OpCode.thumb.instruction32.UDIV_A8_760
import brilliant.arm.OpCode.thumb.instruction32.UMAAL_A8_774
import brilliant.arm.OpCode.thumb.instruction32.UMLAL_A8_776
import brilliant.arm.OpCode.thumb.instruction32.UMULL_A8_778
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

internal object LongMultiplyA6_250 {
    fun parse(data: Int): ParseSupport {

        val op1 = getShiftInt(data, 20, 3)
        val op2 = getShiftInt(data, 4, 4)

        if (op1 == 0)
            if (op2 == 0)
                return SMULL_A8_646.INSTANCE

        if (op1 == 1)
            if (op2 == 15)
                return SDIV_A8_600.INSTANCE

        if (op1 == 2)
            if (op2 == 0)
                return UMULL_A8_778.INSTANCE

        if (op1 == 3)
            if (op2 == 15)
                return UDIV_A8_760.INSTANCE

        if (op1 == 4) {
            if (op2 == 0)
                return SMLAL_A8_624.INSTANCE

            if (assert0(op2, 2) && assert1(op2, 3))
                return SMLAL_A8_626.INSTANCE

            if (assert0(op2, 1) && assert1(op2, 2, 3))
                return SMLALD_A8_628.INSTANCE
        }

        if (op1 == 5)
            if (assert0(op2, 1) && assert1(op2, 2, 3))
                return SMLSLD_A8_634.INSTANCE

        if (op1 == 6) {
            if (op2 == 0)
                return UMLAL_A8_776.INSTANCE

            if (op2 == 6)
                return UMAAL_A8_774.INSTANCE

        }
        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }
}
