package be.breina.show.openrgb.devices

import io.gitlab.mguimard.openrgb.entity.OpenRGBColor
import java.util.*
import java.util.stream.Collectors

class MultiDevice(controllerIndex: Int, val devices: List<RgbDevice>) : AbstractRgbDevice(controllerIndex, 0) {
    override fun mergeLeds(offset: Int, length: Int, color: OpenRGBColor) {
        val deviceIterator = devices.iterator()
        var workingOffset = offset
        var workingLength = length
        while (deviceIterator.hasNext() && workingLength > 0) {
            val device = deviceIterator.next()
            val deviceLedCount = device.getDeviceLedCount()
            if (workingOffset < deviceLedCount) {
                val deviceLedMergeLength = Integer.min(deviceLedCount, workingLength)
                device.mergeLeds(workingOffset, deviceLedMergeLength, color)
                workingLength -= deviceLedMergeLength
            }
            workingOffset -= deviceLedCount
        }
    }

    override fun clear() {
        devices.forEach(RgbDevice::clear)
    }

    override var isDirty: Boolean
        get() = devices.stream().anyMatch(RgbDevice::isDirty)
        set(value) {}

    override fun getDeviceLedCount(): Int {
        return devices.stream()
            .mapToInt(RgbDevice::getDeviceLedCount)
            .sum()
    }

    override fun getDeviceLeds(): Array<OpenRGBColor> {
        return devices.stream()
            .map(RgbDevice::getDeviceLeds)
            .flatMap { array: Array<OpenRGBColor> -> Arrays.stream(array) }
            .collect(Collectors.toList())
            .toTypedArray()
    }
}