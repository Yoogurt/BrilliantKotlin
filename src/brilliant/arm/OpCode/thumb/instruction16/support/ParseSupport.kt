package brilliant.arm.OpCode.thumb.instruction16.support

import brilliant.arm.OpCode.arm.instructionSet.ConditionParseFactory
import brilliant.arm.OpCode.factory.ParseTemplate

abstract class ParseSupport : ParseTemplate {

    override fun parse(data: Int): String {
        var data = data

        data = data and 0xffff

        verify(data)

        val sb = StringBuilder(getOpCode(data)!!)

        if (enableCond())
            sb.append(ConditionParseFactory.parseCondition(cond))

        sb.append(" ")

        val Rn = getRn(data)
        if (Rn != null)
            sb.append(Rn)

        val Rm = getRm(data)
        if (Rm != null) {
            sb.append(" , ")
            if (isRmRegisterList) {
                sb.append("{")
                sb.append(getRm(data))
                sb.append("}")
            } else if (isRmMenory) {
                sb.append("[")
                sb.append(getRm(data))
                sb.append("]")
            } else
                sb.append(getRm(data))
        }

        val comment = getComment(data)
        if (comment != null)
            sb.append(" ").append(comment)

        return sb.toString()
    }

    protected open fun getOpCode(data: Int): String? {
        return null
    }

    protected open fun getRn(data: Int): String? {
        return null
    }

    protected open fun getRm(data: Int): String? {
        return null
    }

    protected fun verify(data: Int) {

    }

    protected open val isRmRegisterList: Boolean
        get() = false

    protected fun enableCond(): Boolean {
        return false
    }

    protected open val cond: Int
        get() = -1

    protected open val isRmMenory: Boolean
        get() = false

    protected open fun getComment(data: Int): String? {
        return null
    }

    protected fun error(data: Int): String {
        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }

    abstract override fun performExecuteCommand(data: Int)
}
