package com.coden.starslicer.entities

import com.coden.starslicer.entities.powerups.HPBoost
import com.coden.starslicer.entities.powerups.PowerUp.PowerUpType.*
import com.coden.starslicer.entities.powerups.Shield
import com.coden.starslicer.entities.powerups.ShockWave
import com.coden.starslicer.hud.PowerUpIcon
import com.coden.starslicer.util.Assets

data class EntityData(val assets: Assets) {

    val powerUpIcons = arrayListOf(
            PowerUpIcon(SHIELD, powerUpIconAssets.getTexture(SHIELD)),
            PowerUpIcon(HPBOOST, powerUpIconAssets.getTexture(HPBOOST)),
            PowerUpIcon(SHOCKWAVE, powerUpIconAssets.getTexture(SHOCKWAVE))
    )

    //TODO:observer
    var score = 0
    var coins = 0

    val powerUpIconAssets: Assets.PowerUpAssets
    get() = assets.powerUpAssets

    val attackerAssets: Assets.AttackerAssets
    get() =  assets.attackerAssets

}