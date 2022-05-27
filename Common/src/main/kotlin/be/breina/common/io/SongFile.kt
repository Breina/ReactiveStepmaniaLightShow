package be.breina.common.io

import be.breina.common.dto.SongDTO
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.io.IOException

class SongFile {
    private val mapper = ObjectMapper(YAMLFactory())

    init {
        mapper.setSerializationInclusion(Include.NON_EMPTY)
        mapper.registerModule(
            KotlinModule.Builder().build()
        )
    }

    @Throws(IOException::class)
    fun save(song: SongDTO) {
        save(song, File(song.title + ".yaml"))
    }

    @Throws(IOException::class)
    fun save(song: SongDTO, file: File) {
        mapper.writeValue(file, song)
    }

    fun load(file: File): SongDTO = mapper.readValue(file)
}