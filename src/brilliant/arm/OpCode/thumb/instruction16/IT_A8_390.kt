/*-------------------------------
 Auto Generated By AutoGenetate.java
     Don't remove or modify
        License GPL/GNU
-------------------------------*/
package brilliant.arm.OpCode.thumb.instruction16

import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.factory.CondFactory
import brilliant.arm.OpCode.thumb.instruction16.support.ParseSupport

class IT_A8_390 : ParseSupport() {

    override fun parse(data: Int): String {

        val firstcond = getShiftInt(data, 4, 4)
        val mask = getShiftInt(data, 0, 4)
        val xyz = parseXYZ(firstcond, mask)

        val sb = StringBuilder()
        sb.append("IT")
        sb.append(xyz)
        sb.append(" ")
        sb.append(CondFactory.parse(firstcond))
        return sb.toString()
    }

    private fun parseXYZ(firstcond: Int, mask: Int): String {
        var mask = mask

        val firstcond_0 = firstcond and 1
        val mask_3 = 1 and (mask shr 3)
        val mask_2 = 1 and (mask shr 2)
        val mask_1 = 1 and (mask shr 1)

        val sb = StringBuilder()

        if (mask_3 == firstcond_0)
            sb.append("T")
        else
            sb.append("E")

        if (mask_2 == firstcond_0)
            sb.append("T")
        else
            sb.append("E")

        if (mask_1 == firstcond_0)
            sb.append("T")
        else
            sb.append("E")

        var sub = 0
        while (mask > 0) {
            if (mask and 1 == 1)
                break
            mask = mask shr 1
            sub++
        }

        return sb.substring(0, sb.length - sub)
    }

    override fun performExecuteCommand(data: Int) {}

    companion object {

        val INSTANCE = IT_A8_390()
    }

}