package brilliant.arm.OpCode.thumb

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.ParseTemplate
import brilliant.arm.OpCode.thumb.instructionSet16.Thumb16Factory
import brilliant.arm.OpCode.thumb.instructionSet32.Thumb32Factory

object ThumbFactory {

    fun parse(data: Int, halfword: Boolean): ParseTemplate? {

        if (halfword)
            when (getShiftInt(data, 11, 5)) {
                29, 30, 31 -> return null
                else -> return Thumb16Factory.parse(data)
            }
        else
            return Thumb32Factory.parse(data)
    }
}
