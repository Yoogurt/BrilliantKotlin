package brilliant.arm.OpCode.factory

object CondFactory {

    private val CONDITION_EXECUTION = arrayOf("EQ", "NE", "CS", "CC", "MI", "PL", "VS", "VC", "HI", "LS", "GE", "LT", "GT", "LE", "")

    val AL = 14

    fun parse(cond: Int): String {
        return CONDITION_EXECUTION[cond]
    }
}
