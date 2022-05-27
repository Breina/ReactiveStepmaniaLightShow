package be.breina.stepmaniaparser.mapping

import be.breina.common.color.PaletteExtractor
import be.breina.common.color.model.Palette
import be.breina.common.dto.ColorThemeDTO
import be.breina.common.dto.SongDTO
import be.breina.common.dto.event.*
import be.breina.stepmaniaparser.model.Chart
import be.breina.stepmaniaparser.model.Event
import be.breina.stepmaniaparser.model.Song
import be.breina.stepmaniaparser.model.event.*
import java.awt.Color
import java.util.*

object SongMapper {
    fun mapToDTO(song: Song): SongDTO {
        val colors = getColors(song)
        val colorThemeDTO = ColorThemeDTO(colors.primary.rgb, colors.secondary.rgb, colors.tertiary.rgb)

        return SongDTO(
            song.title ?: "Unknown",
            song.subtitle,
            song.artist ?: "Unknown artist",
            colorThemeDTO,
            song.credit ?: "Unknown stepper",
            song.music!!,
            song.offset ?: 0,
            song.sampleStart ?: 0,
            song.sampleLength ?: 10,
            mapToDTO(getHardestChart(song).events),
        )
    }

    private fun getColors(song: Song): Palette {
        val referenceImage = song.background ?: song.banner ?: song.cdTitle
        return if (referenceImage != null) {
            PaletteExtractor.extractColors(referenceImage)
        } else {
            println("Warning: song has no background, banner or cdTitle. Using random colors...")
            val r = Random()
            Palette(
                arrayOf(
                    Color(r.nextInt()),
                    Color(r.nextInt()),
                    Color(r.nextInt())
                )
            )
        }
    }

    private fun getHardestChart(song: Song): Chart {
        assert(!song.charts.isEmpty())
        return song.charts["Challenge"]
            ?: song.charts["Hard"]
            ?: song.charts["Medium"]
            ?: song.charts["Easy"]
            ?: song.charts["Beginner"]
            ?: song.charts.entries.stream().findAny().get().value
    }

    private fun mapToDTO(events: List<Event>): List<AbstractEventDTO> = events.map {
        when (it) {
            is Tap -> IndexSpecificEventDTO(it.time(), IndexSpecificEventType.SINGLE, it.lane)
            is Jump -> IndexSpecificEventDTO(it.time(), IndexSpecificEventType.DOUBLE, it.lane)
            is Hands -> IndexSpecificEventDTO(it.time(), IndexSpecificEventType.TRIPLE, it.lane)
            is Quad -> GenericEventDTO(it.time(), GenericEventType.QUAD)
            is Hold -> IndexSpecificEventDTO(it.time(), IndexSpecificEventType.LONG_START, it.lane)
            is Roll -> IndexSpecificEventDTO(it.time(), IndexSpecificEventType.BURST_START, it.lane)
            is Release -> IndexSpecificEventDTO(it.time(), IndexSpecificEventType.LONG_OR_BURST_END, it.lane)
            is Mine -> IndexSpecificEventDTO(it.time(), IndexSpecificEventType.OMIT, it.lane)
            is TempoChange -> TempoEventDTO(it.time(), it.newTempo)
            is Stop -> PauseEventDTO(it.time(), it.duration)
            else -> {
                throw java.lang.UnsupportedOperationException("Wtf is $it?")
            }
        }
    }
}