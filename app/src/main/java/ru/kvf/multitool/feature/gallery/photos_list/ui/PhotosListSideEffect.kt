package ru.kvf.multitool.feature.gallery.photos_list.ui

sealed interface PhotosListSideEffect {
    object AskPhotoPermission : PhotosListSideEffect
    object CheckPhotoPermission : PhotosListSideEffect
}