/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction32

import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

@Deprecated("")
class PLI_A8_532 : ParseSupport() {

    override fun getOpCode(data: Int): String? {
        return "PLI"
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = PLI_A8_532()
    }

}