package com.example.mypractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mypractice.data.repository.UnsplashRepository
import com.example.mypractice.screen.UnsplashScreen
import com.example.mypractice.ui.theme.MyPracticeTheme
import com.example.mypractice.viewmodels.UnsplashViewModel
import com.example.mypractice.viewmodels.UnsplashViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyPracticeTheme {

                val context = LocalContext.current

                val repository = remember { UnsplashRepository(context) }

                val viewModel: UnsplashViewModel = viewModel(
                    factory = UnsplashViewModelFactory(repository)
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    Box(modifier = Modifier.padding(innerPadding)) {
                        UnsplashScreen(viewModel)
                    }
                }
            }
        }
    }
}
