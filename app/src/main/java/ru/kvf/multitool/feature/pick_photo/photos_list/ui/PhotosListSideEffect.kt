package ru.kvf.multitool.feature.pick_photo.photos_list.ui

sealed interface PhotosListSideEffect {
    object AskPhotoPermission : PhotosListSideEffect
    object CheckPhotoPermission : PhotosListSideEffect
}