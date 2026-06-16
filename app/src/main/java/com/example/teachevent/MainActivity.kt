package com.example.teachevent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.teachevent.data.local.SessionDataStore
import com.example.teachevent.ui.navigation.NavGraph
import com.example.teachevent.ui.theme.TeachEventTheme
import com.example.teachevent.ui.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val sessionDataStore = SessionDataStore(this)
        val loginViewModel = LoginViewModel(sessionDataStore)

        setContent {
            TeachEventTheme {
                val windowSizeClass = calculateWindowSizeClass(this)

                NavGraph(
                    sessionDataStore = sessionDataStore,
                    loginViewModel = loginViewModel,
                    windowSizeClass = windowSizeClass.widthSizeClass
                )
            }
        }
    }
}