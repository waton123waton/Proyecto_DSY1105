package com.example.proyecto_dsy1105

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.proyecto_dsy1105.navigation.AppNavigation
import com.example.proyecto_dsy1105.ui.theme.Proyecto_DSY1105Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Proyecto_DSY1105Theme {
                AppNavigation()
            }
        }
    }
}