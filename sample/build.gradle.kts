/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("SpellCheckingInspection")

plugins {
    id("com.android.application")
    kotlin("multiplatform")
}

android {
    compileSdkVersion(ProjectVersions.androidSdk)
    buildToolsVersion(ProjectVersions.androidBuildTools)
    defaultConfig {
        applicationId = "com.louiscad.splittiessample"
        minSdkVersion(14)
        targetSdkVersion(ProjectVersions.androidSdk)
        versionCode = 1
        versionName = ProjectVersions.thisLibrary
        resConfigs("en", "fr")
        proguardFile(getDefaultProguardFile("proguard-android-optimize.txt"))
        proguardFile("proguard-rules.pro")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        getByName("debug") {
            storeFile = file("${System.getProperty("user.home")}/.android/debug.keystore")
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storePassword = "android"
        }
    }
    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    sourceSets.getByName("main") {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDir("src/androidMain/res")
    }
}

kotlin {
    android()
    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.Experimental")
                useExperimentalAnnotation("splitties.experimental.ExperimentalSplittiesApi")
                useExperimentalAnnotation("splitties.lifecycle.coroutines.PotentialFutureAndroidXLifecycleKtxApi")
            }
        }
        getByName("androidMain").dependencies {
            arrayOf(
                "activities",
                "alertdialog-appcompat",
                "appctx",
                "arch-lifecycle",
                "arch-room",
                "bitflags",
                "bundle",
                "checkedlazy",
                "collections",
                "exceptions",
                "fragments",
                "fragmentargs",
                "initprovider",
                "intents",
                "lifecycle-coroutines",
                "material-colors",
                "material-lists",
                "permissions",
                "preferences",
                "systemservices",
                "toast",
                "typesaferecyclerview",
                "mainthread",
                "views-coroutines",
                "views-dsl-appcompat",
                "views-dsl-constraintlayout",
                "views-dsl-material"
            ).forEach { moduleName ->
                implementation(splitties(moduleName))
            }
            with(Libs) {
                arrayOf(
                    kotlin.stdlibJdk7,
                    androidX.appCompat,
                    androidX.coreKtx,
                    androidX.constraintLayout,
                    google.material,
                    timber,
                    kotlinX.coroutines.android
                )
            }.forEach {
                implementation(it)
            }
        }
    }
}

dependencies {
    arrayOf(
        "stetho-init",
        "views-dsl-ide-preview"
    ).forEach { moduleName ->
        debugImplementation(splitties(moduleName))
    }
}
