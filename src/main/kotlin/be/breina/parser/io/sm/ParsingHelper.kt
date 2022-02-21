package be.breina.parser.io.sm

object ParsingHelper {

    internal fun secondsToMillis(value: String): Int = (value.toFloat() * 1000f).toInt()
}