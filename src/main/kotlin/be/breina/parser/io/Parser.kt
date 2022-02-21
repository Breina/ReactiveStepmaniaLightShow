package be.breina.parser.io

import kotlin.Throws
import java.io.IOException
import java.io.File
import be.breina.parser.model.Song
import be.breina.parser.model.event.AbstractEvent
import java.awt.image.BufferedImage
import be.breina.parser.model.Chart
import java.nio.file.Path
import java.util.LinkedList

interface Parser {
    @Throws(IOException::class)
    fun parse(simFile: Path): Song?
}