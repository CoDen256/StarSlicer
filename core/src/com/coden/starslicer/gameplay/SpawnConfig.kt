package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.coden.starslicer.Commands.Command
import com.coden.starslicer.entities.attackers.Attacker
import com.coden.starslicer.entities.attackers.Missile
import com.coden.starslicer.util.Log

data class SpawnConfig(private val initMax: Int, private var initNumPerPeriod: Float, private var initPeriod: Float,
                       private val maxGrowth : Float, private val numPerPeriodGrowth: Float, private val periodGrowth: Float,
                       private val waveNum: Int, val spawnCommand: Command, val growthType: Int = 0){ // 0 - Absolute, 1-Exponential

    private val expGrowth = {init: Float, growth: Float -> init*(Math.pow(growth.toDouble(), waveNum.toDouble())).toFloat()}
    private val absGrowth = {init: Float, growth: Float -> init + growth*waveNum}

    var current = 0

    val max get() = if (growthType == 0) computeWaveParAbs(initMax.toFloat(), maxGrowth, waveNum)  else computeWaveParExp(initMax.toFloat(), maxGrowth, waveNum)
    val period get() = if (growthType == 0) computeWaveParAbs(initPeriod, periodGrowth, waveNum) else computeWaveParExp(initPeriod, periodGrowth, waveNum)
    val numPerPeriod get() = if (growthType == 0) computeWaveParAbs(initNumPerPeriod, numPerPeriodGrowth, waveNum).toInt() else computeWaveParExp(initNumPerPeriod, numPerPeriodGrowth, waveNum).toInt()

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