package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.coden.starslicer.Commands.CommandQueue
import com.coden.starslicer.Commands.SpawnContainer
import com.coden.starslicer.Commands.SpawnMeteor
import com.coden.starslicer.Commands.SpawnMissile
import com.coden.starslicer.entities.entityInterfaces.Mortal
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.GrowthResolver as GR
import com.coden.starslicer.util.GrowthResolver.GrowthType.*
import com.coden.starslicer.util.Log

class Wave(var number: Int): Mortal {

    val missile0Spawner = Spawner(
            maxNumberGrowth = GR(150f, 1.5f, EXPONENTIAL),
            numberGrowth = GR(4, 1, POLYNOMIAL),
            periodGrowth = GR(2f, 0.95f, EXPONENTIAL),
            delayGrowth = GR(2.5f, 0f, POLYNOMIAL),
            waveNum = number,
            spawnCommand = SpawnMissile(0))

    val missile1Spawner = Spawner(
            maxNumberGrowth = GR(15, 1, POLYNOMIAL),
            numberGrowth = GR(1, 1, POLYNOMIAL),
            periodGrowth = GR(5f, 0.95f, EXPONENTIAL),
            delayGrowth = GR(0, 0, POLYNOMIAL),
            waveNum = number,
            spawnCommand = SpawnMissile(1)

    )

    val missile2Spawner = Spawner(
            maxNumberGrowth = GR(30, 1, POLYNOMIAL),
            numberGrowth = GR(3, 1, POLYNOMIAL),
            periodGrowth = GR(3f, 0.95f, EXPONENTIAL),
            delayGrowth = GR(1, 0, POLYNOMIAL),
            waveNum = number,
            spawnCommand = SpawnMissile(2)

    )

    val missile3Spawner = Spawner(
            maxNumberGrowth = GR(20, 1, POLYNOMIAL),
            numberGrowth = GR(3, 1, POLYNOMIAL),
            periodGrowth = GR(4.5f, 0.95f, EXPONENTIAL),
            delayGrowth = GR(2.5f, 0f, POLYNOMIAL),
            waveNum = number,
            spawnCommand = SpawnMissile(3)

    )

    val containerSpawner1 = Spawner(
            maxNumberGrowth = GR(6, 1, POLYNOMIAL),
            numberGrowth = GR(1f, 1.5f, EXPONENTIAL),
            periodGrowth = GR(15f, 0.97f, EXPONENTIAL),
            delayGrowth = GR(0, 0, POLYNOMIAL),
            waveNum = number,
            spawnCommand = SpawnContainer(0, PowerUp.PowerUpType.SHIELD)
    )

    val containerSpawner2 = containerSpawner1.copy(
            delayGrowth = GR(7.5f, 0f, POLYNOMIAL),
            spawnCommand = SpawnContainer(0, PowerUp.PowerUpType.SHOCKWAVE))


    val spawners = arrayListOf(missile0Spawner, missile1Spawner, containerSpawner1, containerSpawner2,
            missile2Spawner, missile3Spawner)


    var life = 0f
    override var isDead = false

    fun update(queue: CommandQueue): Boolean {
        life += Gdx.graphics.deltaTime

        for (spawner in spawners){
            spawner.update(queue)

        }

        if (spawners.all{it.isDead}){
            if (!isDead){
                Log.info("all spawners are dead at $life", Log.LogType.SPAWN)
            }
            return false
        }

        return true
    }

    fun evolve(){
        number ++
        life = 0f
        isDead = false
        for (spawner in spawners){
            spawner.evolve()
        }
    }
}