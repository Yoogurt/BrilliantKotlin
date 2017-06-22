package brilliant.arm.OpCode.arm.instructionSet

import brilliant.arm.OpCode.factory.OpUtil.assert0
import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.arm.instruction.LDREXB_A8_434
import brilliant.arm.OpCode.arm.instruction.LDREXD_A8_436
import brilliant.arm.OpCode.arm.instruction.LDREXH_A8_438
import brilliant.arm.OpCode.arm.instruction.LDREX_A8_432
import brilliant.arm.OpCode.arm.instruction.STREXB_A8_692
import brilliant.arm.OpCode.arm.instruction.STREXD_A8_694
import brilliant.arm.OpCode.arm.instruction.STREXH_A8_696
import brilliant.arm.OpCode.arm.instruction.STREX_A8_690
import brilliant.arm.OpCode.arm.instruction.SWP_A8_722
import brilliant.arm.OpCode.arm.instruction.support.ParseSupport

object SynachronizationPrimitives_A5_205 {
    fun parse(data: Int): ParseSupport {

        val op = getShiftInt(data, 20, 4)

        if (assert0(op, 0, 1, 3))
            return SWP_A8_722.INSTANCE

        when (op) {
            8 -> return STREX_A8_690.INSTANCE

            9 -> return LDREX_A8_432.INSTANCE

            10 -> return STREXD_A8_694.INSTANCE

            11 -> return LDREXD_A8_436.INSTANCE

            12 -> return STREXB_A8_692.INSTANCE

            13 -> return LDREXB_A8_434.INSTANCE

            14 -> return STREXH_A8_696.INSTANCE

            15 -> return LDREXH_A8_438.INSTANCE
        }

        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }
}
