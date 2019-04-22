package com.coden.starslicer.util.loaders

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.JsonValue
import com.badlogic.gdx.utils.JsonWriter
import com.google.gson.Gson
import java.lang.Exception

interface Loader<T> {

    val configMap: Any

    companion object {
        fun loadJson(path: String) = JsonReader().parse(Gdx.files.internal(path))
    }

    fun loadReflection(config: JsonValue, type: Class<T>) : T {
        // Loads pure Reflection of class
        return Gson().fromJson(config.toJson(JsonWriter.OutputType.json), type)
    }
}

fun JsonValue.getFloatArray(name: String): Array<Float>{
    val obj = get(name) ?: throw Exception("Unable to find $name in $this")
    if (obj.isArray) return obj.asFloatArray().toTypedArray()

    return arrayOf(getFloat(name))
}

fun JsonValue.getBooleanArray(name: String): Array<Boolean>{
    val obj = get(name)?: throw Exception("Unable to find $name in $this")
    if (obj.isArray) return obj.asBooleanArray().toTypedArray()

    return arrayOf(getBoolean(name))
}

fun JsonValue.getIntArray(name: String): Array<Int>{
    val obj = get(name)?: throw Exception("Unable to find $name in $this")
    if (obj.isArray) return obj.asIntArray().toTypedArray()

    return arrayOf(getInt(name))
}