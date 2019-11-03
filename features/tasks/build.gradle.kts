import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(PluginId.androidDynamicFeature)
    id(PluginId.kotlinAndroid)
    id(PluginId.kotlinAndroidExtensions)
    id(PluginId.kotlinKapt)
}

tasks.getting(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.getting(JavaCompile::class) {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
}

android {
    compileSdkVersion(AndroidDefaults.compileSdkVersion)

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    defaultConfig {
        minSdkVersion(AndroidDefaults.minSdkVersion)
        targetSdkVersion(AndroidDefaults.targetSdkVersion)
        versionCode = AndroidDefaults.versionCode
        versionName = AndroidDefaults.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":app"))

    kapt(Deps.dagger.compiler)
    kapt(Deps.dagger.androidProcessor)
    kapt(Deps.autoService.autoService)

    testImplementation(Deps.junit)
}