/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction32

import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

@Deprecated("")
class PLD_A8_524 : ParseSupport() {

    override fun getOpCode(data: Int): String? {
        return "PLD"
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = PLD_A8_524()
    }

}