package brilliant.arm.OpCode.thumb.instructionSet16

import brilliant.arm.OpCode.factory.OpUtil.assert0
import brilliant.arm.OpCode.factory.OpUtil.assert1
import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.thumb.instruction16.LDRB_A8_416
import brilliant.arm.OpCode.thumb.instruction16.LDRB_A8_422
import brilliant.arm.OpCode.thumb.instruction16.LDRH_A8_440
import brilliant.arm.OpCode.thumb.instruction16.LDRH_A8_446
import brilliant.arm.OpCode.thumb.instruction16.LDRSB_A8_454
import brilliant.arm.OpCode.thumb.instruction16.LDRSH_A8_462
import brilliant.arm.OpCode.thumb.instruction16.LDR_A8_406
import brilliant.arm.OpCode.thumb.instruction16.LDR_A8_412
import brilliant.arm.OpCode.thumb.instruction16.STRB_A8_678
import brilliant.arm.OpCode.thumb.instruction16.STRB_A8_682
import brilliant.arm.OpCode.thumb.instruction16.STRH_A8_698
import brilliant.arm.OpCode.thumb.instruction16.STRH_A8_702
import brilliant.arm.OpCode.thumb.instruction16.STR_A8_672
import brilliant.arm.OpCode.thumb.instruction16.STR_A8_676
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport

internal object LoadOrStoreSingleData_A6_227 {
    fun parse(data: Int): ParseSupport {

        val opA = getShiftInt(data, 12, 4)
        val opB = getShiftInt(data, 9, 3)

        if (opA == 5) {
            if (opB == 0)
                return STR_A8_676.INSTANCE

            if (opB == 1)
                return STRH_A8_702.INSTANCE

            if (opB == 2)
                return STRB_A8_682.INSTANCE

            if (opB == 3)
                return LDRSB_A8_454.INSTANCE

            if (opB == 4)
                return LDR_A8_412.INSTANCE

            if (opB == 5)
                return LDRH_A8_446.INSTANCE

            if (opB == 6)
                return LDRB_A8_422.INSTANCE

            if (opB == 7)
                return LDRSH_A8_462.INSTANCE

        }

        if (opA == 6) {
            if (assert0(opB, 2))
                return STR_A8_672.INSTANCE

            if (assert1(opB, 2))
                return LDR_A8_406.INSTANCE
        }

        if (opA == 7) {
            if (assert0(opB, 2))
                return STRB_A8_678.INSTANCE

            if (assert1(opB, 2))
                return LDRB_A8_416.INSTANCE
        }

        if (opA == 8) {
            if (assert0(opB, 2))
                return STRH_A8_698.INSTANCE

            if (assert1(opB, 2))
                return LDRH_A8_440.INSTANCE
        }

        if (opA == 9) {
            if (assert0(opB, 2))
                return STR_A8_672.INSTANCE

            if (assert1(opB, 2))
                return LDR_A8_406.INSTANCE
        }
        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }
}
