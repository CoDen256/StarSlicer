package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.coden.starslicer.Commands.*
import com.coden.starslicer.entities.entityInterfaces.Mortal
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.states.WaveBeginState
import com.coden.starslicer.states.WaveState
import com.coden.starslicer.util.GrowthResolver as GR
import com.coden.starslicer.util.GrowthResolver.GrowthType.*
import com.coden.starslicer.util.JSONLoader
import com.coden.starslicer.util.Log

class Wave(var number: Int, val queue: CommandQueue) {

    val spawnerLoader = JSONLoader("entities/attackers/Spawners")
    val spawners  = with(spawnerLoader){
        load("containerSpawners") +
        load("meteorSpawners") +
        load("missileSpawners") +
        load("nuclearbombSpawners") +
        load("satelliteSpawners")
    }

    var currentState: WaveState = WaveBeginState(this)
    var life = 0f

    fun update() {
        life += Gdx.graphics.deltaTime

        currentState = currentState.update() ?: currentState

    }

    fun render(batch: SpriteBatch, font: BitmapFont){
        currentState.render(batch, font)
    }

    fun evolve(){
        number ++
        life = 0f
        for (spawner in spawners){
            spawner.evolve()
        }
    }
}