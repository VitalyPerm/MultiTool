package ru.kvf.multitool.feature.gallery.photos_list.ui

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.kvf.multitool.feature.gallery.photos_list.domain.CustomDate
import ru.kvf.multitool.feature.gallery.photos_list.domain.Photo
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class PhotosListViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : ContainerHost<PhotosListState, PhotosListSideEffect>, ViewModel() {

    override val container: Container<PhotosListState, PhotosListSideEffect> =
        container(PhotosListState())

    init {
        intent {
            postSideEffect(PhotosListSideEffect.CheckPhotoPermission)
        }
    }


    fun getPhotos() = intent {
        reduce { state.copy(isLoading = true) }
        val photos = mutableListOf<Photo>()
        val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_TAKEN
        )
        val query = context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            null
        )
        query?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            val dateColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val date = cursor.getLong(dateColumn)
                val uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                photos.add(Photo(uri, name, Date(date)))
            }
            val sorted = photos.groupBy {
                val date = Calendar.getInstance()
                date.time = it.date
                CustomDate(date)
            }
            reduce { state.copy(photos = sorted, isLoading = false) }
        }
    }

    fun permissionChecked(granted: Boolean) = intent {
        reduce { state.copy(isPermissionsGranted = granted) }
        if (granted) getPhotos()
    }

}