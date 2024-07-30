package deus.away.engine.core.media

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.GdxRuntimeException
import java.awt.image.BufferedImage
import java.io.IOException
import java.io.InputStream
import javax.imageio.ImageIO
import javax.sound.sampled.*

object SourceLoader {

    fun loadImageResource(path: String): Texture? {
        return try {
            val fileHandle = Gdx.files.internal(path)
            if (fileHandle.exists()) {
                Texture(fileHandle)
            } else {
                println("Resource not found: $path")
                null
            }
        } catch (e: GdxRuntimeException) {
            println("Error reading image from path '$path': ${e.message}")
            null
        }
    }

    fun readResourceFile(path: String): String? {
        return javaClass.getResourceAsStream(path)?.bufferedReader()?.use { it.readText() }
    }

    fun loadWAVFromInputStream(inputStream: InputStream, speed: Float = 1f): Clip? {
        return try {
            val clip: Clip = AudioSystem.getClip()
            val audioFormat = clip.format
            val newFormat = AudioFormat(
                audioFormat.encoding,
                audioFormat.sampleRate * speed,
                audioFormat.sampleSizeInBits,
                audioFormat.channels,
                audioFormat.frameSize,
                audioFormat.frameRate * speed,
                audioFormat.isBigEndian
            )
            val audioInputStream: AudioInputStream =
                AudioSystem.getAudioInputStream(newFormat, AudioSystem.getAudioInputStream(inputStream))



            clip.open(audioInputStream)
            println("Clip loaded successfully.")
            clip
        } catch (e: UnsupportedAudioFileException) {
            println("Unsupported audio file format: ${e.message}")
            null
        } catch (e: IOException) {
            println("Error reading audio file: ${e.message}")
            null
        } catch (e: Exception) {
            println("An unexpected error occurred: ${e.message}")
            null
        }
    }
}
