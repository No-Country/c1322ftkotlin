package com.nocuntry.c1322ftkotlin

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.nocuntry.c1322ftkotlin.model.NasaApiService
import com.nocuntry.c1322ftkotlin.ui.theme.C1322ftkotlinTheme

class MainActivity : ComponentActivity() {

    private val apiService = NasaApiService.create()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = Color.Black) {

                AppNavigation(apiService)
            }
        }
    }
}
