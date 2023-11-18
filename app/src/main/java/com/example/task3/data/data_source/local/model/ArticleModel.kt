package com.example.task3.data.data_source.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.task3.domain.model.Article

@Entity(tableName = "articles_db")
data class ArticleModel(
    @Embedded("article") val article: Article,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)