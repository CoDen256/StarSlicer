package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.coden.starslicer.Commands.CommandQueue
import com.coden.starslicer.Commands.SpawnMeteor
import com.coden.starslicer.Commands.SpawnMissile
import com.coden.starslicer.entities.entityInterfaces.Mortal
import com.coden.starslicer.util.GrowthResolver as GR
import com.coden.starslicer.util.GrowthResolver.GrowthType.*
import com.coden.starslicer.util.Log

class Wave(var number: Int) {

    val missile0Spawner = Spawner(
            GR(10, 2, POLYNOMIAL),
            GR(2, 1, POLYNOMIAL),
            GR(5f, -1f, POLYNOMIAL),
            number, SpawnMissile(0))

    val missile1Spawner = Spawner(
            GR(5, 1, POLYNOMIAL),
            GR(1, 1, POLYNOMIAL),
            GR(10f, -1f, POLYNOMIAL),
            number, SpawnMissile(1)

    )

    val spawners = arrayListOf(missile0Spawner, missile1Spawner)


    var life = 0f


    fun update(queue: CommandQueue): Boolean {

        life += Gdx.graphics.deltaTime

        val iter = spawners.iterator()
        while (iter.hasNext()){
            val spawner = iter.next()
            spawner.update(queue)
            if (spawner.isDead){
                Log.info("Spawner $spawner is dead", Log.LogType.SPAWN)
                iter.remove()
            }
        }

        if (spawners.isEmpty()){
            Log.info("all spawners are dead", Log.LogType.SPAWN)
            return false
        }

        return true
    }
}