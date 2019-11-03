val kotlinVersion = "1.3.50"

object Versions {
    val androidGradlePlugin = "4.0.0-alpha01"
    val googleServices = "4.3.2"
    val googlePlayCore = "1.6.4"

    object androidx {
        val lifecycle = "2.1.0"
        val appcompat = "1.1.0"
        val constraintlayout = "1.1.3"
        val coreKtx = "1.0.2"
        val navigation = "2.3.0-SNAPSHOT"
        val preferenceKtx = "1.1.0"
        object room {
            val ktx = "2.2.1"
            val coroutines = "2.1.0-alpha04"
            val compiler = "2.2.1"
            val runtime = "2.2.1"
        }
        object test {
            val junit = "1.1.1"
            val espresso = "3.2.0"
        }
        val recyclerview = "1.0.0"
    }

    object firebase {
        val analytics = "17.2.0"
        val config = "19.0.2"
        val auth = "19.1.0"
    }
    val crahslytics = "2.10.1"
    val fabric = "1.31.2"
    val kotlinx = "1.3.2"
    val kotlinxSerializationRuntime = "0.13.0"
    val androidMaterial = "1.0.0"
    val googlePlayServices = "17.0.0"
    val dagger = "2.24"
    val autoService = "1.0-rc6"
    val junit = "4.12"
    val okHttp = "4.2.2"
    val retrofit = "2.6.2"
    val apollo = "1.2.0"
    val jetbrainsAnnotations = "13.0"
    val stetho = "1.5.1"
    val material = "1.0.0"
}

object Plugins {
    val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    val kotlinSerializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion"
    val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
    val fabric = "io.fabric.tools:gradle:1.31.2"
    val apolloGradlePlugin = "com.apollographql.apollo:apollo-gradle-plugin:${Versions.apollo}"
    val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.androidx.navigation}"
}

object PluginId {
    val androidApplication = "com.android.application"
    val kotlinAndroid = "kotlin-android"
    val kotlinAndroidExtensions = "kotlin-android-extensions"
    val kotlinKapt = "kotlin-kapt"
    val googleServices = "com.google.gms.google-services"
    val fabric = "io.fabric"
    val apollo = "com.apollographql.android"
    val kotlinSerialization = "kotlinx-serialization"
    val safeArgs = "androidx.navigation.safeargs.kotlin"
    val androidDynamicFeature = "com.android.dynamic-feature"
    val androidLibrary = "com.android.library"
}

object Deps {
    object androidx{
        object lifeycle {
            val extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.androidx.lifecycle}"
        }
        val appcompat = "androidx.appcompat:appcompat:${Versions.androidx.appcompat}"
        val coreKtx = "androidx.core:core-ktx:${Versions.androidx.coreKtx}"
        val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.androidx.constraintlayout}"
        object navigation{
            val runtimeKtx = "androidx.navigation:navigation-runtime-ktx:${Versions.androidx.navigation}"
            val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.androidx.navigation}"
            val uiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.androidx.navigation}"
            val dynamicFeaturesFragment = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.androidx.navigation}"
        }
        val preferenceKtx = "androidx.preference:preference-ktx:${Versions.androidx.preferenceKtx}"
        object test {
            val junit = "androidx.test.ext:junit:${Versions.androidx.test.junit}"
            object espresso {
                val core = "androidx.test.espresso:espresso-core:${Versions.androidx.test.espresso}"
            }
        }
        object room {
            val ktx = "androidx.room:room-ktx:${Versions.androidx.room.ktx}"
            val coroutines = "androidx.room:room-coroutines:${Versions.androidx.room.coroutines}"
            val compiler = "androidx.room:room-compiler:${Versions.androidx.room.compiler}"
            val runtime = "androidx.room:room-runtime:${Versions.androidx.room.runtime}"
        }
        val recyclerview = "androidx.recyclerview:recyclerview:${Versions.androidx.recyclerview}"
    }
    val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
    object kotlinx {
        val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx}"
        val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinx}"
        val serializationRuntime = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.kotlinxSerializationRuntime}"
    }
    object firebase {
        val analytics = "com.google.firebase:firebase-analytics:${Versions.firebase.analytics}"
        val config = "com.google.firebase:firebase-config:${Versions.firebase.config}"
        val auth = "com.google.firebase:firebase-auth:${Versions.firebase.auth}"
    }
    val androidMaterial = "com.google.android.material:material:${Versions.androidMaterial}"
    val googlePlayServices = "com.google.android.gms:play-services-auth:${Versions.googlePlayServices}"
    object dagger {
        val dagger = "com.google.dagger:dagger:${Versions.dagger}"
        val android = "com.google.dagger:dagger-android:${Versions.dagger}"
        val androidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
        val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
        val androidProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    }
    object autoService {
        val autoService = "com.google.auto.service:auto-service:${Versions.autoService}"
        val annotations = "com.google.auto.service:auto-service-annotations:${Versions.autoService}"
    }
    val junit = "junit:junit:${Versions.junit}"
    val googlePlayCore = "com.google.android.play:core-ktx:${Versions.googlePlayCore}"
    val crahslytics = "com.crashlytics.sdk.android:crashlytics:${Versions.crahslytics}"
    val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val retrofitScalarConverter = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}"
    object apollo {
        val runtime = "com.apollographql.apollo:apollo-runtime:${Versions.apollo}"
    }
    val jetbrainsAnnotations = "org.jetbrains:annotations:${Versions.jetbrainsAnnotations}"
    val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
    val stethoOkHttp = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"
}