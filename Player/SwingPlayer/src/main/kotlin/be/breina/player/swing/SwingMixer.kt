package be.breina.player.swing

import be.breina.player.Mixer
import be.breina.player.model.SongTheming
import javax.swing.JFrame

class SwingMixer(songTheming: SongTheming, lightShowPanel: JLightShowPanel = JLightShowPanel(songTheming)) : JFrame("Swing mixer"), Mixer by lightShowPanel {
    init {
        add(lightShowPanel)

        setSize(1024, 800)
        extendedState = this.extendedState or MAXIMIZED_BOTH;
        defaultCloseOperation = EXIT_ON_CLOSE
        isVisible = true
    }
}