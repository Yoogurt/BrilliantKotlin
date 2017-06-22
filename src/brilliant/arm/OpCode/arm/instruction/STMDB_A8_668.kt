/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.arm.instruction

import brilliant.arm.OpCode.factory.OpUtil.SP
import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.arm.instruction.support.ParseSupport

class STMDB_A8_668 : ParseSupport() {

    override fun getOpCode(data: Int): String {
        val Rn = getShiftInt(data, 16, 4)
        if (Rn == SP)
            return "STMFD"
        return "STMDB"
    }

    override fun getRn(data: Int): Int {
        return getShiftInt(data, 16, 4)
    }

    override fun getShift(data: Int): Int {
        return getShiftInt(data, 0, 16)
    }

    override fun isRnwback(data: Int): Boolean {
        return getShiftInt(data, 21, 1) == 1
    }

    override fun shifterRegisterList(): Boolean {
        return true
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = STMDB_A8_668()
    }

}