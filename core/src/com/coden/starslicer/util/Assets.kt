package com.coden.starslicer.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.coden.starslicer.entities.attackers.AttackerType
import com.coden.starslicer.entities.attackers.AttackerType.*
import com.coden.starslicer.entities.powerups.HPBoost
import com.coden.starslicer.entities.powerups.PowerUp
import java.io.*

class Assets{
    private val manager = AssetManager()

    private val POWER_UP_ATLAS_DESCRIPTOR = AssetDescriptor<TextureAtlas>("entities/powerups/powerups.atlas", TextureAtlas::class.java)
    private val ATTACKER_ATLAS_DESCRIPTOR = AssetDescriptor<TextureAtlas>("entities/attackers/attackers.atlas", TextureAtlas::class.java)


    lateinit var powerUpAssets: PowerUpAssets
    lateinit var attackerAssets: AttackerAssets
    //lateinit var attackerConfigs:

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
        Gdx.app.log("Assets", "Loading TextureAtlases...")
        manager.setLoader(TextureAtlas::class.java, TextureAtlasLoader(InternalFileHandleResolver()))
        manager.load(POWER_UP_ATLAS_DESCRIPTOR)
        manager.load(ATTACKER_ATLAS_DESCRIPTOR)

    }


    fun updateLoading(): Boolean {
        return manager.update()
    }

    fun finishLoading() {
        Gdx.app.log("Assets", "Finishing Loading...")
        manager.finishLoading()
        Gdx.app.log("Assets", "Initializing")
        initialize()
    }

    private fun initialize() {
        powerUpAssets = PowerUpAssets(manager.get(POWER_UP_ATLAS_DESCRIPTOR))
        attackerAssets = AttackerAssets(manager.get(ATTACKER_ATLAS_DESCRIPTOR))

    }

    fun dispose() {
        manager.dispose()
    }

    fun getManager(): AssetManager {
        return manager
    }

    class PowerUpAssets(atlas: TextureAtlas){
        private val map = mapOf(
                PowerUp.PowerUpType.HPBOOST to atlas.findRegion("hpboost") as TextureRegion,
                PowerUp.PowerUpType.SHIELD to atlas.findRegion("shield") as TextureRegion,
                PowerUp.PowerUpType.SHOCKWAVE to atlas.findRegion("shockwave") as TextureRegion
        )

        init {
            Gdx.app.log("Initializing", "PowerUpAssets...")
        }
        fun getTexture(type: PowerUp.PowerUpType): TextureRegion? = map[type]

        val width = map[PowerUp.PowerUpType.SHIELD]!!.regionWidth
        val height = map[PowerUp.PowerUpType.SHIELD]!!.regionHeight

    }

    class AttackerAssets(atlas: TextureAtlas){
        private val map = mapOf(
                MISSILE to atlas.findRegion("missile") as TextureRegion,
                NUCLEAR_BOMB to atlas.findRegion("nuclearbomb") as TextureRegion,
                SMALL_METEOR to atlas.findRegion("smallMeteor") as TextureRegion,
                MEDIUM_METEOR to atlas.findRegion("mediumMeteor") as TextureRegion,
                LARGE_METEOR to atlas.findRegion("largeMeteor") as TextureRegion,
                SATELLITE to atlas.findRegion("satellite") as TextureRegion,
                POWERUP_CONTAINER to atlas.findRegion("powerUpContainer") as TextureRegion
        )
        init {
            Gdx.app.log("Initializing", "AttackerAssets...")
        }
        fun getTexture(type: AttackerType): TextureRegion? = map[type]
    }

    companion object {
        val attackerConfigMap = mapOf(
                MISSILE to loadAttacker("Missile/Missile.json"),
                NUCLEAR_BOMB to loadAttacker("NuclearBomb/NuclearBomb.json"),
                SMALL_METEOR to loadAttacker("Meteor/SmallMeteor.json"),
                MEDIUM_METEOR to loadAttacker("Meteor/MediumMeteor.json"),
                LARGE_METEOR to loadAttacker("Meteor/LargeMeteor.json"),
                SATELLITE to loadAttacker("Satellite/Satellite.json"),
                POWERUP_CONTAINER to loadAttacker("PowerUpContainer/PowerUpContainer.json")

        )

        val powerupConfigMap = mapOf(
                PowerUp.PowerUpType.HPBOOST to loadPowerUp("HPBoost/hpboost.json"),
                PowerUp.PowerUpType.SHIELD to loadPowerUp("Shield/shield.json"),
                PowerUp.PowerUpType.SHOCKWAVE to loadPowerUp("ShockWave/shockwave.json")
        )

        val bladeConfig = arrayOf(loadBlade("blades/first.json"), loadBlade("blades/second.json"))
        val spaceCraftConfig = loadSpaceCraft("spacecraft/spacecraft.json")

        private fun load(path: String) = Gdx.files.internal(path).reader()

        private fun loadAttacker(name: String): Reader {
            Gdx.app.log("AttackerConfigMap", "Loading...$name")
            return load("entities/attackers/$name")
        }

        private fun loadPowerUp(name: String): Reader {
            Gdx.app.log("PowerUpConfigMap", "Loading...$name")
            return load("entities/powerups/$name")
        }

        private fun loadSpaceCraft(name: String): Reader {
            Gdx.app.log("SpaceCraftConfig", "Loading...$name")
            return load("entities/$name")
        }

        private fun loadBlade(name: String): Reader{
            Gdx.app.log("Bladesconfig", "Loading..$name")
            return load("entities/$name")
        }

        init {
            Gdx.app.log("Loading", "Config files...")
        }
    }

    object SpaceCraftAssets {
        private val path = "entities/spacecraft/spacecraft.png"
        val spaceCraftTexture = TextureRegion(Texture(path))

        init {
            Gdx.app.log("Initializing", "SpaceCraftAssets...")
        }

        fun dispose() {
            Gdx.app.log("SpaceCraftAssets", "disposing")
            spaceCraftTexture.texture.dispose()
        }
    }



    }
