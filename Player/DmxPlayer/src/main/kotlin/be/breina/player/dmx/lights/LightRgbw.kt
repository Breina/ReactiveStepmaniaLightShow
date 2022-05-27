package be.breina.player.dmx.lights

import java.awt.Color

class LightRgbw(dmxData: ByteArray, address: Int) {
    private val red = SingleLight(dmxData, address)
    private val green = SingleLight(dmxData, address + 2)
    private val blue = SingleLight(dmxData, address + 4)
    private val white = SingleLight(dmxData, address + 6)

    var color: Color = Color.RED

    fun setColorBrightness(ratio: Float) {
        val rgb = color.getRGBColorComponents(null)

        red.setBrightness(rgb[0] * ratio)
        green.setBrightness(rgb[1] * ratio)
        blue.setBrightness(rgb[2] * ratio)
    }

    fun setWhiteBrightness(ratio: Float) {
        white.setBrightness(ratio)
    }
}