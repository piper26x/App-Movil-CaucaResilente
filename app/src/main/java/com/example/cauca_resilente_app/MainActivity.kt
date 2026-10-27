package com.example.cauca_resilente_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.tooling.preview.Preview
import com.example.cauca_resilente_app.ui.theme.AzulOscuro
import com.example.cauca_resilente_app.ui.theme.Cauca_Resilente_AppTheme


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cauca_resilente_app.ui.Navigation.AppNavigation
import com.example.cauca_resilente_app.ui.theme.AmarilloInstitucional
import com.example.cauca_resilente_app.ui.theme.TextoBlanco

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Cauca_Resilente_AppTheme {
                AppNavigation()
            }
        }
    }
}



@Composable
fun BienvenidaScreen(
      onLoginClick: () -> Unit,
      onRegisterClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AzulOscuro) // Fondo Azul Oscuro
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // TODO: Reemplazar con el logo real (R.drawable.logo_cauca_resiliente)
        // Icono placeholder (Escudo)
        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_compass), // Placeholder
            contentDescription = "Logo Cauca Resiliente",
            tint = AmarilloInstitucional,
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Cauca\nResiliente",
            color = TextoBlanco,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 40.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Sistema de apoyo a la localización y gestión de emergencias",
            color = TextoBlanco.copy(alpha = 0.8f),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Botón Iniciar Sesión
        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AmarilloInstitucional),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Iniciar sesión",
                color = AzulOscuro,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Crear Cuenta (Outlined)
        OutlinedButton(
            onClick = onRegisterClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                width = 2.dp,

            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Crear cuenta",
                color = TextoBlanco,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Identificación Institucional
        Text(
            text = "UNIVERSIDAD AUTÓNOMA DEL CAUCA",
            color = TextoBlanco.copy(alpha = 0.6f),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

