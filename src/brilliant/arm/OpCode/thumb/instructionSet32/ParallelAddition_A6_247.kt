package brilliant.arm.OpCode.thumb.instructionSet32

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.thumb.instruction32.UADD16_A8_750
import brilliant.arm.OpCode.thumb.instruction32.UADD8_A8_752
import brilliant.arm.OpCode.thumb.instruction32.UASX_A8_754
import brilliant.arm.OpCode.thumb.instruction32.UHADD16_A8_762
import brilliant.arm.OpCode.thumb.instruction32.UHADD8_A8_764
import brilliant.arm.OpCode.thumb.instruction32.UHASX_A8_766
import brilliant.arm.OpCode.thumb.instruction32.UHSAX_A8_768
import brilliant.arm.OpCode.thumb.instruction32.UHSUB16_A8_770
import brilliant.arm.OpCode.thumb.instruction32.UHSUB8_A8_772
import brilliant.arm.OpCode.thumb.instruction32.UQADD16_A8_780
import brilliant.arm.OpCode.thumb.instruction32.UQADD8_A8_782
import brilliant.arm.OpCode.thumb.instruction32.UQASX_A8_784
import brilliant.arm.OpCode.thumb.instruction32.UQSAX_A8_786
import brilliant.arm.OpCode.thumb.instruction32.UQSUB16_A8_788
import brilliant.arm.OpCode.thumb.instruction32.UQSUB8_A8_790
import brilliant.arm.OpCode.thumb.instruction32.USAX_A8_880
import brilliant.arm.OpCode.thumb.instruction32.USUB16_A8_802
import brilliant.arm.OpCode.thumb.instruction32.USUB8_A8_804
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

object ParallelAddition_A6_247 {

    fun parse(data: Int): ParseSupport {

        val op1 = getShiftInt(data, 20, 3)
        val op2 = getShiftInt(data, 4, 2)

        if (op1 == 1 && op2 == 0)
            return UADD16_A8_750.INSTANCE

        if (op1 == 2 && op2 == 0)
            return UASX_A8_754.INSTANCE

        if (op1 == 6 && op2 == 0)
            return USAX_A8_880.INSTANCE

        if (op1 == 5 && op2 == 0)
            return USUB16_A8_802.INSTANCE

        if (op1 == 0 && op2 == 0)
            return UADD8_A8_752.INSTANCE

        if (op1 == 4 && op2 == 0)
            return USUB8_A8_804.INSTANCE

        if (op1 == 1 && op2 == 1)
            return UQADD16_A8_780.INSTANCE

        if (op1 == 2 && op2 == 1)
            return UQASX_A8_784.INSTANCE

        if (op1 == 6 && op2 == 1)
            return UQSAX_A8_786.INSTANCE

        if (op1 == 5 && op2 == 1)
            return UQSUB16_A8_788.INSTANCE

        if (op1 == 0 && op2 == 1)
            return UQADD8_A8_782.INSTANCE

        if (op1 == 4 && op2 == 1)
            return UQSUB8_A8_790.INSTANCE

        if (op1 == 1 && op2 == 2)
            return UHADD16_A8_762.INSTANCE

        if (op1 == 2 && op2 == 2)
            return UHASX_A8_766.INSTANCE

        if (op1 == 6 && op2 == 2)
            return UHSAX_A8_768.INSTANCE

        if (op1 == 5 && op2 == 2)
            return UHSUB16_A8_770.INSTANCE

        if (op1 == 0 && op2 == 2)
            return UHADD8_A8_764.INSTANCE

        if (op1 == 4 && op2 == 2)
            return UHSUB8_A8_772.INSTANCE

        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }

}
