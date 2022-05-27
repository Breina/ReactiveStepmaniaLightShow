import be.breina.common.io.SongFile
import be.breina.stepmaniaparser.mapping.SongMapper
import be.breina.stepmaniaparser.parsing.SMParser
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.io.path.Path

internal class SongYamlTest {
    @Test
    fun serialize() {
        val song = SMParser.parse(
            Path(
                "src/test/resources/Rose - cornandbeans.sm"
            )
        )

        val songDTO = SongMapper.mapToDTO(song)

        val songYaml = SongFile()
        songYaml.save(songDTO, File("build/tmp/Rose - cornandbeans.yaml"))

        val loadedSong = songYaml.load(File("build/tmp/Rose - cornandbeans.yaml"))

        Assertions.assertEquals(songDTO.artist, loadedSong.artist)
        Assertions.assertEquals(songDTO.colorTheme, loadedSong.colorTheme)
        Assertions.assertEquals(songDTO.credit, loadedSong.credit)
        Assertions.assertEquals(songDTO.offset, loadedSong.offset)
        Assertions.assertTrue(loadedSong.music.isAbsolute)
        Assertions.assertEquals(songDTO.title, loadedSong.title)
        Assertions.assertEquals(songDTO.sampleStart, loadedSong.sampleStart)
        Assertions.assertEquals(songDTO.sampleLength, loadedSong.sampleLength)
    }
}