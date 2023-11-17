package ru.kvf.multitool.feature.gallery.photos_list.domain

import android.net.Uri
import java.util.Date

data class Photo(
    val uri: Uri,
    val name: String,
    val date: Date
)