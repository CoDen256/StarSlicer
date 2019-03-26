package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.coden.starslicer.Commands.Command
import com.coden.starslicer.entities.attackers.Attacker
import com.coden.starslicer.entities.attackers.Missile
import com.coden.starslicer.util.Log

data class SpawnConfig(private val initMax: Int, private var initNumPerPeriod: Float, private var initPeriod: Float,
                       private val maxGrowth : Float, private val numPerPeriodGrowth: Float, private val periodGrowth: Float,
                       private val waveNum: Int, val spawnCommand: Command, val growthType: Int = 0){ // 0 - Absolute, 1-Exponential

    private val computeWaveParExp = {init: Float, growth: Float, num: Int -> init*(Math.pow(growth.toDouble(), num.toDouble())).toFloat()}
    private val computeWaveParAbs = {init: Float, growth: Float, num: Int -> init + growth*waveNum.toFloat()}

    var current = 0

    val max get() = computeWavePar(initMax.toFloat(), maxGrowth, waveNum)
    val period get() = computeWavePar(initPeriod, periodGrowth, waveNum)
    val numPerPeriod get() = computeWavePar(initNumPerPeriod, numPerPeriodGrowth, waveNum).toInt()

    val timelimit get() = period*(max/numPerPeriod)

    private var timePassed = period

    init {
        Log.debug("Setting up the following Spawner for ${spawnCommand.javaClass} with wave $waveNum")
        Log.debug("Max: $initMax -> $max, NPP: $initNumPerPeriod -> $numPerPeriod, Period: $initPeriod -> $period")
    }

    fun update(): Boolean{
        timePassed += Gdx.graphics.deltaTime

        if (current >= max) return false

        if (timePassed >= period){
            timePassed = 0f
            current += numPerPeriod
            return true
        }

        return false
    }

}