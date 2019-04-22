package com.coden.starslicer.util.loaders

import com.coden.starslicer.entities.powerups.PowerUpSnapshot
import com.coden.starslicer.util.Assets

class PowerUpLoader: Loader<PowerUpSnapshot> {
    override val configMap = Assets.powerupConfigMap
}