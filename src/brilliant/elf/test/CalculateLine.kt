package brilliant.elf.test

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

object CalculateLine {
    @JvmStatic fun main(args: Array<String>) {

        val file = File("src\\brilliant")

        if (!file.exists())
            throw RuntimeException("file not exists")

        println("Total : " + getLine(file) + " lines")

    }

    private fun getLine(file: File): Int {

        println("at " + file.absolutePath)

        val children = file.listFiles()

        var count = 0

        for (f in children)
            if (f.isDirectory)
                count += getLine(f)
            else
                count += getFileLine(f)

        return count

    }

    private fun getFileLine(file: File): Int {

        println("at " + file.absolutePath)

        try {
            val br = BufferedReader(InputStreamReader(
                    FileInputStream(file)))

            var count = 0

            var str: String? = br.readLine()

            while (str != null) {
                if (!str.equals(""))
                    count++
                str = br.readLine();
            }

            br.close()

            return count

        } catch (e: Throwable) {
            e.printStackTrace()
            return 0
        }

    }
}
