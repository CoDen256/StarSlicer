package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.coden.starslicer.Commands.CommandQueue
import com.coden.starslicer.Commands.SpawnContainer
import com.coden.starslicer.Commands.SpawnMeteor
import com.coden.starslicer.Commands.SpawnMissile
import com.coden.starslicer.entities.attackers.Missile
import com.coden.starslicer.entities.entityInterfaces.Mortal
import com.coden.starslicer.util.Log

class Wave(var number: Int): Mortal {

    val missileMissingSpawner = SpawnConfig(10, 2f, 3f, 2f, 2f,2f, number, SpawnMissile(0))
    var life = 0f

    override var isDead = false

    fun update(queue: CommandQueue): Boolean {

        life += Gdx.graphics.deltaTime
        //Log.info("$life/${missileMissingSpawner.timelimit}")

        if (missileMissingSpawner.update()){
            for (i in 0 until missileMissingSpawner.numPerPeriod){
                queue.add(missileMissingSpawner.spawnCommand)
            }

        }

        return true
    }
}