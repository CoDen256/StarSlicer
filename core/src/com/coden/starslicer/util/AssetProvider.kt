package com.coden.starslicer.util

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class AssetProvider{
    val manager = AssetManager()
    val progress: Float get() = manager.progress
    fun dispose() = manager.dispose()


    private val POWERUP_ATLAS_DESCRIPTOR = AssetDescriptor<TextureAtlas>("ui/icons/powerups/powerups.atlas", TextureAtlas::class.java)
    private val ATTACKER_ATLAS_DESCRIPTOR = AssetDescriptor<TextureAtlas>("entities/animation/attackers/attackers3.atlas", TextureAtlas::class.java)

    private val SWIPE_TEXTURE_DESCRIPTORS = generateSequence { "entities/animation/blades/gradient${--SwipeAssets.max}.png" }
            .map{AssetDescriptor<Texture>(it, Texture::class.java)}.takeWhile { SwipeAssets.max >= 0 }.toList()

    lateinit var powerUpAssets: PowerUpAssets
    lateinit var attackerAssets: AttackerAssets
    lateinit var swipeAssets: SwipeAssets


    fun load(){
        Log.info("Loading TextureAtlases...", Log.LogType.ASSETS)

        manager.setLoader(TextureAtlas::class.java, TextureAtlasLoader(InternalFileHandleResolver()))
        manager.load(POWERUP_ATLAS_DESCRIPTOR)
        manager.load(ATTACKER_ATLAS_DESCRIPTOR)

        manager.setLoader(Texture::class.java, TextureLoader(InternalFileHandleResolver()))
        SWIPE_TEXTURE_DESCRIPTORS.forEach { manager.load(it) }
    }

    fun update(): Boolean =  manager.update()


    fun finish(){
        Log.info("Finish Loading...", Log.LogType.ASSETS)
        manager.finishLoading()

        Log.info("Creating assets", Log.LogType.ASSETS)
        powerUpAssets = PowerUpAssets(manager.get(POWERUP_ATLAS_DESCRIPTOR))
        attackerAssets = AttackerAssets(manager.get(ATTACKER_ATLAS_DESCRIPTOR))
        swipeAssets = SwipeAssets(SWIPE_TEXTURE_DESCRIPTORS.map { manager.get(it) })
    }




}

