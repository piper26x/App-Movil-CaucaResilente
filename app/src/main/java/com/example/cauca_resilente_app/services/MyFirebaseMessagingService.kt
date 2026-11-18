package com.example.cauca_resilente_app.services

import android.util.Log
import com.example.cauca_resilente_app.api.CaucaResilienteApiService
import com.example.cauca_resilente_app.ui.AlertRefreshBus
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // ⚠️ CAMBIA ESTA URL por la URL real de tu backend en Render/Railway
    private val baseUrl = "https://cauca-resiliente-api-production.up.railway.app/"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Nuevo token: $token")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val api = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CaucaResilienteApiService::class.java)

                val response = api.registrarToken(
                    CaucaResilienteApiService.DeviceTokenRequest(
                        token = token,
                        topics = listOf("alertas_cauca", "alertas_hidro", "alertas_sismo")
                    )
                )

                if (response.isSuccessful) {
                    Log.d("FCM", "Token registrado en backend correctamente")
                } else {
                    Log.e("FCM", "Error backend al registrar token: HTTP ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("FCM", "Error registrando token: ${e.message}", e)
            }
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FCM", "Mensaje recibido en primer plano: ${remoteMessage.data}")

        AlertRefreshBus.triggerRefresh()
    }
}