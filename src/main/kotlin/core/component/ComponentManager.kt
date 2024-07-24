package core.component

import core.GameObject

class ComponentManager(private val ref: GameObject) {
    private val components = mutableMapOf<String, MutableList<Component<*>>>()

    fun addComponent(component: Component<*>) {
        component.ref = ref
        components.computeIfAbsent(component.name) { mutableListOf() }.add(component)
    }

    fun getComponentsByName(name: String): List<Component<*>> {
        return components[name] ?: emptyList()
    }

    fun getComponentByName(name: String): Component<*>? {
        return components[name]?.firstOrNull()
    }

    fun update(dt: Float) {
        for (componentList in components.values) {
            for (component in componentList) {
                component.update(dt)
            }
        }
    }
}