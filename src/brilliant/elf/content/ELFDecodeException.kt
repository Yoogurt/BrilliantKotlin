package brilliant.elf.content

class ELFDecodeException : Exception {

    constructor() : super() {}

    constructor(msg: String) : super(msg) {}

    constructor(t: Throwable, msg: String) : super(msg, t) {}

    companion object {

        /**

         */
        private val serialVersionUID = 6635832896412030305L
    }

}
