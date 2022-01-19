// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(dependencyNotation = Plugins.build_gradle)
        classpath(dependencyNotation = Plugins.kotlin_gradle)
        classpath(dependencyNotation = Plugins.safe_args_gradle)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}