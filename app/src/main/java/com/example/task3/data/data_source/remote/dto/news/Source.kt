package com.example.task3.data.data_source.remote.dto.news

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    val id: String?,
    val name: String?
) : Parcelable