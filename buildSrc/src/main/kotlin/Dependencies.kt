internal object Versions {

    // Core
    const val android_core = "1.9.0"
    const val gradle_versions = "0.42.0"
    const val kotlin_stdlib = "1.7.10"
    const val kotlinx_coroutines_android = "1.6.4"
    const val gradle = "7.3.0"
    const val timber = "5.0.1"
    const val viewBinding_delegate = "1.5.6"
    const val paging = "3.2.0-alpha02"
    const val android_fragments = "1.6.0-alpha02"

    // Design
    const val appcompat = "1.6.0-rc01"
    const val material = "1.8.0-alpha01"
    const val constraint = "2.2.0-alpha03"
    const val ssu = "1.1.0"
    const val expandableText = "1.0.5"
    const val coil = "2.2.1"
    const val flexbox = "3.0.0"
    const val circular_image_view = "4.3.1"
    const val youtube_player = "10.0.5"
    const val image_cropper = "4.3.2"
    const val swipe_reveal = "1.1.2"

    // Retrofit & Gson
    const val retrofit = "2.9.0"
    const val logging_interceptor = "5.0.0-alpha.10"

    // Room
    const val room = "2.5.0-alpha03"

    // Preferences Datastore
    const val preferences_datastore = "1.0.0"

    // Navigation
    const val navigation = "2.6.0-alpha01"

    // Dagger
    const val dagger = "2.43.2"

    // Testing
    const val junit = "4.13.2"
    const val androidx_junit = "1.1.3"
    const val espresso_core = "3.4.0"
    const val mockito_core = "4.8.0"
}

object Dependencies {

    // Core
    const val android_core = "androidx.core:core-ktx:${Versions.android_core}"
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_stdlib}"
    const val kotlinx_coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinx_coroutines_android}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val viewBinding_delegate =
        "com.github.kirich1409:viewbindingpropertydelegate:${Versions.viewBinding_delegate}"
    const val paging = "androidx.paging:paging-runtime:${Versions.paging}"
    const val android_fragments = "androidx.fragment:fragment-ktx:${Versions.android_fragments}"

    // Design
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
    const val sdp = "com.intuit.sdp:sdp-android:${Versions.ssu}"
    const val ssp = "com.intuit.ssp:ssp-android:${Versions.ssu}"
    const val expandable_text = "at.blogc:expandabletextview:${Versions.expandableText}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val flexbox_layout = "com.google.android.flexbox:flexbox:${Versions.flexbox}"
    const val circular_image_view =
        "com.mikhaellopez:circularimageview:${Versions.circular_image_view}"
    const val youtube_player =
        "com.github.PierfrancescoSoffritti.android-youtube-player:chromecast-sender:${Versions.youtube_player}"
    const val image_cropper = "com.github.CanHub:Android-Image-Cropper:${Versions.image_cropper}"
    const val swipe_reveal = "com.apachat:swipereveallayout-android:${Versions.swipe_reveal}"

    // Dagger
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    // Testing
    const val junit = "junit:junit:${Versions.junit}"
    const val androidx_junit = "androidx.test.ext:junit:${Versions.androidx_junit}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"
    const val mockito_core = "org.mockito:mockito-core:${Versions.mockito_core}"

    // Room
    const val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"

    // Preferences
    const val preferences_datastore =
        "androidx.datastore:datastore-preferences:${Versions.preferences_datastore}"

    // Navigation
    const val navigation_ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val navigation_fragment_ktx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigation_runtime =
        "androidx.navigation:navigation-runtime-ktx:${Versions.navigation}"

    // Network
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val interceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.logging_interceptor}"
    const val gson_converter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
}

object Plugins {
    const val build_gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlin_gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_stdlib}"
    const val safe_args_gradle =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    const val gradle_dependencies =
        "com.github.ben-manes:gradle-versions-plugin:${Versions.gradle_versions}"

    const val gradle_versions = "com.github.ben-manes.versions"
    const val application = "com.android.application"
    const val kapt = "kapt"
    const val android = "android"
    const val safe_args = "androidx.navigation.safeargs"
}