/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction32

import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

@Deprecated("")
class HVC_B9_1984 : ParseSupport() {

    override fun getOpCode(data: Int): String? {
        return "HVC.W"
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = HVC_B9_1984()
    }

}