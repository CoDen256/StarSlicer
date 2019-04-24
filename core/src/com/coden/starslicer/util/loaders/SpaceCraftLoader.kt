package com.coden.starslicer.util.loaders

import com.coden.starslicer.entities.spacecraft.SpaceCraftSnapshot
import com.coden.starslicer.util.assets.AssetLocator

class SpaceCraftLoader: Loader<SpaceCraftSnapshot> {
    override val configMap = AssetLocator.getConfigs().spaceCraftConfig
    fun load(): SpaceCraftSnapshot{
        return loadReflection(configMap, SpaceCraftSnapshot::class.java)
    }
}