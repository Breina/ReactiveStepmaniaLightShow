package be.breina.parser

import be.breina.parser.audio.AudioFilePlayer
import be.breina.parser.audio.AudioFilePlayer2
import be.breina.parser.mixer.Mixer
import be.breina.parser.model.Chart
import be.breina.parser.model.Song
import player.JOrbisPlayer
import java.io.FileInputStream

object Player {
    fun play(song: Song, chart: Chart, mixer: Mixer) {
        if (song.music == null) {
            throw RuntimeException("Can't play song without a music file!")
        }

//        if (song.music!!.extension == "mp3") {
            val player = javazoom.jl.player.Player(FileInputStream(song.music!!))
            Thread {
                if (song.offset != null && song.offset!! > 0) {
                    Thread.sleep(song.offset!!.toLong())
                }
                player.play()

            }.start()

//        } else if (song.music!!.extension == "ogg") {
//            Thread {
//                val player = JOrbisPlayer()
//                if (song.offset != null && song.offset!! > 0) {
//                    Thread.sleep(song.offset!!.toLong())
//                }
//                player.play(song.music!!)
//
//            }.start()
//        }


        if (song.offset != null && song.offset!! < 0) {
            Thread.sleep(-song.offset!!.toLong())
        }

        val startupTime = System.currentTimeMillis()
        val eventIterator = chart.events.iterator()

        while (eventIterator.hasNext()) {
            val event = eventIterator.next()

            val relativeTime = System.currentTimeMillis() - startupTime

            val eventTime = event.time() / 1000
            if (relativeTime < eventTime) {
                Thread.sleep(eventTime - relativeTime)
            }

            event.visit(mixer)
        }
    }
}
