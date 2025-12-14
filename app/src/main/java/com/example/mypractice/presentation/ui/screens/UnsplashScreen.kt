package com.example.mypractice.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.DefaultFillType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.mypractice.domain.model.UnsplashPhoto
import com.example.mypractice.presentation.viewmodels.UnsplashViewModel
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.mypractice.R


/*@Composable
fun UnsplashScreen(
    viewModel: UnsplashViewModel,
                   onImageClick: (UnsplashPhoto) -> Unit)
{

    val photos by viewModel.photos.collectAsState()

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
}*/

@Composable
fun UnsplashScreen(
    viewModel: UnsplashViewModel,
    onImageClick: (UnsplashPhoto) -> Unit
) {
    val lazyPhotos = viewModel.photos.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        items(
            count = lazyPhotos.itemCount,
            key = { index -> lazyPhotos[index]?.id ?: index }
        ) { index ->

            val photo = lazyPhotos[index]

            photo?.let { it ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            onImageClick(it)
                        }
                ) {

                    if (it.urls.small.isNotEmpty() && it.description!=null && it.description.isNotEmpty()){
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(it.urls.small)
                                .crossfade(true)
                                .size(250)
                                .build(),
                            placeholder = painterResource(R.drawable.placeholder),
                            contentDescription = it.description,
                            modifier = Modifier.size(100.dp, 100.dp)
                                .fillMaxWidth()
                                .fillMaxSize()
                        )


                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = it.description ?: "No description",
                            minLines = 1,
                            maxLines = 1,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                    }


                }
            }
        }

        // Paging Load States
        lazyPhotos.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Text(
                            text = "Loading...",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        Text(
                            text = "Loading more...",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    item {
                        Text(
                            text = "Error occurred while loading!",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        )
                    }
                }
            }
        }
    }
}

