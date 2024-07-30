package deus.away.engine.core.systems.scene

class SceneManager(vararg initScenes: Scene) {

    var currentScene: Scene? = null
    var previousScene: Scene? = null
    private val scenes = mutableMapOf<String, Scene>()

    init {
        for (scene in initScenes) {
            scenes[scene.name] = scene
        }
        // Set the first scene as the current scene if provided
        if (initScenes.isNotEmpty()) {
            currentScene = initScenes.first()
        }
    }

    fun add(vararg newScenes: Scene) {
        for (scene in newScenes) {
            scenes[scene.name] = scene
        }
    }

    fun setCurrent(name: String) {
        currentScene = scenes[name]
        println("Current scene set to: $name")
    }

    fun changeTo(name: String) {
        previousScene = currentScene
        currentScene = scenes[name]
        println("Previous scene was: ${previousScene?.name} Current scene set to: $name")
    }


}
