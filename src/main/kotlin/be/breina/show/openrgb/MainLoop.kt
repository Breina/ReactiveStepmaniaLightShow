package be.breina.show.openrgb

import be.breina.show.openrgb.animation.Animator
import be.breina.show.openrgb.openrgb.DeviceUpdater

class MainLoop(private val deviceUpdater: DeviceUpdater, private val animator: Animator) : Runnable {
    fun play() {
        Thread(this).start()
    }

    override fun run() {
        while (true) {
            animator.tick()
            try {
                deviceUpdater.updateState()
                Thread.sleep(30)
            } catch (e: OpenRgbException) {
                e.printStackTrace()
                return
            } catch (e: InterruptedException) {
                e.printStackTrace()
                return
            }
        }
    }
}