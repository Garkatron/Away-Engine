package core.media.audio

import java.io.InputStream
import javax.sound.sampled.*

class WAVPlayer(private val clip: Clip) : AudioPlayer() {

    init {
        clip.addLineListener { event ->
            when (event.type) {
                LineEvent.Type.STOP -> {
                    println("Playback finished.")
                    finished = true
                }
                LineEvent.Type.START -> {
                    println("Playback started.")
                    finished = false
                }
            }
        }
    }

    override fun restart() {
        clip.framePosition = 0
    }

    override fun play() {
        if (clip.isRunning) {
            clip.stop()
        }
        clip.framePosition = 0  // Reinicia el clip antes de reproducir
        clip.start()
        println("Playing the clip.")
    }

    override fun stop() {
        clip.stop()
        println("Clip stopped.")
    }


}
