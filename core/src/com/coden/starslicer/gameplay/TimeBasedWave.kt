package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.coden.starslicer.Commands.CommandQueue
import com.coden.starslicer.Commands.SpawnContainer
import com.coden.starslicer.entities.entityInterfaces.Mortal
import com.coden.starslicer.util.Log

class TimeBasedWave(val number: Int, val lifeSpan: Float, val spawnPeriod: Float, val maxContainers: Int): Mortal {

    constructor(number: Int, parent: TimeBasedWave): this(number, parent.lifeSpan, parent.spawnPeriod, parent.maxContainers)

    var life = 0f
    var spawnTimer = 0f

    val entitiesPerSpawn = {max: Int -> Math.round(max/(lifeSpan/spawnPeriod))}

    override var isDead = false

    fun update(queue: CommandQueue): Boolean {
        life += Gdx.graphics.deltaTime
        spawnTimer += Gdx.graphics.deltaTime

        if (life > lifeSpan) {
            if (!isDead) {
                Log.info("Wave ${number} is dead")
            }
            kill()
            return false
        }

        if (spawnTimer > spawnPeriod){
            Log.info("Adding ${entitiesPerSpawn(maxContainers)} Containers after $spawnTimer")
            spawnTimer = 0f
            for (i in 0 until entitiesPerSpawn(maxContainers)){
                queue.add(SpawnContainer())
            }

        }

        return true
    }


}