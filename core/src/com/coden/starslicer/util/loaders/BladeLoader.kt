package com.coden.starslicer.util.loaders

import com.badlogic.gdx.utils.JsonValue
import com.coden.starslicer.entities.spacecraft.BladePoint
import com.coden.starslicer.util.assets.AssetLocator

class BladeLoader : Loader<BladePoint>{
    override val configMap = AssetLocator.getConfigs().bladesConfig

    fun load(num: Int): BladePoint{
        return loadToSnapshot(configMap[num])
    }

    fun loadToSnapshot(config: JsonValue): BladePoint {
        return loadReflection(config, BladePoint::class.java)
    }
}