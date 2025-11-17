package com.example.mypractice.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.mypractice.models.UnsplashPhoto
import com.example.mypractice.viewmodels.UnsplashViewModel

@Composable
fun UnsplashScreen(
    viewModel: UnsplashViewModel,
                   onImageClick: (UnsplashPhoto) -> Unit) {

    val photos by viewModel.photos.collectAsState()

    // Load photos only once
    LaunchedEffect(Unit) {
        viewModel.loadPhotos()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(photos) { photo ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable{ onImageClick(photo) }
            ) {

                Image(
                    painter = rememberAsyncImagePainter(photo.urls.small),
                    contentDescription = photo.description,
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = photo.description ?: "No description")
            }
        }
    }
}
