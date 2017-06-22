/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction32

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

class AND_A8_326 : ParseSupport() {

    override fun getOpCode(data: Int): String? {
        return "AND"
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

    override fun getS(data: Int): Int {
        return getShiftInt(data, 20, 1)
    }

    override fun getType(data: Int): Int {
        return getShiftInt(data, 4, 2)
    }

    override fun getShift(data: Int): Int {
        val imm3 = getShiftInt(data, 12, 3)
        val imm2 = getShiftInt(data, 6, 2)
        return imm3 shl 2 or imm2
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = AND_A8_326()
    }

}