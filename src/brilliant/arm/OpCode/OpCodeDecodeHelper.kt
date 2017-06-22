package brilliant.arm.OpCode

import brilliant.arm.OpCode.arm.ArmFactory
import brilliant.arm.OpCode.factory.ParseTemplate
import brilliant.arm.OpCode.thumb.ThumbFactory
import brilliant.elf.content.ELF
import brilliant.elf.util.ByteUtil
import brilliant.elf.vm.OS
import brilliant.elf.vm.Register
import brilliant.elf.vm.Register.RegisterIllegalStateExeception

object OpCodeDecodeHelper {

    private fun decode(data: ByteArray, start: Int, size: Int, isLittleEndian: Boolean, callback: OpCodeHookCallback?) {
        var start = start

        if (callback == null)
            throw NullPointerException("callback must no tbe null")

        val mode = start and 1
        Register.t = mode

        start = start and (-1 xor 1)

        when (Register.t) {
            0 -> decodeArm(data, start, size, isLittleEndian, callback)
            1 -> decodeThumb(data, start, size, isLittleEndian, callback)
            else -> throw RegisterIllegalStateExeception("Flag Rigister has accessed an unpredictable state")
        }
        callback.onFinish()
    }

    fun decode(start: Int, size: Int, elf: ELF, callback: OpCodeHookCallback) {
        decode(OS.mainImage.memory, start, size, elf.isLittleEndian, callback)
    }

    fun isArm(position: Int): Boolean {
        return 0 == position and 1
    }

    fun isThumb(position: Int): Boolean {
        return 1 == position and 1
    }

    private fun decodeArm(data: ByteArray, start: Int, size: Int, isLittleEndian: Boolean,
                          callback: OpCodeHookCallback) {

        var current: Int

        var i = 0
        while (i < size) {

            current = start + i
            Register.PC = current + 8

            val opCode = ByteUtil.bytes2Int32(data, current, 4, isLittleEndian)
            var ret: ParseTemplate? = null

            try {
                ret = ArmFactory.parse(opCode)
            } catch (t: Throwable) {
                if (!callback.exception(t, current, opCode))
                    break
                i += 8
                continue
            }

            if (!callback.ArminstructionDecodeDone(current, opCode, ret))
                break
            i += 8
        }
    }

    private fun decodeThumb(data: ByteArray, start: Int, size: Int, isLittleEndian: Boolean,
                            callback: OpCodeHookCallback) {

        var current: Int

        var i = 0
        while (i <= size) {

            current = start + i
            Register.PC = current + 4

            val opCode = ByteUtil.bytes2Int32(data, current, 2, isLittleEndian)

            var ret: ParseTemplate?
            try {
                ret = ThumbFactory.parse(opCode, true)
            } catch (t: Throwable) {
                if (!callback.exception(t, current, opCode))
                    break
                i += 2
                continue
            }

            if (ret != null) {
                if (!callback.Thumb16instructionDecodeDone(current, opCode.toShort(), ret))
                    break
            } else {

                if (i + 2 >= size)
                    if (!callback.exception(IllegalArgumentException(
                            "Unable to decode instruction " + Integer.toBinaryString(opCode)), current, opCode))
                        break

                val command = opCode shl 16 or ByteUtil.bytes2Int32(data, current + 2, 2, isLittleEndian)

                i += 2

                try {

                    ret = ThumbFactory.parse(command, false)

                } catch (t: Throwable) {
                    if (!callback.exception(t, current, command))
                        break
                    i += 2
                    continue
                }

                if (ret != null) {
                    if (!callback.Thumb32instructionDecodeDone(current, command, ret))
                        break
                } else if (!callback.exception(
                        IllegalArgumentException("Unable to decode instruction " + Integer.toBinaryString(command)),
                        current, command))
                    break
            }
            i += 2
        }
    }

    interface OpCodeHookCallback {
        fun ArminstructionDecodeDone(current: Int, instruction: Int, ret: ParseTemplate): Boolean

        fun Thumb16instructionDecodeDone(current: Int, instruction: Short, ret: ParseTemplate): Boolean

        fun Thumb32instructionDecodeDone(current: Int, instruction: Int, ret: ParseTemplate): Boolean

        fun exception(t: Throwable, current: Int, instruction: Int): Boolean

        fun onFinish()
    }

    fun main(args: Array<String>) {
        val opcode = -1059000172
        val p = ArmFactory.parse(opcode)
        System.out.println(p::class.simpleName)
        System.out.println(p.parse(opcode))
    }
}
