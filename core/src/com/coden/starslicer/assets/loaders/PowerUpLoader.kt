package com.coden.starslicer.assets.loaders

import com.coden.starslicer.gameObjects.powerups.PowerUpSnapshot
import com.coden.starslicer.assets.AssetLocator
import com.coden.starslicer.gameObjects.powerups.PowerUpType

class PowerUpLoader: Loader<PowerUpSnapshot> {
    override val configMap = AssetLocator.getConfigs().powerupConfigMap
    fun load(ability: PowerUpType): PowerUpSnapshot{
        return loadReflection(configMap[ability]!!, PowerUpSnapshot::class.java)
    }
}