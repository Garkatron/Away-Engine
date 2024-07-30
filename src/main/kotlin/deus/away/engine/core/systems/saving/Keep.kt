package deus.away.engine.core.systems.saving
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class Keep(val name: String)
