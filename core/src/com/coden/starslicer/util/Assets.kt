package com.coden.starslicer.util

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.coden.starslicer.entities.attackers.AttackerType
import com.coden.starslicer.entities.attackers.AttackerType.*
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.hud.PowerUpIcon
import com.coden.starslicer.util.loaders.Loader

class Assets{
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
        val spaceCraftConfig = loadConfig("spacecraft/spacecraft.json")

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
                PowerUp.PowerUpType.HPBOOST to loadConfig("powerups/hpboost.json"),
                PowerUp.PowerUpType.SHIELD to loadConfig("powerups/shield.json"),
                PowerUp.PowerUpType.SHOCKWAVE to loadConfig("powerups/shockwave.json"))

        val spawnerConfigList = arrayListOf(
                loadSpawnerConfig("containerSpawners.json"),
                loadSpawnerConfig("meteorSpawners.json"),
                loadSpawnerConfig("missileSpawners.json"),
                loadSpawnerConfig("nuclearbombSpawners.json"),
                loadSpawnerConfig("satelliteSpawners.json")
        )


        private fun loadConfig(path: String) = Loader.loadJson("entities/config/$path")
        private fun loadSpawnerConfig(path: String) = Loader.loadJson("entities/config/spawners/$path")

        init { Log.info("Config files created", Log.LogType.ASSETS) }
    }
}

class PowerUpAssets(atlas: TextureAtlas){
    private val map = mapOf(
            PowerUp.PowerUpType.HPBOOST to atlas.findRegion("hpboost") as TextureRegion,
            PowerUp.PowerUpType.SHIELD to atlas.findRegion("shield") as TextureRegion,
            PowerUp.PowerUpType.SHOCKWAVE to atlas.findRegion("shockwave") as TextureRegion
    )

    val icons = arrayListOf(
            PowerUpIcon(PowerUp.PowerUpType.SHIELD, getTexture(PowerUp.PowerUpType.SHIELD)),
            PowerUpIcon(PowerUp.PowerUpType.HPBOOST, getTexture(PowerUp.PowerUpType.HPBOOST)),
            PowerUpIcon(PowerUp.PowerUpType.SHOCKWAVE, getTexture(PowerUp.PowerUpType.SHOCKWAVE))
    )

    init { Log.info("PowerUpAssets textureMap created", Log.LogType.ASSETS) }

    fun getTexture(type: PowerUp.PowerUpType): TextureRegion? = map[type]

    val width = map[PowerUp.PowerUpType.SHIELD]!!.regionWidth
    val height = map[PowerUp.PowerUpType.SHIELD]!!.regionHeight

}
class AttackerAssets(atlas: TextureAtlas){
    private val textureMap = mapOf(
            AttackerType.MISSILE to atlas.findRegion("missile") as TextureRegion,
            AttackerType.NUCLEAR_BOMB to atlas.findRegion("nuclearbomb") as TextureRegion,
            AttackerType.SMALL_METEOR to atlas.findRegion("smallMeteor") as TextureRegion,
            AttackerType.MEDIUM_METEOR to atlas.findRegion("mediumMeteor") as TextureRegion,
            AttackerType.LARGE_METEOR to atlas.findRegion("largeMeteor") as TextureRegion,
            AttackerType.SATELLITE to atlas.findRegion("satellite") as TextureRegion,
            AttackerType.POWERUP_CONTAINER to atlas.findRegion("container") as TextureRegion
    )
    init { Log.info("AttackerAssets  textureMap created", Log.LogType.ASSETS) }

    fun getTexture(type: AttackerType): TextureRegion? = textureMap[type]
}

class SwipeAssets(val textureMap: List<Texture>){
    companion object { var max = 4 }

    init { Log.info("SwipeAssets textureMap created", Log.LogType.ASSETS)}


    fun getTexture(num: Int): Texture =  textureMap[textureMap.lastIndex - num]
}