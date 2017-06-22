package brilliant.arm.OpCode.arm.instructionSet

import brilliant.arm.OpCode.arm.instruction.support.ParseSupport

object CoprocessorInstructionAndSupervisorCall_A5_215 {
    fun parse(instruction: Int): ParseSupport {
        throw IllegalArgumentException("Unable to decode instruction "
                + Integer.toBinaryString(instruction)
                + " at Coprocess instructions")
    }
}
