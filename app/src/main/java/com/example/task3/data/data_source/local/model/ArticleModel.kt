package com.example.task3.data.data_source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.task3.domain.model.Article

@Entity(tableName = "articles_db")
data class ArticleModel(
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "publishedAt") val publishedAt: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "urlToImage") val urlToImage: String?,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
) {
    companion object {
        fun toModel(article: Article): ArticleModel = article.run {
            ArticleModel(
                author ?: "",
                content ?: "",
                description ?: "",
                publishedAt ?: "",
                title ?: "",
                url ?: "",
                urlToImage ?: ""
            )
        }
    }
}