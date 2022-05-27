package be.breina.stepmaniaparser.parsing

object ParsingHelper {

    internal fun secondsToMillis(value: String): Int = (value.toFloat() * 1000f).toInt()
}