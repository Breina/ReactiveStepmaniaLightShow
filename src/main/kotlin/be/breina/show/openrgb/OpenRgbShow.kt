package be.breina.show.openrgb

import io.gitlab.mguimard.openrgb.client.OpenRGBClient
import io.gitlab.mguimard.openrgb.entity.OpenRGBColor


object OpenRgbShow {
    val frameTime = 1000 / 40

    @JvmStatic
    fun main(args: Array<String>) {
        val client = OpenRGBClient("localhost", 6742, "Lightshow")

        client.connect()

        println(client.controllerCount)

        client.getDeviceController(9)

//        client.updateZoneLeds(9, 0, )

        client.updateLeds(
            9,
            arrayOf(
                OpenRGBColor(255, 0, 0),
                OpenRGBColor(0, 255, 0),
                OpenRGBColor(0, 0, 255),
                OpenRGBColor(255, 255, 255),
                OpenRGBColor(255, 0, 0),
            )
        )
    }
}