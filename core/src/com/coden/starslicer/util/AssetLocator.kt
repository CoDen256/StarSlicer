package com.coden.starslicer.util

import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.hud.PowerUpIcon

class AssetLocator {

    companion object {
        private lateinit var powerUpAssetService: PowerUpAssets
        fun provide(assets: PowerUpAssets){
            powerUpAssetService = assets
        }
        fun getPowerUpAssets(): PowerUpAssets{
            return powerUpAssetService
        }



        private lateinit var attackerAssetService: AttackerAssets
        fun provide(assets: AttackerAssets){
            attackerAssetService = assets
        }
        fun getAttackerAssets(): AttackerAssets{
            return attackerAssetService
        }



    }
}