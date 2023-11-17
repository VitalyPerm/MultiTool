package ru.kvf.multitool.feature.pick_photo.photos_list.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.kvf.multitool.core.App
import ru.kvf.multitool.core.theme.Typography
import ru.kvf.multitool.core.widget.FullScreenLoader
import ru.kvf.multitool.feature.pick_photo.photos_list.domain.CustomDate
import ru.kvf.multitool.feature.pick_photo.photos_list.domain.Photo

@SuppressLint("InlinedApi")
@Composable
fun PhotosListUi(
    vm: PhotosListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by vm.collectAsState()
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(), onResult = { granted ->
            App.toast("granted - $granted")
            vm.permissionChecked(granted)
        })
    vm.collectSideEffect(sideEffect = {
        when (it) {
            PhotosListSideEffect.AskPhotoPermission -> {
                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }

            PhotosListSideEffect.CheckPhotoPermission -> {
                val hasPhotoPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED
                vm.permissionChecked(hasPhotoPermission)
            }
        }
    })

    Crossfade(targetState = state, label = "") {
        when {
            it.isPermissionsGranted.not() -> {
                PleaseGrantPermissionsBanner {
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            }

            it.isLoading -> FullScreenLoader()

            else -> Content(photos = it.photos)
        }
    }


}

@Composable
private fun Content(
    photos: Map<CustomDate, List<Photo>>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .fillMaxSize()
    ) {
        photos.forEach { (date, photos) ->
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = date.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
            items(photos) { photo ->
                AsyncImage(
                    model = photo.uri,
                    contentDescription = "photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(1.dp)
                )
            }
        }
    }
}

@Composable
private fun PleaseGrantPermissionsBanner(
    onClickGrant: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Photo permissions not granted",
            style = Typography.titleLarge
        )

        Button(
            onClick = onClickGrant,
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = "Grant permission")
        }
    }
}