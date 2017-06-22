package brilliant.arm.OpCode.thumb.instructionSet32

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.thumb.instruction32.CLZ_A8_362
import brilliant.arm.OpCode.thumb.instruction32.QADD_A8_540
import brilliant.arm.OpCode.thumb.instruction32.QDADD_A8_548
import brilliant.arm.OpCode.thumb.instruction32.QDSUB_A8_550
import brilliant.arm.OpCode.thumb.instruction32.QSUB_A8_554
import brilliant.arm.OpCode.thumb.instruction32.RBIT_A8_560
import brilliant.arm.OpCode.thumb.instruction32.REV16_A8_564
import brilliant.arm.OpCode.thumb.instruction32.REVSH_A8_566
import brilliant.arm.OpCode.thumb.instruction32.REV_A8_562
import brilliant.arm.OpCode.thumb.instruction32.SEL_A8_602
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

object MiscellaneousOperationsA6_248 {

    fun parse(data: Int): ParseSupport {

        val op1 = getShiftInt(data, 20, 2)
        val op2 = getShiftInt(data, 4, 2)

        if (op1 == 0)
            when (op2) {
                0 -> return QADD_A8_540.INSTANCE
                1 -> return QDADD_A8_548.INSTANCE
                2 -> return QSUB_A8_554.INSTANCE
                3 -> return QDSUB_A8_550.INSTANCE
            }

        if (op1 == 1)
            when (op2) {
                0 -> return REV_A8_562.INSTANCE
                1 -> return REV16_A8_564.INSTANCE
                2 -> return RBIT_A8_560.INSTANCE
                3 -> return REVSH_A8_566.INSTANCE
            }

        if (op1 == 2 && op2 == 0)
            return SEL_A8_602.INSTANCE

        if (op1 == 3 && op2 == 0)
            return CLZ_A8_362.INSTANCE

        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }
}
