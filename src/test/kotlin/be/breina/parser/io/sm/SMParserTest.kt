package be.breina.parser.io.sm

import org.junit.jupiter.api.Test
import kotlin.io.path.Path

internal class SMParserTest {

    @Test
    internal fun testDragonForce() {
        SMParser.parse(Path("F:\\Games\\Etterna\\Songs\\ODIPack3\\[Nick Skyline] - Fallen World\\oh my fucking god another dragonforce file what bullshit.sm"))

    }
}