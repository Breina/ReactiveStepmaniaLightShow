package be.breina.player.swing

import be.breina.player.Player
import org.junit.jupiter.api.Test
import java.io.File

internal class SwingMixerTest {

    @Test
    internal fun testRose() {
        Player.play(File("src/test/resources/Rose - cornandbeans.yaml"), ::SwingMixer)
    }
}