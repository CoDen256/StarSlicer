package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.coden.starslicer.Commands.*
import com.coden.starslicer.entities.entityInterfaces.Mortal
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.GrowthResolver as GR
import com.coden.starslicer.util.GrowthResolver.GrowthType.*
import com.coden.starslicer.util.JSONLoader
import com.coden.starslicer.util.Log

class Wave(var number: Int): Mortal {

    val spawnerLoader = JSONLoader("entities/attackers/Spawners")
    val spawners  = with(spawnerLoader){
        load("containerSpawners") +
        load("meteorSpawners") +
        load("missileSpawners") +
        load("nuclearbombSpawners") +
        load("satelliteSpawners")
    }

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