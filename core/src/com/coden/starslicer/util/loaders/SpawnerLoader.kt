package com.coden.starslicer.util.loaders

import com.badlogic.gdx.utils.JsonValue
import com.coden.starslicer.Commands.spawnCommands.*
import com.coden.starslicer.entities.attackers.AttackerType
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.gameplay.GrowthResolver
import com.coden.starslicer.gameplay.Spawner
import com.coden.starslicer.util.Locator
import com.coden.starslicer.util.assets.AssetLocator


class SpawnerLoader : Loader<Spawner>{
    override val configMap= AssetLocator.getConfigs().spawnerConfigList

    fun load(): ArrayList<Spawner>{
        return loadAllConfigs(configMap)
    }

    fun loadAllConfigs(configList: ArrayList<JsonValue>): ArrayList<Spawner> {
        val spawners = ArrayList<Spawner>()
        for (config in configList){
            spawners += loadSpawners(config)
        }
        return spawners
    }

    fun loadSpawners(config: JsonValue): ArrayList<Spawner> {
        val spawners = config.get("spawners")
        val result = arrayListOf<Spawner>()

        for (spawner in spawners.asIterable()){
            result.add(loadSingleSpawner(spawner, spawners))
        }
        return result
    }

    fun loadSingleSpawner(spawner: JsonValue, spawners: JsonValue): Spawner{
        val numberGrowth  = parseGrowthResolver(spawner,"number", spawners)
        val periodGrowth  = parseGrowthResolver(spawner,"period", spawners)
        val delayGrowth  = parseGrowthResolver(spawner,"delay", spawners)
        val type = spawner.getString("type")
        val state = spawner.getInt("state")
        val startWave = spawner.getInt("startWave")


        val content = try {
            PowerUp.PowerUpType.valueOf(spawner.getString("content"))
        }catch (e: Exception){
            null
        }

        val id = when(AttackerType.valueOf(type)){
            AttackerType.SMALL_METEOR -> "met${state}0"
            AttackerType.MEDIUM_METEOR -> "met${state}1"
            AttackerType.LARGE_METEOR -> "met${state}2"
            AttackerType.MISSILE -> "mis$state"
            AttackerType.NUCLEAR_BOMB -> "nuc$state"
            AttackerType.SATELLITE -> "sat$state" + PowerUp.convert(content!!)
            AttackerType.POWERUP_CONTAINER -> "puc$state" + PowerUp.convert(content!!)
        }

        var spawnCommand = Locator.getSpawnCommand(id)

        if (spawnCommand is NullSpawnCommand){
            spawnCommand = SpawnCommand.convert(id)
            Locator.provide(id, spawnCommand)
        }

        assert(spawnCommand !is NullSpawnCommand)

        return Spawner(numberGrowth, periodGrowth, delayGrowth, startWave, spawnCommand,
                try {
                    parseGrowthResolver(spawner, "lifeSpan", spawners)
                } catch (e: Exception) {
                    GrowthResolver(25.0f, 2.75f, GrowthResolver.GrowthType.POLYNOMIAL)
                })
    }

    fun parseGrowthResolver(spawner: JsonValue, name: String, spawners: JsonValue): GrowthResolver {
        val field = try{
            spawners.get(spawner.get(name).asString()).get(name)
        }catch (e: IllegalStateException){
            spawner.get(name)
        }
        with(field){
            return GrowthResolver(get("init").asFloat(), get("rate").asFloat(), GrowthResolver.GrowthType.valueOf(get("type").asString()))
        }
    }
}
