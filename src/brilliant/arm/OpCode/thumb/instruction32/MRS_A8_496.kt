/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction32

import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

@Deprecated("")
class MRS_A8_496 : ParseSupport() {

    override fun getOpCode(data: Int): String? {
        return "MRS.W"
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = MRS_A8_496()
    }

}