/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction32

import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

@Deprecated("")
class SMLABB_A8_620 : ParseSupport() {

    override fun getOpCode(data: Int): String? {
        return "SMLABB"
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = SMLABB_A8_620()
    }

}