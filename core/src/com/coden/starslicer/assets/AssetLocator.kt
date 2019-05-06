package com.coden.starslicer.assets

import com.coden.starslicer.assets.assetUtils.*

class AssetLocator {

    companion object {
        private lateinit var powerUpAssetService: PowerUpAssets
        fun provide(assets: PowerUpAssets){
            powerUpAssetService = assets
        }
        fun getPowerUpAssets(): PowerUpAssets {
            return powerUpAssetService
        }



        private lateinit var attackerAssetService: AttackerAssets
        fun provide(assets: AttackerAssets){
            attackerAssetService = assets
        }
        fun getAttackerAssets(): AttackerAssets {
            return attackerAssetService
        }

        private lateinit var swipeAssetService: SwipeAssets
        fun provide(assets: SwipeAssets){
            swipeAssetService = assets
        }

        fun getSwipeAssets(): SwipeAssets {
            return swipeAssetService
        }

        private lateinit var spaceCraftAssets: SpaceCraftAssets
        fun provide(assets: SpaceCraftAssets){
            spaceCraftAssets = assets
        }

        fun getSpaceCraftAssets(): SpaceCraftAssets {
            return spaceCraftAssets
        }

        private lateinit var configurationAssets: ConfigurationAssets
        fun provide(assets: ConfigurationAssets){
            configurationAssets = assets
        }

        fun getConfigs(): ConfigurationAssets {
            return configurationAssets
        }



    }
}