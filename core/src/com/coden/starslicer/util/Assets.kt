package com.coden.starslicer.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.JsonReader
import com.coden.starslicer.entities.attackers.AttackerType
import com.coden.starslicer.entities.attackers.AttackerType.*
import com.coden.starslicer.entities.powerups.HPBoost
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.loaders.Loader
import java.io.*

class Assets{
    private val manager = AssetManager()

    private val POWER_UP_ATLAS_DESCRIPTOR = AssetDescriptor<TextureAtlas>("ui/icons/powerups/powerups.atlas", TextureAtlas::class.java)
    private val ATTACKER_ATLAS_DESCRIPTOR = AssetDescriptor<TextureAtlas>("entities/animation/attackers/attackers3.atlas", TextureAtlas::class.java)


    val swipeTexture = Texture("entities/animation/blades/gradient8.png")
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
        Log.info("Loading TextureAtlases...", Log.LogType.ASSETS)
        manager.setLoader(TextureAtlas::class.java, TextureAtlasLoader(InternalFileHandleResolver()))
        manager.load(POWER_UP_ATLAS_DESCRIPTOR)
        manager.load(ATTACKER_ATLAS_DESCRIPTOR)

    }


    fun updateLoading(): Boolean {
        return manager.update()
    }

    fun finishLoading() {
        Log.info("Finish Loading...", Log.LogType.ASSETS)
        manager.finishLoading()
        Log.info("Initializing", Log.LogType.ASSETS)
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
            Log.info("PowerUpAssets textureMap created", Log.LogType.ASSETS)
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
                POWERUP_CONTAINER to atlas.findRegion("container") as TextureRegion
        )
        init {
            Log.info("AttackerAssets  textureMap created", Log.LogType.ASSETS)
        }
        fun getTexture(type: AttackerType): TextureRegion? = map[type]
    }

    object SpaceCraftAssets {
        val path = "entities/animation/spacecraft/spacecraft3tex.png"
        val spaceCraftTexture = TextureRegion(Texture(path))

        init {
            Log.info("SpaceCraftAssets texture created", Log.LogType.ASSETS)
        }

        fun dispose() {
            Log.info("SpaceCraftAssets disposing", Log.LogType.ASSETS)
            spaceCraftTexture.texture.dispose()
        }
    }

    companion object Configs{
        val spaceCraftConfig = loadConfig2("spacecraft/spacecraft.json")

        val bladesConfig = arrayOf(
                loadConfig("blades/first.json"),
                loadConfig("blades/second.json"))

        val attackerConfigMap = mapOf(
                MISSILE to loadConfig("missile/missile.json"),
                NUCLEAR_BOMB to loadConfig("nuclearBomb/nuclearBomb.json"),
                SMALL_METEOR to loadConfig("meteors/smallMeteor.json"),
                MEDIUM_METEOR to loadConfig("meteors/mediumMeteor.json"),
                LARGE_METEOR to loadConfig("meteors/largeMeteor.json"),
                SATELLITE to loadConfig("satellite/satellite.json"),
                POWERUP_CONTAINER to loadConfig("container/powerUpContainer.json"))

        val powerupConfigMap = mapOf(
                PowerUp.PowerUpType.HPBOOST to loadConfig2("powerups/hpboost.json"),
                PowerUp.PowerUpType.SHIELD to loadConfig2("powerups/shield.json"),
                PowerUp.PowerUpType.SHOCKWAVE to loadConfig2("powerups/shockwave.json"))

        val spawnerConfigList = arrayListOf(
                loadSpawnerConfig("containerSpawners.json"),
                loadSpawnerConfig("meteorSpawners.json"),
                loadSpawnerConfig("missileSpawners.json"),
                loadSpawnerConfig("nuclearbombSpawners.json"),
                loadSpawnerConfig("satelliteSpawners.json")
        )


        private fun loadConfig2(path: String) = Gdx.files.internal("entities/config/$path").reader()
        private fun loadConfig(path: String) = Loader.loadJson("entities/config/$path")
        private fun loadSpawnerConfig(path: String) = Loader.loadJson("entities/config/spawners/$path")

        init {
            Log.info("Config files created", Log.LogType.ASSETS)
        }
    }
}
