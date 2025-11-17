package com.example.mypractice

import android.R.attr.type
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mypractice.data.repository.UnsplashRepository
import com.example.mypractice.screen.ImagePreviewScreen
import com.example.mypractice.screen.UnsplashScreen
import com.example.mypractice.ui.theme.MyPracticeTheme
import com.example.mypractice.viewmodels.UnsplashViewModel
import com.example.mypractice.viewmodels.UnsplashViewModelFactory
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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

                // Navigation
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "unsplash_list"
                ) {

                    // Screen 1 → Grid List
                    composable("unsplash_list") {
                        UnsplashScreen(
                            viewModel = viewModel,
                            onImageClick = { imageUrl ->
                                val encodedUrl = URLEncoder.encode(imageUrl.urls.full, StandardCharsets.UTF_8.toString())
                                navController.navigate("preview/$encodedUrl")
                            }
                        )
                    }

                    // Screen 2 → Full-screen Image Preview
                    composable(
                        route = "preview/{url}",
                        arguments = listOf(navArgument("url") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val encodedUrl = backStackEntry.arguments?.getString("url")!!
                        val imageUrl = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.toString())

                        ImagePreviewScreen(imageUrl = imageUrl) {
                            navController.popBackStack() // handle back button
                        }
                    }
                }
            }
        }
    }
}
