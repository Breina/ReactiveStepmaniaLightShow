package be.breina.show.openrgb.linking

import be.breina.show.openrgb.OpenRgbException
import be.breina.show.openrgb.devices.AbstractRgbDevice
import io.gitlab.mguimard.openrgb.client.OpenRGBClient
import io.gitlab.mguimard.openrgb.entity.OpenRGBDevice
import java.io.IOException
import java.util.*
import java.util.function.BiFunction
import java.util.function.Function
import java.util.stream.Collectors

class OpenRgbDeviceLinker(client: OpenRGBClient) {
    private val controllers: Map<String, DeviceWithIndex>
    private val linkedDevices: MutableList<AbstractRgbDevice>

    init {
        controllers = getControllers(client).stream()
            .collect(Collectors.toMap({ controller -> java.lang.String(controller.getLocation()).trim() }, Function.identity()))
        linkedDevices = LinkedList()
    }

    @Throws(OpenRgbException::class)
    fun <D : AbstractRgbDevice> linkDevice(location: String, deviceCreator: BiFunction<Int, OpenRGBDevice, D>): D {
        val openRGBDevice = controllers[location] ?: throw OpenRgbException("Could not find RGB controller on location: $location")
        val rgbDevice = deviceCreator.apply(openRGBDevice.getIndex(), openRGBDevice.getOpenRGBDevice())
        linkedDevices.add(rgbDevice)
        return rgbDevice
    }

    fun getLinkedDevices(): List<AbstractRgbDevice> = linkedDevices

    private class DeviceWithIndex(private val index: Int, private val openRGBDevice: OpenRGBDevice) {
        fun getOpenRGBDevice(): OpenRGBDevice = openRGBDevice

        fun getIndex(): Int = index

        fun getLocation(): String = openRGBDevice.location
    }

    companion object {
        @Throws(OpenRgbException::class)
        private fun getControllers(client: OpenRGBClient): LinkedList<DeviceWithIndex> {
            return try {
                val controllerCount = client.controllerCount
                val deviceControllers = (0 until controllerCount)
                    .mapTo(LinkedList<DeviceWithIndex>()) { DeviceWithIndex(it, client.getDeviceController(it)) }
                deviceControllers
            } catch (e: IOException) {
                throw OpenRgbException("Could not link device.", e)
            }
        }
    }
}