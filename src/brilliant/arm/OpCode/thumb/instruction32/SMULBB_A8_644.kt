/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction32

import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

@Deprecated("")
class SMULBB_A8_644 : ParseSupport() {

    override fun getOpCode(data: Int): String? {
        return "SMULBB"
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = SMULBB_A8_644()
    }

}