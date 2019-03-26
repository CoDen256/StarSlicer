package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.coden.starslicer.Commands.Command
import com.coden.starslicer.Commands.CommandQueue
import com.coden.starslicer.entities.attackers.Attacker
import com.coden.starslicer.entities.attackers.Missile
import com.coden.starslicer.entities.entityInterfaces.Mortal
import com.coden.starslicer.util.GrowthResolver
import com.coden.starslicer.util.Log

data class Spawner(private val maxNumberGrowth:GrowthResolver,
                   private val numberGrowth:GrowthResolver,
                   private val periodGrowth:GrowthResolver,
                   private val waveNum: Int, val spawnCommand: Command): Mortal {



    private val maxNumber = maxNumberGrowth.resolve(waveNum).toInt()
    private val number = numberGrowth.resolve(waveNum).toInt()
    private val period = periodGrowth.resolve(waveNum)

    private val timelimit get() = period*(maxNumber/number)
    private var timePassed = period

    private var spawned = 0
    override var isDead = false


    init {
        Log.info("Setting up the following Spawner for ${spawnCommand.javaClass} with wave $waveNum for $timelimit seconds", Log.LogType.SPAWN)
        Log.info("Max: ${maxNumberGrowth.init} -> $maxNumber, NPP: ${numberGrowth.init} -> $number, Period: ${periodGrowth.init} -> $period", Log.LogType.SPAWN)
    }

    override fun toString() = "$spawnCommand"

    fun update(queue: CommandQueue){
        if (spawned >= maxNumber) kill()

        timePassed += Gdx.graphics.deltaTime

        if (timePassed >= period){
            timePassed = 0f
            spawned += number
            queue.add(spawnCommand, number)
        }

    }

}