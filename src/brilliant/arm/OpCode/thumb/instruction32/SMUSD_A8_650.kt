/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction32

import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

@Deprecated("")
class SMUSD_A8_650 : ParseSupport() {

    override fun getOpCode(data: Int): String? {
        return "SMUSD"
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = SMUSD_A8_650()
    }

}