/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction32

import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

class LSR_A8_472 : ParseSupport() {

    override fun getOpCode(data: Int): String? {
        return "LSR.W"
    }

    override fun getRd(data: Int): Int {
        return -1
    }

    override fun getRn(data: Int): Int {
        return -1
    }

    override fun getRm(data: Int): Int {
        return -1
    }

    override fun getS(data: Int): Int {
        return -1
    }

    override fun getType(data: Int): Int {
        return -1
    }

    override fun getShift(data: Int): Int {
        return 0
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = LSR_A8_472()
    }

}