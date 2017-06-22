package brilliant.arm.OpCode.thumb.instructionSet32

import brilliant.arm.OpCode.factory.OpUtil.assert0
import brilliant.arm.OpCode.factory.OpUtil.assert1
import brilliant.arm.OpCode.factory.OpUtil.getShiftInt
import brilliant.arm.OpCode.thumb.instruction32.support.ParseSupport

object Thumb32Factory {

    fun parse(data: Int): ParseSupport {

        val op1 = getShiftInt(data, 27, 2)
        val op2 = getShiftInt(data, 20, 7)
        val op = getShiftInt(data, 15, 1)

        if (op1 == 1) {
            if (assert0(op2, 2, 5, 6))
                return LoadStoreMultipleA6_237.parse(data)

            if (assert0(op2, 5, 6) && assert1(op2, 2))
                return LoadStoreExclusiveA6_238.parse(data)

            if (assert0(op2, 6) && assert1(op2, 5))
                return DataProcessingA6_243.parse(data)

            if (assert1(op2, 6))
                return CoprocessorA6_251.parse(data)
        }

        if (op1 == 2) {

            if (assert0(op2, 5))
                if (op == 0)
                    return DataProcessingA6_231.parse(data)

            if (assert1(op2, 5))
                if (op == 0)
                    return DataProcessingA6_234.parse(data)

            if (op == 1)
                return BranchesA6_235.parse(data)
        }

        if (op1 == 3) {
            if (assert0(op2, 0, 4, 5, 6))
                return StoreSingleDataA6_242.parse(data)

            if (assert0(op2, 1, 2, 5, 6) && assert1(op2, 0))
                return LoadByteA6_241.parse(data)

            if (assert0(op2, 2, 5, 6) && assert1(op2, 0, 1))
                return LoadHalfwordA6_240.parse(data)

            if (assert0(op2, 1, 5, 6) && assert1(op2, 0, 2))
                return LoadWordA6_239.parse(data)

            if (assert0(op2, 5, 6) && assert1(op2, 0, 1, 2))
                throw UnsupportedOperationException("UNDEFINED Instruction")

            if (assert0(op2, 0, 5, 6) && assert1(op2, 4))
                return AdvancedSIMDA7_275.parse(data)

            if (assert0(op2, 4, 6) && assert1(op2, 5))
                return DataProcessingA6_245.parse(data)

            if (assert0(op2, 3, 6) && assert1(op2, 4, 5))
                return MultiplyA6_249.parse(data)

            if (assert0(op2, 6) && assert1(op2, 3, 4, 5))
                return LongMultiplyA6_250.parse(data)

            if (assert1(op2, 6))
                return CoprocessorA6_251.parse(data)
        }

        throw IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(data))
    }
}
