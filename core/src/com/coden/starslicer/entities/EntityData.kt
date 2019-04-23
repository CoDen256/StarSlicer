package com.coden.starslicer.entities

import com.coden.starslicer.entities.powerups.HPBoost
import com.coden.starslicer.entities.powerups.PowerUp.PowerUpType.*
import com.coden.starslicer.entities.powerups.Shield
import com.coden.starslicer.entities.powerups.ShockWave
import com.coden.starslicer.hud.PowerUpIcon
import com.coden.starslicer.util.Assets
import com.coden.starslicer.util.AttackerAssets
import com.coden.starslicer.util.PowerUpAssets

data class EntityData(val assets: Assets) {



    //TODO:observer
    var score = 0
    var coins = 0


}