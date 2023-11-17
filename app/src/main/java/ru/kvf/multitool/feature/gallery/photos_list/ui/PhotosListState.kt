package ru.kvf.multitool.feature.gallery.photos_list.ui

import ru.kvf.multitool.feature.gallery.photos_list.domain.CustomDate
import ru.kvf.multitool.feature.gallery.photos_list.domain.Photo

data class PhotosListState(
    val photos: Map<CustomDate, List<Photo>> = emptyMap(),
    val isLoading: Boolean = true,
    val isPermissionsGranted: Boolean = false
)