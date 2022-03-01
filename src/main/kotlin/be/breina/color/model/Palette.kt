package be.breina.color.model

import java.awt.Color

class Palette(colors: Array<Color>) {
    private val colors: Array<Color>

    init {
        assert(colors.isNotEmpty())
        this.colors = colors
    }

    val primary: Color
        get() = colors[0]

    val secondary: Color
        get() = if (colors.size >= 2) colors[1] else primary

    val tertiary: Color
        get() = if (colors.size >= 3) colors[2] else secondary
}