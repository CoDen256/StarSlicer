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

class Assets{
    private val manager = AssetManager()

    val POWER_UP_ATLAS_DESCRIPTOR = AssetDescriptor<TextureAtlas>("powerups.atlas", TextureAtlas::class.java)
    lateinit var powerUpAssets: PowerUpAssets

    var progress = 0f
        get() = manager.progress

    fun load() {
        loadAllTextures()
    }

    fun loadAllTextures() {
        loadPowerUpTextures()
    }


    fun loadPowerUpTextures() {
        manager.setLoader(TextureAtlas::class.java, TextureAtlasLoader(InternalFileHandleResolver()))
        manager.load(POWER_UP_ATLAS_DESCRIPTOR)
    }

    fun updateLoading(): Boolean {
        return manager.update()
    }

    fun finishLoading() {
        manager.finishLoading()
        initialize()
    }

    fun initialize() {
        powerUpAssets = PowerUpAssets(manager.get(POWER_UP_ATLAS_DESCRIPTOR))
    }

    fun dispose() {
        manager.dispose()
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