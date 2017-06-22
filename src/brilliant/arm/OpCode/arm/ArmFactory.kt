package brilliant.arm.OpCode.arm

import brilliant.arm.OpCode.factory.OpUtil.assert1
import brilliant.arm.OpCode.arm.instruction.support.ParseSupport
import brilliant.arm.OpCode.arm.instructionSet.ConditionParseFactory
import brilliant.arm.OpCode.arm.instructionSet.UnConditionParseFactory

object ArmFactory {
    fun parse(data: Int): ParseSupport {

        if (!assert1(data, 28, 29, 30, 31))
            return ConditionParseFactory.parseCondition(data)
        else
            return UnConditionParseFactory.parseUncondition(data)

    }
}
