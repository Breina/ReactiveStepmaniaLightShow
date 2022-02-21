package be.breina.show.dmx

import be.breina.show.dmx.lights.LightCwWw
import be.breina.show.dmx.lights.LightRgbw
import kotlin.jvm.JvmStatic
import ch.bildspur.artnet.ArtNetClient
import java.awt.Color
import java.net.InetAddress

object DmxShow{
    val frameTime = 1000 / 40

    @JvmStatic
    fun main(args: Array<String>) {

        val dmxData = ByteArray(512)
        val light = LightCwWw(dmxData, 1)
        val colorLight = LightRgbw(dmxData, 25)

        val address = InetAddress.getByName("192.168.1.15")

        val client = ArtNetClient()
        client.start()

//        dmxData[57] = 255.toByte()
//        dmxData[58] = 255.toByte()

//        light.setColdWhite(1F)

        colorLight.color = Color.BLUE
        colorLight.setColorBrightness(0.1f)
        client.unicastDmx(address, 0, 0, dmxData)

//        for (ratio: Int in 0..100) {
////            light.setColdWhite((100 - ratio.toFloat()) / 100f)
////            light.setWarmWhite(ratio.toFloat() / 100f)
//
//            doWithTimeout(frameTime) {
//                light.setColdWhite(1f)
//                client.unicastDmx(address, 0, 0, dmxData)
//            }
//
//            doWithTimeout(frameTime) {
//                light.setColdWhite(0f)
//                client.unicastDmx(address, 0, 0, dmxData)
//            }
//        }

        client.stop()
    }

    fun doWithTimeout(waitTime: Int, function: () -> Unit) {
        val before = System.currentTimeMillis()
        function.invoke()
        val diff = System.currentTimeMillis() - before

        if (diff < waitTime) {
            println(waitTime - diff)
            Thread.sleep((waitTime - diff))
        }
    }
}