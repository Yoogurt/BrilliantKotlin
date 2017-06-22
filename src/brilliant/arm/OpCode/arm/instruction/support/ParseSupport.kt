package brilliant.arm.OpCode.arm.instruction.support

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.OpUtil.parseRegister
import brilliant.arm.OpCode.factory.OpUtil.parseRegisterList
import brilliant.arm.OpCode.factory.CondFactory
import brilliant.arm.OpCode.factory.ParseTemplate
import brilliant.arm.OpCode.factory.TypeFactory
import brilliant.elf.vm.Register.it

abstract class ParseSupport : ParseTemplate {

    override fun parse(data: Int): String? {

        val jump = verify(data)
        if (jump != null)
            return jump

        val sb = StringBuilder(getOpCode(data))

        val cond = getCond(data)
        if (cond != -1)
            sb.append(CondFactory.parse(getCond(data)))

        if (getS(data) == 1)
            sb.append("S")

        val Rd = getRd(data)
        val Rn = getRn(data)
        val Rm = getRm(data)

        sb.append(" ")
        if (Rd != -1)
            sb.append(parseRegister(getRd(data)))

        if (Rn != -1) {
            if (Rd != -1)
                sb.append(" , ")

            if (isRnMemory)
                sb.append("[")
            sb.append(parseRegister(Rn))
            if (isRnMemory)
                sb.append("]")

            if (isRnwback(data)) {
                sb.append("!")
            }
        }

        if (Rm != -1) {
            if (Rd != -1 || Rn != -1)
                sb.append(" , ")

            if (isRmMemory)
                sb.append("[")

            sb.append(parseRegister(Rm))

            if (isRmMemory)
                sb.append("] ")
        }

        val shift = getShift(data)
        val type = getType(data)

        if (!shifterUsed()) {
            if (shift != 0) {
                if (type >= 0)
                    sb.append(TypeFactory.parse(type))
                parseShift(sb, shift, type >= 0 || Rm > 0 || Rn > 0 || Rd > 0)
            }
        } else {
            if (type >= 0)
                sb.append(TypeFactory.parse(type))
            parseShift(sb, shift, type >= 0 || Rm > 0 || Rn > 0 || Rd > 0)
        }

        val comment = getCommnet(data)

        if (comment != null)
            sb.append(comment)

        return sb.toString()
    }

    private fun parseShift(sb: StringBuilder, imm5: Int, type: Boolean) {

        sb.append(" ")

        if (type)
            sb.append(", ")

        if (shifterRegister())
            sb.append(parseRegister(imm5))
        else if (shifterRegisterList())
            sb.append("{").append(parseRegisterList(imm5, -1)).append("}")
        else if (shifterMenory())
            sb.append("[").append(parseRegister(imm5)).append("]")
        else
            sb.append("#").append(imm5)
    }

    protected open fun getOpCode(data: Int): String {
        return javaClass.getSimpleName().split("_".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[0]
    }

    protected open fun getRd(data: Int): Int {
        return -1
    }

    protected open fun getRn(data: Int): Int {
        return -1
    }

    protected open fun getRm(data: Int): Int {
        return -1
    }

    protected fun verify(data: Int): String? {
        return null
    }

    protected open fun getCond(data: Int): Int {
        return getShiftInt(data, 28, 4)
    }

    protected fun error(data: Int): String {
        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data) + " at "
                + javaClass.getSimpleName().split("_".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[0])
    }

    protected open fun getS(data: Int): Int {
        return -1
    }

    protected open fun getType(data: Int): Int {
        return -1
    }

    protected open fun getShift(data: Int): Int {
        return 0
    }

    protected open fun shifterRegister(): Boolean {
        return false
    }

    protected open fun shifterRegisterList(): Boolean {
        return false
    }

    protected open fun shifterMenory(): Boolean {
        return false
    }

    protected open val isRnMemory: Boolean
        get() = false

    protected open fun getCommnet(data: Int): String? {
        return null
    }

    protected open fun isRnwback(data: Int): Boolean {
        return false
    }

    protected open val isRmMemory: Boolean
        get() = false

    protected open fun shifterUsed(): Boolean {
        return false
    }

    abstract override fun performExecuteCommand(data: Int)
}
