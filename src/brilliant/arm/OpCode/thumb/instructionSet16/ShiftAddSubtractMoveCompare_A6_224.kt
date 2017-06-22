package brilliant.arm.OpCode.thumb.instructionSet16

import brilliant.arm.OpCode.factory.OpUtil.assert0
import brilliant.arm.OpCode.factory.OpUtil.assert1
import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.thumb.instruction16.ADD_A8_306
import brilliant.arm.OpCode.thumb.instruction16.ADD_A8_310
import brilliant.arm.OpCode.thumb.instruction16.ASR_A8_330
import brilliant.arm.OpCode.thumb.instruction16.CMP_A8_370
import brilliant.arm.OpCode.thumb.instruction16.LSL_A8_468
import brilliant.arm.OpCode.thumb.instruction16.LSR_A8_472
import brilliant.arm.OpCode.thumb.instruction16.MOV_A8_484
import brilliant.arm.OpCode.thumb.instruction16.SUB_A8_708
import brilliant.arm.OpCode.thumb.instruction16.SUB_A8_712
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport

internal object ShiftAddSubtractMoveCompare_A6_224 {

    fun parse(data: Int): ParseSupport {

        val OpCode = getShiftInt(data, 9, 5)

        if (assert0(OpCode, 2, 3, 4))
            return LSL_A8_468.INSTANCE

        if (assert0(OpCode, 3, 4) && assert1(OpCode, 2))
            return LSR_A8_472.INSTANCE

        if (assert0(OpCode, 2, 4) && assert1(OpCode, 3))
            return ASR_A8_330.INSTANCE

        when (OpCode) {
            12 -> return ADD_A8_310.INSTANCE
            13 -> return SUB_A8_712.INSTANCE
            14 -> return ADD_A8_306.INSTANCE
            15 -> return SUB_A8_708.INSTANCE
        }

        if (assert0(OpCode, 2, 3) && assert1(OpCode, 4))
            return MOV_A8_484.INSTANCE

        if (assert0(OpCode, 3) && assert1(OpCode, 2, 4))
            return CMP_A8_370.INSTANCE

        if (assert0(OpCode, 2) && assert1(OpCode, 3, 4))
            return ADD_A8_306.INSTANCE

        if (assert1(OpCode, 2, 3, 4))
            return SUB_A8_708.INSTANCE

        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }

}
