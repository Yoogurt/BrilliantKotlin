package brilliant.arm.OpCode.factory

object TypeFactory {

    val LSL = 0
    val LSR = 1
    val ASR = 2
    val ROR = 3

    private val TYPE = arrayOf("LSL", "LSR", "ASR", "ROR")

    fun parse(type: Int): String {
        return TYPE[type]
    }
}
