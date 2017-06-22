package brilliant.arm.OpCode.thumb.instructionSet16

import brilliant.arm.OpCode.thumb.instruction16.B_A8_334
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport

internal object ConditionalBranchAndSupervisorCall_A6_229 {
    fun parse(data: Int): ParseSupport {
        return B_A8_334.INSTANCE
    }
}
