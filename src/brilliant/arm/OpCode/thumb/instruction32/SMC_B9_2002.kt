/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction32

import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

@Deprecated("")
class SMC_B9_2002 : ParseSupport() {

    override fun getOpCode(data: Int): String? {
        return "SMC.W"
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = SMC_B9_2002()
    }

}