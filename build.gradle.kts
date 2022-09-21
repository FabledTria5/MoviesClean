apply {
    plugin(Plugins.gradle_versions)
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(dependencyNotation = Plugins.build_gradle)
        classpath(dependencyNotation = Plugins.kotlin_gradle)
        classpath(dependencyNotation = Plugins.safe_args_gradle)
        classpath(dependencyNotation = Plugins.gradle_dependencies)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}