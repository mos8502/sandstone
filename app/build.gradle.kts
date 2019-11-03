import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(PluginId.androidApplication)
    id(PluginId.kotlinAndroid)
    id(PluginId.kotlinAndroidExtensions)
    id(PluginId.kotlinKapt)
    id(PluginId.googleServices)
    id(PluginId.fabric)
    id(PluginId.apollo)
    id(PluginId.kotlinSerialization)
    id(PluginId.safeArgs)
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

    dynamicFeatures.add(":tasks")

    defaultConfig {
        applicationId = "hu.nemi.sandstone"
        minSdkVersion(AndroidDefaults.minSdkVersion)
        targetSdkVersion(AndroidDefaults.targetSdkVersion)
        versionCode = AndroidDefaults.versionCode
        versionName = AndroidDefaults.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", "\"${baseUrl}\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    api(project(":tasks-common"))
    api(Deps.androidx.navigation.fragmentKtx)
    api(Deps.androidx.navigation.dynamicFeaturesFragment)
    api(Deps.apollo.runtime)
    api(Deps.dagger.dagger)
    api(Deps.dagger.android)
    api(Deps.dagger.androidSupport)
    api(Deps.autoService.annotations)
    api(Deps.kotlinStdlib)
    api(Deps.androidx.recyclerview)
    api(Deps.kotlinx.coroutinesCore)
    api(Deps.kotlinx.coroutinesAndroid)
    api(Deps.crahslytics)
    api(Deps.androidx.room.runtime)

    kapt(Deps.dagger.compiler)
    kapt(Deps.dagger.androidProcessor)
    kapt(Deps.autoService.autoService)

    implementation(Deps.androidx.appcompat)
    implementation(Deps.androidx.coreKtx)
    implementation(Deps.androidx.constraintlayout)

    implementation(Deps.firebase.analytics)
    implementation(Deps.firebase.config)
    implementation(Deps.firebase.auth)
    implementation(Deps.googlePlayServices)

    implementation(Deps.androidx.navigation.runtimeKtx)
    implementation(Deps.androidx.navigation.uiKtx)

    implementation(Deps.androidx.lifeycle.extensions)

    implementation(Deps.androidMaterial)

    kapt(Deps.autoService.autoService)

    implementation(Deps.googlePlayCore)

    implementation(Deps.androidx.preferenceKtx)

    implementation(Deps.okHttp)
    implementation(Deps.retrofit)
    implementation(Deps.retrofitGsonConverter)
    implementation(Deps.retrofitScalarConverter)

    implementation(Deps.jetbrainsAnnotations)

    implementation(Deps.androidx.room.ktx)
    kapt(Deps.androidx.room.compiler)

    implementation(Deps.stetho)
    implementation(Deps.stethoOkHttp)

    implementation(Deps.kotlinx.serializationRuntime)

    testImplementation(Deps.junit)

    androidTestImplementation(Deps.androidx.test.junit)
    androidTestImplementation(Deps.androidx.test.espresso.core)
}
