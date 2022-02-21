package be.breina.parser.model

import be.breina.parser.mixer.Mixer
import kotlin.Throws
import java.io.IOException
import java.io.File
import be.breina.parser.model.Song
import be.breina.parser.model.event.AbstractEvent
import java.awt.image.BufferedImage
import be.breina.parser.model.Chart
import java.util.LinkedList

interface Event {
    fun time(): Long
    fun visit(mixer: Mixer)
}