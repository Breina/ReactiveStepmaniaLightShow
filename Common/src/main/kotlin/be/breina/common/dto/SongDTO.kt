package be.breina.common.dto

import be.breina.common.dto.event.AbstractEventDTO
import java.io.File


data class SongDTO(
    val title: String,
    val subtitle: String?,
    val artist: String,
    val colorTheme: ColorThemeDTO,
    val credit: String,
    val music: File,
    val offset: Int,
    val sampleStart: Int,
    val sampleLength: Int,
    val events: List<AbstractEventDTO>
)