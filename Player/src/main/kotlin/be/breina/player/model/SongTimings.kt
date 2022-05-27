package be.breina.player.model

import java.io.File

data class SongTimings(val music: File, val offset: Int, val events: List<MixerEvent>)