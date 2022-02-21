package be.breina.parser.audio

import java.io.File
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.AudioFormat
import be.breina.parser.audio.AudioFilePlayer
import javax.sound.sampled.DataLine
import javax.sound.sampled.SourceDataLine
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.UnsupportedAudioFileException
import java.lang.IllegalStateException
import javax.sound.sampled.LineUnavailableException
import java.io.IOException
import kotlin.Throws

object AudioFilePlayer {
    fun play(file: File?) {
        try {
            AudioSystem.getAudioInputStream(file).use { `in` ->
                println(`in`.format)
                val outFormat = getOutFormat(`in`.format)
                val info = DataLine.Info(SourceDataLine::class.java, outFormat)
                val line = AudioSystem.getLine(info) as SourceDataLine
                if (line != null) {
                    line.open(outFormat)
                    line.start()
                    val inputMystream = AudioSystem.getAudioInputStream(outFormat, `in`)
                    stream(inputMystream, line)
                    line.drain()
                    line.stop()
                }
            }
        } catch (e: UnsupportedAudioFileException) {
            throw IllegalStateException(e)
        } catch (e: LineUnavailableException) {
            throw IllegalStateException(e)
        } catch (e: IOException) {
            throw IllegalStateException(e)
        }
    }

    private fun getOutFormat(inFormat: AudioFormat): AudioFormat {
        val ch = inFormat.channels
        val rate = inFormat.sampleRate
        return AudioFormat(AudioFormat.Encoding.PCM_SIGNED, rate, 16, ch, ch * 2, rate, false)
    }

    @Throws(IOException::class)
    private fun stream(`in`: AudioInputStream, line: SourceDataLine) {
        val buffer = ByteArray(4096)
        var n = 0
        while (n != -1) {
            line.write(buffer, 0, n)
            n = `in`.read(buffer, 0, buffer.size)
        }
    }
}