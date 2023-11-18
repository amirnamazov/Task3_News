package com.example.task3.data.data_source.local.model

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.task3.data.data_source.remote.dto.news.Source
import com.google.gson.Gson

@ProvidedTypeConverter
class SourceTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun toSource(json: String?): Source = gson.fromJson(json, Source::class.java)

    @TypeConverter
    fun toString(source: Source?): String = gson.toJson(source)
}