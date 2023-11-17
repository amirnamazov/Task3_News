package com.example.task3.domain.model

import android.os.Parcelable
import com.example.task3.data.data_source.remote.dto.news.Source
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Parcelable