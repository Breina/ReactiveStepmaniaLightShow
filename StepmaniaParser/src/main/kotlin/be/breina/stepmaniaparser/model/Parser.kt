package be.breina.stepmaniaparser.model

import java.io.IOException
import java.nio.file.Path

interface Parser {
    @Throws(IOException::class)
    fun parse(simFile: Path): Song?
}