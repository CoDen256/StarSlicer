package com.coden.starslicer.util

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.coden.starslicer.entities.attackers.Attacker
import com.coden.starslicer.entities.attackers.AttackerType
import com.coden.starslicer.entities.attackers.AttackerType.*
import com.coden.starslicer.entities.powerups.PowerUp

class Assets{
    private val manager = AssetManager()

    private val POWER_UP_ATLAS_DESCRIPTOR = AssetDescriptor<TextureAtlas>("entities/powerups/powerups.atlas", TextureAtlas::class.java)
    private val CONTAINER_ATLAS_DESCRIPTOR = AssetDescriptor<TextureAtlas>("entities/containers/containers.atlas", TextureAtlas::class.java)
    private val ATTACKER_ATLAS_DESCRIPTOR = AssetDescriptor<TextureAtlas>("entities/attackers/attackers.atlas", TextureAtlas::class.java)

    lateinit var powerUpAssets: PowerUpAssets
    lateinit var containerAssets: ContainerAssets
    lateinit var attackerAssets: AttackerAssets

    var progress: Float
        get() = manager.progress
        set(value) {}

    fun load() {
        loadAllTextures()
    }

    fun loadAllTextures() {
        loadTextureAtlases()
    }


    fun loadTextureAtlases() {
        manager.setLoader(TextureAtlas::class.java, TextureAtlasLoader(InternalFileHandleResolver()))
        manager.load(POWER_UP_ATLAS_DESCRIPTOR)
        manager.load(CONTAINER_ATLAS_DESCRIPTOR)
        manager.load(ATTACKER_ATLAS_DESCRIPTOR)
    }

    fun updateLoading(): Boolean {
        return manager.update()
    }

    fun finishLoading() {
        manager.finishLoading()
        initialize()
    }

    private fun initialize() {
        powerUpAssets = PowerUpAssets(manager.get(POWER_UP_ATLAS_DESCRIPTOR))
        containerAssets = ContainerAssets(manager.get(CONTAINER_ATLAS_DESCRIPTOR))
        attackerAssets = AttackerAssets(manager.get(ATTACKER_ATLAS_DESCRIPTOR))

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

        fun getTexture(type: PowerUp.PowerUpType): TextureRegion? = map[type]

        val width = map[PowerUp.PowerUpType.SHIELD]!!.regionWidth
        val height = map[PowerUp.PowerUpType.SHIELD]!!.regionHeight

    }

    class ContainerAssets(atlas: TextureAtlas){
        private val map = mapOf(
                SATELLITE to atlas.findRegion("satellite") as TextureRegion,
                POWERUP_CONTAINER to atlas.findRegion("powerUpContainer") as TextureRegion
        )
        fun getTexture(type: AttackerType): TextureRegion? = map[type]
    }

    class AttackerAssets(atlas: TextureAtlas){
        private val map = mapOf(
                MISSILE to atlas.findRegion("missile") as TextureRegion,
                NUCLEAR_BOMB to atlas.findRegion("nuclearbomb") as TextureRegion,
                SMALL_METEOR to atlas.findRegion("smallMeteor") as TextureRegion,
                MEDIUM_METEOR to atlas.findRegion("mediumMeteor") as TextureRegion,
                LARGE_METEOR to atlas.findRegion("largeMeteor") as TextureRegion
        )
        fun getTexture(type: AttackerType): TextureRegion? = map[type]
    }
}