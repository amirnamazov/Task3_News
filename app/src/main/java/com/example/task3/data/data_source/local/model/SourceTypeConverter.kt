package com.example.task3.data.data_source.local.model

import androidx.room.TypeConverter
import com.example.task3.data.data_source.remote.dto.news.Source
import com.google.gson.Gson

class SourceTypeConverter {

    @TypeConverter
    fun toSource(json: String?): Source = Gson().fromJson(json, Source::class.java)

    @TypeConverter
    fun toString(source: Source?): String = Gson().toJson(source)
}