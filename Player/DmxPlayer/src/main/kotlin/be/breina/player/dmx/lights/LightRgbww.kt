package be.breina.player.dmx.lights

import java.awt.Color

class LightRgbww(dmxData: ByteArray, address: Int) {
    private val red = SingleLight(dmxData, address)
    private val green = SingleLight(dmxData, address + 2)
    private val blue = SingleLight(dmxData, address + 4)
    private val coldWhite = SingleLight(dmxData, address + 6)
    private val warmWhite = SingleLight(dmxData, address + 8)

    var color: Color = Color.RED

    fun setColorBrightness(ratio: Float) {
        val rgb = color.getRGBColorComponents(null)

        red.setBrightness(rgb[0] * ratio)
        green.setBrightness(rgb[1] * ratio)
        blue.setBrightness(rgb[2] * ratio)
    }

    var colorTemperature: Color = Color.RED

    fun setColorTemperatureBrightness(ratio: Float) {
        val rgb = colorTemperature.getRGBColorComponents(null)

        warmWhite.setBrightness((rgb[0] + rgb[1]) * ratio / 2)
        coldWhite.setBrightness(rgb[2] * ratio)
    }
}