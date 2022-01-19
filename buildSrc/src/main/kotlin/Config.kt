import org.gradle.api.JavaVersion

object Config {
    const val minSdk = 23
    const val compileSdk = 31
    const val targetSdk = 30
    const val applicationId = "com.fabledt5.moviescleanarchitecture"
    const val versionCode = 1
    const val versionName = "1.0"

    const val testsRunner = "androidx.test.runner.AndroidJUnitRunner"

    val javaVersion = JavaVersion.VERSION_1_8
}