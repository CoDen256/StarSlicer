package com.coden.starslicer.util.loaders

import com.badlogic.gdx.utils.JsonValue

interface Loader <T> {

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