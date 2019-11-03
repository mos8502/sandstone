// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        google()
        jcenter()
        maven {
            url = uri("https://maven.fabric.io/public")
        }
        maven {
            url = uri("http://dl.bintray.com/kotlin/kotlin-eap")
        }
        maven {
            url = uri("https://ci.android.com/builds/submitted/5956592/androidx_snapshot/latest/repository/")
        }
    }

    dependencies {
        classpath(Plugins.androidGradlePlugin)
        classpath(Plugins.androidGradlePlugin)
        classpath(Plugins.googleServices)
        classpath(Plugins.kotlinGradlePlugin)
        classpath(Plugins.fabric)
        classpath(Plugins.apolloGradlePlugin)
        classpath(Plugins.kotlinSerializationPlugin)
        classpath(Plugins.safeArgs)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url = uri("http://dl.bintray.com/kotlin/kotlin-eap")
        }
        maven {
            url = uri("https://ci.android.com/builds/submitted/5956592/androidx_snapshot/latest/repository/")
        }
    }
}

tasks.register("clean").configure {
    delete(buildDir)
}
