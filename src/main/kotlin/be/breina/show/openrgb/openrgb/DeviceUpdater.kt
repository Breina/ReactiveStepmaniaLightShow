package be.breina.show.openrgb.openrgb

import be.breina.show.openrgb.OpenRgbException
import be.breina.show.openrgb.devices.RgbDevice
import io.gitlab.mguimard.openrgb.client.OpenRGBClient
import java.io.IOException

class DeviceUpdater(private val client: OpenRGBClient, private val rgbDevices: List<RgbDevice>) {
    @Throws(OpenRgbException::class)
    fun updateState() {
        for (rgbDevice in rgbDevices) {
            try {
                client.updateLeds(rgbDevice.index, rgbDevice.getDeviceLeds())
                rgbDevice.clear()
            } catch (e: IOException) {
                throw OpenRgbException("Could not update LED device", e)
            }
        }
    }
}