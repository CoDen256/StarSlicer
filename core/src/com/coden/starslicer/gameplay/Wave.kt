package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.coden.starslicer.Commands.CommandQueue
import com.coden.starslicer.Commands.SpawnContainer
import com.coden.starslicer.entities.entityInterfaces.Mortal
import com.coden.starslicer.util.Log

class Wave(val number: Int, val spawnPeriod: Float, val maxContainers: Int, val rateOfSpawning: Float): Mortal {

    constructor(number: Int, parent: TimeBasedWave): this(number, parent.spawnPeriod, parent.maxContainers)

    var spawnTimer = 0f

    override var isDead = false

    fun update(queue: CommandQueue): Boolean {
        spawnTimer += Gdx.graphics.deltaTime

        if (spawnTimer > spawnPeriod){
            Log.info("Adding ${entitiesPerSpawn(maxContainers)} Containers after $spawnTimer")
            spawnTimer = 0f
            for (i in 0 until entitiesPerSpawn(maxContainers)){
                queue.add(SpawnContainer())
            }

        }

        return true
    }
