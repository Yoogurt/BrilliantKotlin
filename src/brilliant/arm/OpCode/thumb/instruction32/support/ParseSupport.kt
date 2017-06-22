package brilliant.arm.OpCode.thumb.instruction32.support

import brilliant.arm.OpCode.factory.OpUtil.parseRegister
import brilliant.arm.OpCode.factory.OpUtil.parseRegisterList
import brilliant.arm.OpCode.factory.CondFactory
import brilliant.arm.OpCode.factory.ParseTemplate
import brilliant.arm.OpCode.factory.TypeFactory

abstract class ParseSupport : ParseTemplate {

    override fun parse(data: Int): String {

        val jump = verify(data)
        if (jump != null)
            return jump

        val sb = StringBuilder(getOpCode(data)!!)

        val cond = getCond(data)
        if (cond != -1)
            sb.append(CondFactory.parse(cond))

        if (getS(data) == 1)
            sb.append("S")

        sb.append(".W")

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
            sb.append(parseRegister(Rm))
        }

        val imm5 = getShift(data)
        val type = getType(data)

        if (imm5 != 0) {
            if (type >= 0) {
                sb.append(" ")
                sb.append(TypeFactory.parse(type))
                sb.append(" ")
            }
            parseShift(sb, imm5)
        }
        val comment = getCommnet(data)

        if (comment != null)
            sb.append(comment)

        return sb.toString()
    }

    private fun parseShift(sb: StringBuilder, imm5: Int) {

        if (shifterRegister()) {
            sb.append(" , ")
            sb.append(parseRegister(imm5))
        } else if (shifterRegisterList())
            sb.append("{").append(parseRegisterList(imm5, -1)).append("}")
        else if (shifterMenory())
            sb.append("[").append(parseRegister(imm5)).append("]")
        else
            sb.append("#").append(imm5)
    }

    protected open fun getOpCode(data: Int): String? {
        return null
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
        return -1
    }

    protected fun error(data: Int): String {
        throw IllegalArgumentException("Unable to decode instruction "
                + Integer.toBinaryString(data) + " at "
                + javaClass.getSimpleName().split("_".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[0])
    }

    protected fun getS(data: Int): Int {
        return -1
    }

    protected fun getType(data: Int): Int {
        return -1
    }

    protected fun getShift(data: Int): Int {
        return 0
    }

    protected fun shifterRegister(): Boolean {
        return false
    }

    protected fun shifterRegisterList(): Boolean {
        return false
    }

    protected fun shifterMenory(): Boolean {
        return false
    }

    protected val isRnMemory: Boolean
        get() = false

    protected fun getCommnet(data: Int): String? {
        return null
    }

    protected fun isRnwback(data: Int): Boolean {
        return false
    }

    abstract override fun performExecuteCommand(data: Int)
}
