package com.coden.starslicer.util.loaders

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.JsonValue
import java.lang.Exception

interface Loader <T>{

    companion object {
        fun loadJson(path: String) = JsonReader().parse(Gdx.files.internal(path))
    }

    fun loadAllConfigs(configList: ArrayList<JsonValue>): ArrayList<T>{
        return arrayListOf()
    }
    fun loadConfigToMultiple(config: JsonValue): ArrayList<T>{
        return arrayListOf()
    }
    fun loadConfigToSingle(config: JsonValue) : T?{
        return null
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