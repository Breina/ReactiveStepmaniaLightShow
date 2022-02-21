package be.breina.show.dmx.lights

import java.awt.Color

class LightRgb(dmxData: ByteArray, address: Int) {
    private val red = SingleLight(dmxData, address)
    private val green = SingleLight(dmxData, address + 2)
    private val blue = SingleLight(dmxData, address + 4)

    var color: Color = Color.RED

    fun setColorBrightness(ratio: Float) {
        val rgb = color.getRGBColorComponents(null)

        red.setBrightness(rgb[0] * ratio)
        green.setBrightness(rgb[1] * ratio)
        blue.setBrightness(rgb[2] * ratio)
    }
}