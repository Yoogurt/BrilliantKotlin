/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.arm.instruction

import brilliant.arm.OpCode.arm.instruction.support.ParseSupport

@Deprecated("")
class ERET_B9_1982 : ParseSupport() {

    override fun parse(data: Int): String {
        throw UnsupportedOperationException("ERET no implements")
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = ERET_B9_1982()
    }

}