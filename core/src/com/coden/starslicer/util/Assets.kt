package com.coden.starslicer.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.coden.starslicer.entities.powerups.PowerUp

class Assets {
    val manager = AssetManager()

    val POWER_UP_ATLAS = AssetDescriptor<TextureAtlas>("powerups.atlas", TextureAtlas::class.java)

    var powerUpAssets: PowerUpAssets
    init {
        manager.setLoader(TextureAtlas::class.java, TextureAtlasLoader(InternalFileHandleResolver()))
        manager.load(POWER_UP_ATLAS)
        manager.finishLoading()
        val atlas = manager.get(POWER_UP_ATLAS)
        powerUpAssets = PowerUpAssets(atlas)
    }

    class PowerUpAssets(atlas: TextureAtlas){
        private val map = mapOf(
                PowerUp.PowerUpType.HPBOOST to atlas.findRegion("hpboost") as TextureRegion,
                PowerUp.PowerUpType.SHIELD to atlas.findRegion("shield") as TextureRegion,
                PowerUp.PowerUpType.SHOCKWAVE to atlas.findRegion("shockwave") as TextureRegion
        )

        fun getTexture(type: PowerUp.PowerUpType): TextureRegion? {
            return map[type]
        }
    }
}