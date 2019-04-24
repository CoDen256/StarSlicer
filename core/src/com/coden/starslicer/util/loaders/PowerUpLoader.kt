package com.coden.starslicer.util.loaders

import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.entities.powerups.PowerUpSnapshot
import com.coden.starslicer.util.assets.Assets

class PowerUpLoader: Loader<PowerUpSnapshot> {
    override val configMap = Assets.powerupConfigMap
    fun load(ability: PowerUp.PowerUpType): PowerUpSnapshot{
        return loadReflection(configMap[ability]!!, PowerUpSnapshot::class.java)
    }
}