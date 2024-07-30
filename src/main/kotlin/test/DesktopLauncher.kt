package test

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = Lwjgl3ApplicationConfiguration().apply {
            setTitle("AwayEngine")
            setWindowedMode(800, 600) // Configura el tamaño inicial de la ventana
            setResizable(true) // Permite cambiar el tamaño de la ventana
            useVsync(true) // Sincroniza con el refresco de la pantalla
        }
        Lwjgl3Application(TGame(), config)
    }
}
