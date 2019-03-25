package com.coden.starslicer.util

import com.coden.starslicer.entities.spacecraft.SpaceCraft
import com.coden.starslicer.entities.attackers.AttackerSnapshot
import com.coden.starslicer.entities.attackers.AttackerType
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.entities.powerups.PowerUpSnapshot
import com.coden.starslicer.entities.spacecraft.BladePoint
import com.coden.starslicer.entities.spacecraft.SpaceCraftSnapshot
import com.coden.starslicer.util.Assets.Companion.attackerConfigMap
import com.coden.starslicer.util.Assets.Companion.bladeConfig
import com.coden.starslicer.util.Assets.Companion.powerupConfigMap
import com.coden.starslicer.util.Assets.Companion.spaceCraftConfig
import com.google.gson.Gson

object EntityLoader {

    val gson = Gson()


    fun loadAttacker(type: AttackerType) = gson.fromJson(attackerConfigMap[type], AttackerSnapshot::class.java)
    fun loadPowerUp(type: PowerUp.PowerUpType) = gson.fromJson(powerupConfigMap[type], PowerUpSnapshot::class.java)
    fun loadSpaceCraft() = gson.fromJson(spaceCraftConfig, SpaceCraftSnapshot::class.java)
    fun loadBlade(num: Int) = gson.fromJson(bladeConfig[num], BladePoint::class.java)
}