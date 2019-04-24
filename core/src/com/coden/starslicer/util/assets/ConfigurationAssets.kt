package com.coden.starslicer.util.assets

import com.coden.starslicer.entities.attackers.AttackerType
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.Log
import com.coden.starslicer.util.loaders.Loader

class ConfigurationAssets {

    val spaceCraftConfig = loadConfig("spacecraft/spacecraft.json")

    val bladesConfig = arrayOf(
            loadConfig("blades/first.json"),
            loadConfig("blades/second.json"))

    val attackerConfigMap = mapOf(
            AttackerType.MISSILE to loadConfig("missile/missile.json"),
            AttackerType.NUCLEAR_BOMB to loadConfig("nuclearBomb/nuclearBomb.json"),
            AttackerType.SMALL_METEOR to loadConfig("meteors/smallMeteor.json"),
            AttackerType.MEDIUM_METEOR to loadConfig("meteors/mediumMeteor.json"),
            AttackerType.LARGE_METEOR to loadConfig("meteors/largeMeteor.json"),
            AttackerType.SATELLITE to loadConfig("satellite/satellite.json"),
            AttackerType.POWERUP_CONTAINER to loadConfig("container/powerUpContainer.json"))

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

        init {
            Log.info("Config files created", Log.LogType.ASSETS)
        }
}