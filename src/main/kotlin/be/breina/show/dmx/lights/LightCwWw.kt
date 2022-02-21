package be.breina.show.dmx.lights

import java.awt.Color

class LightCwWw(dmxData: ByteArray, address: Int) {
    private val coldWhite = SingleLight(dmxData, address)
    private val warmWhite = SingleLight(dmxData, address + 2)

    var colorTemperature: Color = Color.RED

    fun setColorTemperatureBrightness(ratio: Float) {
        val rgb = colorTemperature.getRGBColorComponents(null)

        warmWhite.setBrightness((rgb[0] + rgb[1]) * ratio / 2)
        coldWhite.setBrightness(rgb[2] * ratio)
    }
}