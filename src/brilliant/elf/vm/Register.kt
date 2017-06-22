package brilliant.elf.vm

object Register {

     var R1 = -1
     var R2 = -1
     var R3 = -1
     var R4 = -1
     var R5 = -1
     var R6 = -1
     var R7 = -1
     var R8 = -1
     var R9 = -1
     var R10 = -1
     var R11 = -1
     var R12 = -1
     var SP = -1
     var LR = -1
     var PC = -1

     var APSR = -1
     var CPSR = -1
     var SPCR = -1

    val CPU_MODE_USER = 16
    val CPU_MODE_FIQ = 17
    val CPU_MODE_IRQ = 18
    val CPU_MODE_MANAGE = 19
    val CPU_MODE_INTERRUPT = 23
    val CPU_MODE_UNDEFINE = 27
    val CPU_MODE_SYSTEM = 31

    /**
     * Negative condition flag
     */
    var n: Int
        get() = 1 and (CPSR and 0x80000000.toInt() shr 31)
        set(data) = modifyCPSR(data, 31)

    /**
     * Zero condition flag
     */
    var z: Int
        get() = 1 and (CPSR and 0x40000000 shr 30)
        set(data) = modifyCPSR(data, 30)

    /**
     * Carry condition flag
     */
    var c: Int
        get() = 1 and (CPSR and 0x20000000 shr 29)
        set(data) = modifyCPSR(data, 29)

    /**
     * Overflow condition flag
     */
    var v: Int
        get() = 1 and (CPSR and 0x10000000 shr 28)
        set(data) = modifyCPSR(data, 28)

    /**
     * Zero condition flag
     */
    var q: Int
        get() = 1 and (CPSR and 0x08000000 shr 27)
        set(data) = modifyCPSR(data, 27)

    var j: Int
        get() = 1 and (CPSR and 0x01000000 shr 24)
        set(data) = modifyCPSR(data, 24)

    var ge: Int
        get() = 0xf and (CPSR and 0x000f0000 shr 16)
        set(data) = modifyCPSRWid(data, 16, 4)

    var it: Int
        get() = 0x3f and (CPSR and 0x0000fc00 shr 10)
        set(data) = modifyCPSRWid(data, 10, 6)

    var e: Int
        get() = 1 and (CPSR and 0x00000200 shr 9)
        set(data) = modifyCPSR(data, 9)

    var a: Int
        get() = 1 and (CPSR and 0x00000100 shr 8)
        set(data) = modifyCPSR(data, 8)

    var i: Int
        get() = 1 and (CPSR and 0x00000080 shr 7)
        set(data) = modifyCPSR(data, 7)

    var f: Int
        get() = 1 and (CPSR and 0x00000040 shr 6)
        set(data) = modifyCPSR(data, 6)

    var t: Int
        get() = 1 and (CPSR and 0x00000020 shr 5)
        set(data) = modifyCPSR(data, 5)

    var m: Int
        get() = CPSR and 0x000001f
        set(data) = modifyCPSRWid(data, 0, 5)

    init {
        m = CPU_MODE_USER
    }

    private fun modifyCPSR(data: Int, position: Int) {
        var data = data
        data = data and 1// cut number down
        val helper = -1 xor (1 shl position)

        if (data == 0)
            CPSR = CPSR and helper
        else
            CPSR = CPSR or helper.inv()
    }

    private fun modifyCPSRWid(data: Int, fromIndex: Int, length: Int) {
        var data = data
        data = data and (1 shl length) - 1// cut number down

        val helper = ((1 shl length) - 1 shl fromIndex).inv()

        CPSR = CPSR and helper
        CPSR = CPSR or (data shl fromIndex)
    }

    class RegisterIllegalStateExeception(msg: String) : IllegalStateException(msg) {
        companion object {

            /**

             */
            private val serialVersionUID = 1L
        }

    }
}
