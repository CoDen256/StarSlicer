package com.coden.starslicer.util.loaders

import com.badlogic.gdx.utils.JsonValue
import com.coden.starslicer.entities.spacecraft.BladePoint
import com.coden.starslicer.util.assets.Assets

class BladeLoader : Loader<BladePoint>{
    override val configMap = Assets.bladesConfig

    fun load(num: Int): BladePoint{
        return loadToSnapshot(Assets.bladesConfig[num])
    }

    fun loadToSnapshot(config: JsonValue): BladePoint {
        return loadReflection(config, BladePoint::class.java)
    }
}