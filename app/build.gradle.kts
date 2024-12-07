plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.shajib.interactivelearningforkids"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.shajib.interactivelearningforkids"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    // Make sure to use packagingOptions inside android block for newer gradle versions
    packagingOptions {
        resources {
            exclude("META-INF/NOTICE.md")
            exclude("META-INF/LICENSE.md")
            exclude("META-INF/DEPENDENCIES")
        }
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-config")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-database")

    implementation("com.sun.mail:android-mail:1.6.7")
    implementation("com.sun.mail:android-activation:1.6.7")

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}
