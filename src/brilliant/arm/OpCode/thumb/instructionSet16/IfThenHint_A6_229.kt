package brilliant.arm.OpCode.thumb.instructionSet16

import brilliant.arm.OpCode.factory.OpUtil
import brilliant.arm.OpCode.thumb.instruction16.IT_A8_390
import brilliant.arm.OpCode.thumb.instruction16.NOP_A8_510
import brilliant.arm.OpCode.thumb.instruction16.SEV_A8_606
import brilliant.arm.OpCode.thumb.instruction16.WFE_A8_1104
import brilliant.arm.OpCode.thumb.instruction16.WFI_A8_1106
import brilliant.arm.OpCode.thumb.instruction16.YIELD_A8_1108
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport

internal object IfThenHint_A6_229 {

    fun parse(data: Int): ParseSupport {
        val opA = OpUtil.getShiftInt(data, 4, 4)
        val opB = OpUtil.getShiftInt(data, 0, 4)

        if (opB != 0)
            return IT_A8_390.INSTANCE

        when (opA) {
            0 -> return NOP_A8_510.INSTANCE
            1 -> return YIELD_A8_1108.INSTANCE
            2 -> return WFE_A8_1104.INSTANCE
            3 -> return WFI_A8_1106.INSTANCE
            4 -> return SEV_A8_606.INSTANCE
            else -> throw IllegalArgumentException("Unable to decode " + Integer.toBinaryString(data))
        }
    }
}
