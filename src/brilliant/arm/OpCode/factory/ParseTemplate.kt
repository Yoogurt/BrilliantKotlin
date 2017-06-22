package brilliant.arm.OpCode.factory

interface ParseTemplate {

    fun parse(data: Int): String?

    fun performExecuteCommand(data: Int)
}
