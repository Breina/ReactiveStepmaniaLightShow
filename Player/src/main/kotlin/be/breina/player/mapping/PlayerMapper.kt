package be.breina.player.mapping

import be.breina.common.dto.SongDTO
import be.breina.common.dto.event.*
import be.breina.player.Mixer
import be.breina.player.model.MixerEvent
import be.breina.player.model.Palette
import be.breina.player.model.SongTimings
import be.breina.player.model.SongTheming
import java.awt.Color

object PlayerMapper {
    fun toSongTheming(songDTO: SongDTO): SongTheming = SongTheming(
        Palette(
            Color(songDTO.colorTheme.primary),
            Color(songDTO.colorTheme.secondary),
            Color(songDTO.colorTheme.tertiary)
        )
    )

    fun toSongTimings(songDTO: SongDTO): SongTimings = SongTimings(songDTO.music, songDTO.offset, toMixerEvents(songDTO.events))

    private fun toMixerEvents(eventDTOs: List<AbstractEventDTO>): List<MixerEvent> {
        return eventDTOs.map { event: AbstractEventDTO ->
            when (event) {
                is IndexSpecificEventDTO -> when (event.type) {
                    IndexSpecificEventType.SINGLE -> MixerEvent(event.time) { it.single(event.index) }
                    IndexSpecificEventType.DOUBLE -> MixerEvent(event.time) { it.double(event.index) }
                    IndexSpecificEventType.TRIPLE -> MixerEvent(event.time) { it.triple(event.index) }
                    IndexSpecificEventType.LONG_START -> MixerEvent(event.time) { it.longStart(event.index) }
                    IndexSpecificEventType.BURST_START -> MixerEvent(event.time) { it.burstStart(event.index) }
                    IndexSpecificEventType.LONG_OR_BURST_END -> MixerEvent(event.time) { it.longOrBurstEnd(event.index) }
                    IndexSpecificEventType.OMIT -> MixerEvent(event.time) { it.omit(event.index) }
                }
                is GenericEventDTO -> when (event.type) {
                    GenericEventType.QUAD -> MixerEvent(event.time, Mixer::quad)
                }
                is TempoEventDTO -> MixerEvent(event.time) { it.tempo(event.newBpm) }
                is PauseEventDTO -> MixerEvent(event.time) { it.pause(event.duration) }
                else -> {
                    throw java.lang.UnsupportedOperationException("Wtf is $event?")
                }
            }
        }
    }
}