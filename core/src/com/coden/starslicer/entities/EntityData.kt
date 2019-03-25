package com.coden.starslicer.entities

import com.coden.starslicer.entities.powerups.HPBoost
import com.coden.starslicer.entities.powerups.PowerUp.PowerUpType.*
import com.coden.starslicer.entities.powerups.Shield
import com.coden.starslicer.entities.powerups.ShockWave
import com.coden.starslicer.hud.PowerUpIcon
import com.coden.starslicer.util.Assets

data class EntityData(val assets: Assets) {

    val boosts = ArrayList<HPBoost>()
    val shields = ArrayList<Shield>()
    val shockWaves = ArrayList<ShockWave>()

    // TODO: ratio amount can be applied to speed when difficulty is bigger (applied in ProgressClass)


    val powerUpIcons = arrayListOf(
            PowerUpIcon(SHIELD, powerUpIconAssets.getTexture(SHIELD)),
            PowerUpIcon(HPBOOST, powerUpIconAssets.getTexture(HPBOOST)),
            PowerUpIcon(SHOCKWAVE, powerUpIconAssets.getTexture(SHOCKWAVE))
    )

    val powerUpIconAssets: Assets.PowerUpAssets
    get() = assets.powerUpAssets

    val attackerAssets: Assets.AttackerAssets
    get() =  assets.attackerAssets

}