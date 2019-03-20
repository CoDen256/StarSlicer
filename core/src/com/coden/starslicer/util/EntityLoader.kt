package com.coden.starslicer.util

import com.coden.starslicer.entities.SpaceCraft.SpaceCraft
import com.coden.starslicer.entities.attackers.AttackerSnapshot
import com.coden.starslicer.entities.attackers.AttackerType
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.entities.powerups.PowerUpSnapshot
import com.coden.starslicer.util.Assets.Companion.attackerConfigMap
import com.coden.starslicer.util.Assets.Companion.powerupConfigMap
import com.coden.starslicer.util.Assets.Companion.spaceCraftConfig
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import java.io.FileReader

object EntityLoader {

    val gson = Gson()


    fun loadAttacker(type: AttackerType) = gson.fromJson(attackerConfigMap[type], AttackerSnapshot::class.java)
    fun loadPowerUp(type: PowerUp.PowerUpType) = gson.fromJson(powerupConfigMap[type], PowerUpSnapshot::class.java)
    fun loadSpaceCraft() = gson.fromJson(spaceCraftConfig, SpaceCraft::class.java)
}