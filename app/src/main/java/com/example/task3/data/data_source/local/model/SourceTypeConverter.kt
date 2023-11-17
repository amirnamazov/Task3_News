package com.example.task3.data.data_source.local.model

import androidx.room.TypeConverter
import com.example.task3.data.data_source.remote.dto.news.Source
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SourceTypeConverter {

    @TypeConverter
    fun toSource(string: String?): Source = Json.decodeFromString(string!!)

    @TypeConverter
    fun toString(source: Source?): String = Json.encodeToString(source!!)
}