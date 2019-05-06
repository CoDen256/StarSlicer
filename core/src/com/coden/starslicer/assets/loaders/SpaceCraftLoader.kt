package com.coden.starslicer.assets.loaders

import com.coden.starslicer.gameObjects.spacecraft.SpaceCraftSnapshot
import com.coden.starslicer.assets.AssetLocator

class SpaceCraftLoader: Loader<SpaceCraftSnapshot> {
    override val configMap = AssetLocator.getConfigs().spaceCraftConfig
    fun load(): SpaceCraftSnapshot{
        return loadReflection(configMap, SpaceCraftSnapshot::class.java)
    }
}