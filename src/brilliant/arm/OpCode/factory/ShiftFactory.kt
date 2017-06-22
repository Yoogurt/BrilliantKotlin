package brilliant.arm.OpCode.factory

object ShiftFactory {
    fun parse(type: Int, imm5: Int): String? {

        if (imm5 > 32)
            throw IllegalArgumentException(
                    "cann't parse Shift immediate to big " + imm5)

        when (type) {
            0 ->
                return "LSL #" + imm5
            1 ->
                return "LSR #" + imm5
            2 ->
                return "ASR #" + imm5
            3 ->
                return "RRX #" + imm5
            else -> throw IllegalArgumentException("cann't parse Shift type " + Integer.toBinaryString(type))
        }
    }
}
