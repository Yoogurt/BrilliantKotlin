package brilliant.arm.OpCode.thumb.instructionSet16

import brilliant.arm.OpCode.factory.OpUtil.assert0
import brilliant.arm.OpCode.factory.OpUtil.assert1
import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.thumb.instruction16.ADD_A8_310
import brilliant.arm.OpCode.thumb.instruction16.BLX_A8_350
import brilliant.arm.OpCode.thumb.instruction16.BX_A8_352
import brilliant.arm.OpCode.thumb.instruction16.CMP_A8_372
import brilliant.arm.OpCode.thumb.instruction16.MOV_A8_486
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport

internal object SpecialDataInstrucationsAndBranchAndExchange_A6_226 {

    fun parse(data: Int): ParseSupport {
        val OpCode = getShiftInt(data, 6, 4)

        if (OpCode == 0)
            return ADD_A8_310.INSTANCE

        if (OpCode == 1 || assert0(OpCode, 2, 3) && assert1(OpCode, 1))
            return ADD_A8_310.INSTANCE

        if (assert0(OpCode, 3) && assert1(OpCode, 2))
            return CMP_A8_372.INSTANCE

        if (OpCode == 8)
            return MOV_A8_486.INSTANCE

        if (OpCode == 9 || assert0(OpCode, 2) && assert1(OpCode, 1, 3))
            return MOV_A8_486.INSTANCE

        if (assert0(OpCode, 1) && assert1(OpCode, 2, 3))
            return BX_A8_352.INSTANCE

        if (assert1(OpCode, 1, 2, 3))
            return BLX_A8_350.INSTANCE

        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }
}
