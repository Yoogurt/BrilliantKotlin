package brilliant.arm.OpCode.thumb.instructionSet32

import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

internal object CoprocessorA6_251 {
    fun parse(data: Int): ParseSupport {
        throw IllegalArgumentException("Unable to decode instruction "
                + Integer.toBinaryString(data) + " at Coprocessor instuctions")
    }
}
