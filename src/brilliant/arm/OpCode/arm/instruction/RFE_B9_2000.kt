/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.arm.instruction

import brilliant.arm.OpCode.arm.instruction.support.ParseSupport

@Deprecated("")
class RFE_B9_2000 : ParseSupport() {

    override fun parse(data: Int): String {
        throw UnsupportedOperationException("RFE no implements")
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = RFE_B9_2000()
    }

}