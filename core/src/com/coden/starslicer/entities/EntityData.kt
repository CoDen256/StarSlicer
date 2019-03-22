package com.coden.starslicer.entities

import com.badlogic.gdx.utils.Logger
import com.coden.starslicer.entities.attackers.Attacker
import com.coden.starslicer.entities.attackers.AttackerType
import com.coden.starslicer.entities.powerups.HPBoost
import com.coden.starslicer.entities.powerups.PowerUp.PowerUpType.*
import com.coden.starslicer.entities.powerups.Shield
import com.coden.starslicer.entities.powerups.ShockWave
import com.coden.starslicer.hud.PowerUpIcon
import com.coden.starslicer.util.Assets
import java.lang.IllegalArgumentException

data class EntityData(val assets: Assets) {

    val boosts = ArrayList<HPBoost>()
    val shields = ArrayList<Shield>()
    val shockWaves = ArrayList<ShockWave>()

    val maxMissiles = arrayListOf(20, 5, 20, 20) // 0 - missing, 1 - direct, 2 - orbiting , 3 - spiraling
    val maxNuclearBombs = arrayListOf(8, 0) // 0 - missing, 1 - direct
    val maxMeteors = arrayListOf(17, 8, 2) // 0 - small, 1-medium, 2-large
    val maxPowerUpContainers = arrayListOf(2, 0) // 0 - missing 1 - direct
    val maxSatellites = arrayListOf(1, 0) // 0 - missing, 1 - direct


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