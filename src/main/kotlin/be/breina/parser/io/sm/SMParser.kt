package be.breina.parser.io.sm

import be.breina.parser.io.Parser
import be.breina.parser.io.sm.ParsingHelper.secondsToMillis
import be.breina.parser.model.Song
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.regex.Pattern
import java.util.stream.Collectors
import javax.imageio.ImageIO


object SMParser : Parser {

    @Throws(IOException::class)
    override fun parse(simFilePath: Path): Song {
        val simFile = Files.readString(simFilePath)
        val folder = simFilePath.parent

        val song = Song()

        val simFileLines = Pattern.compile("(\\r\\n)?;((//.*)?\\r?\\n)*").split(simFile)

        simFileLines
            .map(Pattern.compile(":((//.*)?\\r?\\n *)*")::split)
            .forEach { keyValue ->
                assert(keyValue.isNotEmpty())
                val key = keyValue[0]
                if (key != "#NOTES") {
                    if (keyValue.size == 2) {
                        parseSongMetadataLine(keyValue[0], keyValue[1], song, folder)
                    }
                } else {
                    assert(keyValue.size == 7)
                    ChartParser.parseCharts(song, keyValue[1], keyValue[2], keyValue[3], keyValue[4], keyValue[5], keyValue[6])
                }
            }

        return song
    }

    private fun parseSongMetadataLine(header: String, value: String, song: Song, folder: Path) {
        when (header) {
            "#TITLE" -> song.title = value
            "#SUBTITLE" -> song.subtitle = value
            "#ARTIST" -> song.artist = value
            "#TITLETRANSLIT" -> song.titleTranslit = value
            "#SUBTITLETRANSLIT" -> song.subtitleTranslit = value
            "#ARTISTTRANSLIT" -> song.artistTranslit = value
            "#GENRE" -> song.genre = value
            "#CREDIT" -> song.credit = value
            "#BANNER" -> song.banner = getImage(folder, value)
            "#BACKGROUND" -> song.background = getImage(folder, value)
            "#LYRICSPATH" -> song.lyricsPath = getFile(folder, value)
            "#CDTITLE" -> song.cdTitle = getImage(folder, value)
            "#MUSIC" -> song.music = getFile(folder, value)
            "#OFFSET" -> song.offset = secondsToMillis(value)
            "#BPMS" -> song.bpms = parseMap(value)
            "#STOPS" -> song.stops = parseMap(value)
            "#SAMPLESTART" -> song.sampleStart = secondsToMillis(value)
            "#SAMPLELENGTH" -> song.sampleLength = secondsToMillis(value)
            "#DISPLAYBPM" -> song.displayBpm = value
            "#SELECTABLE" -> song.selectable = value.toBoolean()
        }
    }

    private fun getFile(folder: Path, fileName: String): File? {
        val file = File(folder.toString(), fileName)
        return if (!file.exists()) {
            println("Could not find: " + file.absolutePath)
            null
        } else {
            file;
        }
    }

    private fun getImage(folder: Path, fileName: String): BufferedImage? {
        val file = getFile(folder, fileName)

        return if (file == null) {
            null
        } else {
            try {
                ImageIO.read(file)

            } catch (e: IOException) {
                println(e.message)
                null
            }
        }
    }

    private fun parseMap(text: String): LinkedList<Pair<Int, Int>>? {
        return if (text.isEmpty()) {
            null
        } else {
            val parts = text.split(',')

            parts.stream()
                .map { part -> part.split('=') }
                .map { subParts -> Pair(secondsToMillis(subParts[0]), secondsToMillis(subParts[1])) }
                .collect(Collectors.toCollection(::LinkedList))
        }
    }
}