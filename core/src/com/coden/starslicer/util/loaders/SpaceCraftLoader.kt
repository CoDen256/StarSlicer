package com.coden.starslicer.util.loaders

import com.coden.starslicer.entities.spacecraft.SpaceCraftSnapshot
import com.coden.starslicer.util.assets.Assets

class SpaceCraftLoader: Loader<SpaceCraftSnapshot> {
    override val configMap = Assets.spaceCraftConfig
    fun load(): SpaceCraftSnapshot{
        return loadReflection(configMap, SpaceCraftSnapshot::class.java)
    }
}