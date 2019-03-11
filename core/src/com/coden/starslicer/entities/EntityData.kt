package com.coden.starslicer.entities

import com.coden.starslicer.entities.powerups.HPBoost
import com.coden.starslicer.entities.powerups.Shield
import com.coden.starslicer.entities.powerups.ShockWave
import com.coden.starslicer.hud.HUD

data class EntityData(val spaceCraft: SpaceCraft,val hud: HUD) {

    val boosts = ArrayList<HPBoost>()
    val shields = ArrayList<Shield>()
    val shockWaves = ArrayList<ShockWave>()
}