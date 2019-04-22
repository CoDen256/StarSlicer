package com.coden.starslicer.util

import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.entities.powerups.PowerUpSnapshot
import com.coden.starslicer.entities.spacecraft.BladePoint
import com.coden.starslicer.entities.spacecraft.SpaceCraftSnapshot
import com.coden.starslicer.util.Assets.Configs.bladesConfig
import com.coden.starslicer.util.Assets.Configs.powerupConfigMap
import com.coden.starslicer.util.Assets.Configs.spaceCraftConfig
import com.google.gson.Gson

object EntityLoader {

    val gson = Gson()


    //fun loadAttacker(type: AttackerType) = gson.fromJson(attackerConfigMap[type], EntitySnapshot::class.java)
    //fun loadPowerUp(type: PowerUp.PowerUpType) = gson.fromJson(powerupConfigMap[type], PowerUpSnapshot::class.java)
    //fun loadSpaceCraft() = gson.fromJson(spaceCraftConfig, SpaceCraftSnapshot::class.java)
    //fun loadBlade(num: Int) = gson.fromJson(bladesConfig[num], BladePoint::class.java)
}