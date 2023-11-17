package ru.kvf.multitool.feature.pick_photo.photos_list.domain

import android.net.Uri
import java.time.LocalDate
import java.util.Date

data class Photo(
    val uri: Uri,
    val name: String,
    val date: Date
)