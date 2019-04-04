package com.coden.starslicer.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.JsonValue
import com.coden.starslicer.Commands.*
import com.coden.starslicer.entities.attackers.AttackerType
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.gameplay.Spawner
import com.google.gson.JsonIOException
import java.lang.NullPointerException


class JSONLoader(val path: String) {
    fun load(filename: String): ArrayList<Spawner> {
        val reader = JsonReader().parse(Gdx.files.internal("$path/$filename.json"))

        val spawners = reader.get("spawners")
        val result = arrayListOf<Spawner>()

        for (spawner in spawners.asIterable()){

            val lifeSpanGrowth = parseGrowthResolver(spawner,"lifeSpan", spawners)
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
                AttackerType.SATELLITE -> SpawnSatellite(state, PowerUp.PowerUpType.valueOf(spawner.getString("content")))
                AttackerType.POWERUP_CONTAINER -> SpawnContainer(state, PowerUp.PowerUpType.valueOf(spawner.getString("content")))

            }

            result.add(Spawner(lifeSpanGrowth, numberGrowth, periodGrowth, delayGrowth, startWave, spawnCommand))

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
