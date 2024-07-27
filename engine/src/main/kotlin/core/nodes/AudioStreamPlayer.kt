package core.nodes

import core.engine.media.SourceLoader
import core.media.audio.WAVPlayer
import core.systems.debug.DebugLogic
import core.systems.node.Node

class AudioStreamPlayer(name: String = "AudioStreamPlayer", path: String) : Node(name) {

    private val wavPlayer = this::class.java.getResourceAsStream(path)?.let {
        DebugLogic.debugPrintln("Loading WAV from path: $path")
        SourceLoader.loadWAVFromInputStream(it)
            ?.let {
                DebugLogic.debugPrintln("WAV loaded successfully.")
                WAVPlayer(it)
            }
    }

    init {
        wavPlayer?.let {
            it.loop = true
            start()
        } ?: DebugLogic.debugPrintln("Failed to load WAV player.")
    }



    fun start() {
        DebugLogic.debugPrintln("Starting playback.")
        wavPlayer?.play()
    }

    fun stop() {
        DebugLogic.debugPrintln("Stopping playback.")
        wavPlayer?.stop()
    }

    fun restart() {
        DebugLogic.debugPrintln("Restarting playback.")
        wavPlayer?.restart()
    }

    fun change(path: String) {
        DebugLogic.debugPrintln("Changing audio to path: $path")
        stop()
        SourceLoader.loadWAVFromInputStream(this::class.java.getResourceAsStream(path))?.let {
            DebugLogic.debugPrintln("New WAV loaded successfully.")
            wavPlayer?.change(it)
        }
    }

    override fun update(dt: Float) {}
}
