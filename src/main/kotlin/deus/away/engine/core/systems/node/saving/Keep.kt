package deus.away.engine.core.systems.node.saving
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class Keep(val name: String)
