package brilliant.elf.util

class BytesBuilder(capacity: Int) {

    private var mInternal: ByteArray? = null
    private var length: Int = 0

    init {
        length = 0
        mInternal = ByteArray(capacity)
    }

    @Synchronized fun append(data: ByteArray, offset: Int, length: Int): BytesBuilder {

        while (this.length + length > mInternal!!.size) {
            val mTmp = ByteArray(length + mInternal!!.size)
            System.arraycopy(mInternal!!, 0, mTmp, 0, mInternal!!.size)
            mInternal = mTmp
        }

        System.arraycopy(data, offset, mInternal!!, this.length, length)

        this.length += length

        return this
    }

    fun append(data: ByteArray): BytesBuilder {
        return append(data, 0, data.size)
    }

    fun trim2Bytes(): ByteArray {

        if (mInternal!!.size == length)
            return mInternal!!

        val mReturn = ByteArray(length)
        System.arraycopy(mInternal!!, 0, mReturn, 0, length)

        return mReturn

    }

}
