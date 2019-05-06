package com.coden.starslicer.assets.loaders

import com.badlogic.gdx.utils.JsonValue
import com.coden.starslicer.gameObjects.spacecraft.BladePoint
import com.coden.starslicer.assets.AssetLocator

class BladeLoader : Loader<BladePoint>{
    override val configMap = AssetLocator.getConfigs().bladesConfig

    fun load(num: Int): BladePoint{
        return loadToSnapshot(configMap[num])
    }

    fun loadToSnapshot(config: JsonValue): BladePoint {
        return loadReflection(config, BladePoint::class.java)
    }
}