/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction32

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.TypeFactory
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

class UXTAH_A8_810 : ParseSupport() {

    override fun getOpCode(data: Int): String? {
        return "UXTAH"
    }

    override fun getRd(data: Int): Int {
        return getShiftInt(data, 8, 4)
    }

    override fun getRn(data: Int): Int {
        return getShiftInt(data, 16, 4)
    }

    override fun getRm(data: Int): Int {
        return getShiftInt(data, 0, 4)
    }

    override fun getType(data: Int): Int {
        return TypeFactory.ROR // default ror
    }

    override fun getShift(data: Int): Int {
        return getShiftInt(data, 4, 2) shl 3
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = UXTAH_A8_810()
    }

}