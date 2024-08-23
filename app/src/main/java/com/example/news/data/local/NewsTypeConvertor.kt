package com.example.news.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.news.domain.model.Source

@ProvidedTypeConverter
class NewsTypeConvertor {
    @TypeConverter
    fun sourceToString(source: Source) : String {
        return "${source.id}, ${source.name}"
    }
    @TypeConverter
    fun stringToSource(string: String) : Source {
        val split = string.split(", ")
        return Source(split[0], split[1])
    }
}