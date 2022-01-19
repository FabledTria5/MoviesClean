plugins {
    id(Plugins.application)
    id(Plugins.safe_args)
    kotlin(Plugins.android)
    kotlin(Plugins.kapt)
}

kotlin {
    sourceSets {
        debug {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        release {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = Config.testsRunner
    }
    buildFeatures {
        viewBinding = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    // Core
    implementation(dependencyNotation = Dependencies.android_core)
    implementation(dependencyNotation = Dependencies.kotlin_stdlib)
    implementation(dependencyNotation = Dependencies.kotlinx_coroutines)
    implementation(dependencyNotation = Dependencies.viewBinding_delegate)
    implementation(dependencyNotation = Dependencies.paging)
    implementation(dependencyNotation = Dependencies.android_fragments)

    // Design
    implementation(dependencyNotation = Dependencies.appcompat)
    implementation(dependencyNotation = Dependencies.material)
    implementation(dependencyNotation = Dependencies.constraint)
    implementation(dependencyNotation = Dependencies.sdp)
    implementation(dependencyNotation = Dependencies.ssp)
    implementation(dependencyNotation = Dependencies.expandable_text)
    implementation(dependencyNotation = Dependencies.coil)
    implementation(dependencyNotation = Dependencies.circular_image_view)
    implementation(group = "com.apachat", name = "swipereveallayout-android", version = "1.1.2")
    implementation(dependencyNotation = Dependencies.flexbox_layout)
    implementation(dependencyNotation = Dependencies.youtube_player)
    implementation(dependencyNotation = Dependencies.image_cropper)

    // Di
    implementation(dependencyNotation = Dependencies.dagger)
    kapt(dependencyNotation = Dependencies.dagger_compiler)

    // Testing
    implementation(dependencyNotation = Dependencies.junit)
    implementation(dependencyNotation = Dependencies.androidx_junit)
    implementation(dependencyNotation = Dependencies.espresso_core)
    testImplementation(dependencyNotation = Dependencies.mockito_core)

    // Room
    implementation(dependencyNotation = Dependencies.room_runtime)
    implementation(dependencyNotation = Dependencies.room_ktx)
    kapt(dependencyNotation = Dependencies.room_compiler)

    // Preferences Datastore
    implementation(dependencyNotation = Dependencies.preferences_datastore)

    // Navigation
    implementation(dependencyNotation = Dependencies.navigation_ui_ktx)
    implementation(dependencyNotation = Dependencies.navigation_fragment_ktx)
    implementation(dependencyNotation = Dependencies.navigation_runtime)

    // Network
    implementation(dependencyNotation = Dependencies.retrofit)
    implementation(dependencyNotation = Dependencies.interceptor)
    implementation(dependencyNotation = Dependencies.gson_converter)

    // Logging
    implementation(dependencyNotation = Dependencies.timber)
}