package be.breina.show.openrgb

import be.breina.show.openrgb.animation.Animator
import be.breina.show.openrgb.devices.RgbDevice
import io.gitlab.mguimard.openrgb.client.OpenRGBClient

class MainLoop(private val animator: Animator, private val devices: List<RgbDevice>, private val client: OpenRGBClient) : Runnable {
    fun play() {
        Thread(this).start()
    }

    override fun run() {
        var timeStart = System.currentTimeMillis()
        var frameCount = 0

        while (true) {
            devices.forEach { rgbDevice ->
                kotlin.run {

                    rgbDevice.clear()
                    animator.tick(rgbDevice)

                    // FIXME If the last update of a non-dirty device was missed, the light will stay on
//                    if (rgbDevice.isDirty) {
                        client.updateLeds(rgbDevice.index, rgbDevice.getDeviceLeds())
//                    }
                }
            }

            // This is way too much, yet OpenRGB still can't follow :/
            Thread.sleep(30)

            frameCount++

            val currentTime = System.currentTimeMillis()
            if ((currentTime - timeStart) > 1000) {
                // Only print once every second to reduce performance overhead of printing
                println("$frameCount FPS")
                frameCount = 0
                timeStart = currentTime
            }
        }
    }
}