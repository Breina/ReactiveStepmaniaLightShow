package be.breina.player.openrgb

import be.breina.player.Player
import be.breina.player.openrgb.linking.OpenRgbBootstrapper
import org.junit.jupiter.api.Test
import java.io.File

class TurboMixerTest {

    @Test
    internal fun testTurbo() {
        Player.play(File("src/test/resources/Rose - cornandbeans.yaml")) { songTheming ->
            val bootstrapper = OpenRgbBootstrapper("localhost", 6742, songTheming, TurboSetup())
            bootstrapper.getMainLoop().play()
            bootstrapper.getMixer()
        }
    }
}