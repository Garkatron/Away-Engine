package deus.away.engine.core.nodes

import deus.away.engine.core.media.SourceLoader
import deus.away.engine.core.media.audio.WAVPlayer
import deus.away.engine.core.systems.debug.DebugLogic
import deus.away.engine.core.systems.node.Node
import deus.away.engine.core.systems.saving.Keep

class AudioStreamPlayer(
    name: String = "AudioStreamPlayer",
    @Keep("path") val path: String,
    @Keep("isLoop")
    val isLoop: Boolean = true
) : Node(name) {

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
            it.loop = isLoop
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
