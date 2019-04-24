package com.coden.starslicer.util.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.coden.starslicer.util.Log

class AssetProvider{
    val manager = AssetManager()
    val progress: Float get() = manager.progress
    fun dispose() = manager.dispose()


    private val POWERUP_ATLAS_DESCRIPTOR = AssetDescriptor<TextureAtlas>("ui/icons/powerups/powerups.atlas", TextureAtlas::class.java)
    private val ATTACKER_ATLAS_DESCRIPTOR = AssetDescriptor<TextureAtlas>("entities/animation/attackers/attackers3.atlas", TextureAtlas::class.java)

    private val SWIPE_TEXTURE_DESCRIPTORS = generateSequence { "entities/animation/blades/gradient${--SwipeAssets.max}.png" }
            .map{AssetDescriptor<Texture>(it, Texture::class.java)}.takeWhile { SwipeAssets.max >= 0 }.toList()

    private val SPACECRAFT_TEXTURE_DESCRIPTOR = AssetDescriptor<Texture>("entities/animation/spacecraft/spacecraft3tex.png", Texture::class.java)

    lateinit var powerUpAssets: PowerUpAssets
    lateinit var attackerAssets: AttackerAssets
    lateinit var swipeAssets: SwipeAssets
    lateinit var spaceCraftAssets: SpaceCraftAssets
    lateinit var configurationAssets: ConfigurationAssets


    fun load(){
        Log.info("Loading TextureAtlases...", Log.LogType.ASSETS)

        manager.setLoader(TextureAtlas::class.java, TextureAtlasLoader(InternalFileHandleResolver()))
        manager.load(POWERUP_ATLAS_DESCRIPTOR)
        manager.load(ATTACKER_ATLAS_DESCRIPTOR)

        manager.setLoader(Texture::class.java, TextureLoader(InternalFileHandleResolver()))
        SWIPE_TEXTURE_DESCRIPTORS.forEach { manager.load(it) }
        manager.load(SPACECRAFT_TEXTURE_DESCRIPTOR)

    }

    fun update(): Boolean =  manager.update()


    fun finish(){
        Log.info("Finish Loading...", Log.LogType.ASSETS)
        manager.finishLoading()

        Log.info("Creating assets", Log.LogType.ASSETS)
        powerUpAssets = PowerUpAssets(manager.get(POWERUP_ATLAS_DESCRIPTOR))
        attackerAssets = AttackerAssets(manager.get(ATTACKER_ATLAS_DESCRIPTOR))
        swipeAssets = SwipeAssets(SWIPE_TEXTURE_DESCRIPTORS.map { manager.get(it) })
        spaceCraftAssets = SpaceCraftAssets(manager.get(SPACECRAFT_TEXTURE_DESCRIPTOR))
        configurationAssets = ConfigurationAssets()
    }




}

