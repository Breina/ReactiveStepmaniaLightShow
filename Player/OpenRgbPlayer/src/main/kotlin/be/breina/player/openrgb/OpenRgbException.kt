package be.breina.player.openrgb

import java.io.IOException

class OpenRgbException : IOException {
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(message: String?) : super(message)
}