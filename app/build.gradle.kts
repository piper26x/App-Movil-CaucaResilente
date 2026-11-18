import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

val localProperties = Properties().apply {
    val archivo = rootProject.file("local.properties")
    if (archivo.exists()) {
        archivo.inputStream().use { load(it) }
    }
}

val mapsApiKey: String = localProperties.getProperty("MAPS_API_KEY")
    ?: System.getenv("MAPS_API_KEY")
    ?: ""

// Datos de la firma de release (archivo local, no se sube a git).
val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties().apply {
    if (keystorePropertiesFile.exists()) {
        keystorePropertiesFile.inputStream().use { load(it) }
    }
}

android {
    namespace = "com.example.cauca_resilente_app"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.cauca_resilente_app"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
    }

    signingConfigs {
        create("release") {
            if (keystorePropertiesFile.exists()) {
                storeFile = rootProject.file(keystoreProperties.getProperty("storeFile"))
                storePassword = keystoreProperties.getProperty("storePassword")
                keyAlias = keystoreProperties.getProperty("keyAlias")
                keyPassword = keystoreProperties.getProperty("keyPassword")
            }
        }
    }

    buildTypes {
        release {
            // Si no existe keystore.properties (p. ej. en otro equipo), se compila sin firmar
            // en lugar de fallar; el APK distribuible se firma con el keystore local.
            if (keystorePropertiesFile.exists()) {
                signingConfig = signingConfigs.getByName("release")
            }
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
}

dependencies {
    // Retrofit para peticiones HTTP
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    // Convertidor de JSON a Objetos de Kotlin
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    // Interceptor para ver los logs en la consola
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("androidx.compose.material:material-icons-extended")
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.play.services.maps)
    implementation("com.google.android.gms:play-services-location:21.3.0")
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Core Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Retrofit para la API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp para logging (opcional pero muy útil para depurar)
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Corrutinas de Kotlin (para llamadas asíncronas)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")




    // When using the BoM, you don't specify versions in Firebase library dependencies

    // Add the dependency for the Firebase SDK for Google Analytics
    implementation("com.google.firebase:firebase-analytics")

    // TODO: Add the dependencies for any other Firebase products you want to use
    // See https://firebase.google.com/docs/android/setup#available-libraries
    // For example, add the dependencies for Firebase Authentication and Cloud Firestore
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


        implementation(platform("com.google.firebase:firebase-bom:34.19.0"))
        // ... el resto de tus dependencias de Firebase (messaging, etc.)

    // Agregar dependencia de Messaging, sin necesidad de especificar versión (gestionada por BoM)
    implementation("com.google.firebase:firebase-messaging")
}