package be.breina.player.openrgb.animation

import be.breina.player.openrgb.devices.MultiDevice
import be.breina.player.openrgb.devices.RgbDevice
import be.breina.player.model.Palette
import io.gitlab.mguimard.openrgb.entity.OpenRGBColor
import java.awt.Color
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

class Animator(private val palette: Palette, linkedDevices: List<RgbDevice>) {
    private val animations: ConcurrentHashMap<RgbDevice, CopyOnWriteArrayList<AbstractAnimation>> = ConcurrentHashMap()

    init {
        initializeDevices(linkedDevices)
    }

    private fun initializeDevices(linkedDevices: List<RgbDevice>) {
        linkedDevices.forEach { linkedDevice ->
            run {
                animations[linkedDevice] = CopyOnWriteArrayList<AbstractAnimation>()
                if (linkedDevice is MultiDevice) {
                    initializeDevices(linkedDevice.devices)
                }
            }
        }
    }

    fun addAnimations(vararg newAnimations: AbstractAnimation) {
        newAnimations.forEach { newAnimation ->
            animations[newAnimation.device]?.also { existingAnimations -> existingAnimations.add(newAnimation) }
        }
    }

    fun hasExistingAnimation(rgbDevice: RgbDevice): Boolean = animations.containsKey(rgbDevice)

    fun getPrimaryColor(): OpenRGBColor = mapToOpenRgb(palette.primary)

    fun getSecondaryColor(): OpenRGBColor = mapToOpenRgb(palette.secondary)

    fun getTertiaryColor(): OpenRGBColor = mapToOpenRgb(palette.tertiary)

    @Synchronized
    fun tick(rgbDevice: RgbDevice) {
        animations[rgbDevice]?.also { abstractAnimations ->
            run {
                abstractAnimations.forEach(AbstractAnimation::tick)
                abstractAnimations.removeIf(AbstractAnimation::isFinished)
            }
        }

        if (rgbDevice is MultiDevice) {
            rgbDevice.devices.forEach(::tick)
        }
    }

    companion object {
        private fun mapToOpenRgb(color: Color): OpenRGBColor = OpenRGBColor(color.red, color.green, color.blue)
    }
}