package ru.kvf.multitool.feature.pick_photo

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import ru.kvf.multitool.core.App

@SuppressLint("InlinedApi")
@Composable
fun PickPhotoUi() {

    val context = LocalContext.current

    val photos = remember { mutableStateListOf<Photo>() }

    fun loadPhotos() {
        val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media._ID
        )
        val query = App.context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            null
        )
        query?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                App.print("name")
                photos.add(Photo(uri, name))
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(), onResult = { granted ->
            Toast.makeText(context, "granted - $granted", Toast.LENGTH_SHORT).show()
            if (granted) loadPhotos()
        })

    LaunchedEffect(key1 = Unit, block = {
        val hasPhotoPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED
        if (hasPhotoPermission.not()) {
            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            Toast.makeText(context, "Read photo permission already granted", Toast.LENGTH_SHORT)
                .show()
            loadPhotos()
        }
    }
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(photos) { photo ->
            AsyncImage(
                model = photo.uri,
                contentDescription = "photo",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }


}