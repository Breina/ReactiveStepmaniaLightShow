package be.breina.parser.mixer

import javax.swing.JFrame
import be.breina.parser.mixer.JLightShowPanel
import be.breina.parser.model.Song
import javax.swing.WindowConstants

class SwingMixer(song: Song, lightShowPanel: JLightShowPanel = JLightShowPanel(song)) : JFrame("Swing mixer"), Mixer by lightShowPanel {

    init {
        add(lightShowPanel)

        setSize(1024, 800)
        extendedState = this.extendedState or MAXIMIZED_BOTH;
        defaultCloseOperation = EXIT_ON_CLOSE
        isVisible = true
    }
}