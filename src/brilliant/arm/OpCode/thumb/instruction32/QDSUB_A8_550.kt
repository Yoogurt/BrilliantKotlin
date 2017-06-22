/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction32

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

class QDSUB_A8_550 : ParseSupport() {

    override fun getOpCode(data: Int): String? {
        return "QDSUB"
    }

    override fun getRd(data: Int): Int {
        return getShiftInt(data, 8, 4)
    }

    override fun getRn(data: Int): Int {
        return getShiftInt(data, 0, 4) // <opcode> <Rd> , <Rm> , <Rn> ,exchage
        // Rn and Rm
    }

    override fun getRm(data: Int): Int {
        return getShiftInt(data, 16, 4)
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = QDSUB_A8_550()
    }

}