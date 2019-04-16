package com.coden.starslicer.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.JsonValue
import com.coden.starslicer.Commands.*
import com.coden.starslicer.entities.attackers.AttackerType
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.gameplay.Spawner
import com.coden.starslicer.util.Assets.Configs.spawnerConfigList
import com.google.gson.JsonIOException
import java.lang.NullPointerException


class JSONLoader {

    fun loadAllSpanwers(): ArrayList<Spawner>{
        val spawners = ArrayList<Spawner>()
        for (config in spawnerConfigList){
            spawners += loadSpawners(config)
        }
        return spawners
    }
    fun loadSpawners(config: JsonValue): ArrayList<Spawner> {

        val spawners = config.get("spawners")
        val result = arrayListOf<Spawner>()

        for (spawner in spawners.asIterable()){

            val numberGrowth  = parseGrowthResolver(spawner,"number", spawners)
            val periodGrowth  = parseGrowthResolver(spawner,"period", spawners)
            val delayGrowth  = parseGrowthResolver(spawner,"delay", spawners)
            val type = spawner.getString("type")
            val state = spawner.getInt("state")
            val startWave = spawner.getInt("startWave")

            val spawnCommand = when(AttackerType.valueOf(type)){

                AttackerType.SMALL_METEOR -> SpawnMeteor(state, 0)
                AttackerType.MEDIUM_METEOR -> SpawnMeteor(state, 1)
                AttackerType.LARGE_METEOR -> SpawnMeteor(state, 2)
                AttackerType.MISSILE -> SpawnMissile(state)
                AttackerType.NUCLEAR_BOMB -> SpawnNuclearBomb(state)
                AttackerType.SATELLITE -> {
                    try {
                        SpawnSatellite(state, PowerUp.PowerUpType.valueOf(spawner.getString("content")))
                    }catch (e: Exception){
                        Log.info("FOR SAT NO SUCH POWERUP: ${spawner.getString("content")}", Log.LogType.SPAWN)
                        throw e
                    }
                }
                AttackerType.POWERUP_CONTAINER -> {
                    try {
                        SpawnContainer(state, PowerUp.PowerUpType.valueOf(spawner.getString("content")))
                    }catch (e: Exception){
                        Log.info("FOR PUC NO SUCH POWERUP: ${spawner.getString("content")}", Log.LogType.SPAWN)
                        throw e
                    }

                }

            }

            result.add(Spawner(numberGrowth, periodGrowth, delayGrowth, startWave, spawnCommand,
                    try {
                        parseGrowthResolver(spawner, "lifeSpan", spawners)
                    }catch (e: Exception){
                        GrowthResolver(25.0f, 2.75f, GrowthResolver.GrowthType.POLYNOMIAL)
                    }
            ))

        }
        return result
    }

    fun parseGrowthResolver(spawner: JsonValue, name: String, spawners: JsonValue): GrowthResolver{
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
