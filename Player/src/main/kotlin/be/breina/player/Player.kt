package be.breina.player

import be.breina.common.io.SongFile
import be.breina.player.mapping.PlayerMapper
import be.breina.player.model.SongTheming
import be.breina.player.model.SongTimings
import java.io.File
import java.io.FileInputStream

object Player {
    fun play(songFile: File, mixerCreator: (SongTheming) -> Mixer) {
        val songDTO = SongFile().load(songFile)

        val mixer = mixerCreator.invoke(PlayerMapper.toSongTheming(songDTO))

        val songTimings = PlayerMapper.toSongTimings(songDTO)

        play(songTimings, mixer)
    }

    private fun play(song: SongTimings, mixer: Mixer) {
        val player = javazoom.jl.player.Player(FileInputStream(song.music))
        Thread {
            if (song.offset > 0) {
                Thread.sleep(song.offset.toLong())
            }
            player.play()

        }.start()

        if (song.offset < 0) {
            Thread.sleep(-song.offset.toLong())
        }

        val startupTime = System.currentTimeMillis()
        val eventIterator = song.events.iterator()

        while (eventIterator.hasNext()) {
            val event = eventIterator.next()

            val relativeTime = System.currentTimeMillis() - startupTime

            val eventTime = event.time / 1000
            if (relativeTime < eventTime) {
                Thread.sleep(eventTime - relativeTime)
            }

            event.visit(mixer)
        }
    }
}
