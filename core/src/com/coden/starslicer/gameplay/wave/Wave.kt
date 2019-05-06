package com.coden.starslicer.gameplay.wave

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.coden.starslicer.gameplay.commands.*
import com.coden.starslicer.assets.loaders.SpawnerLoader
import com.coden.starslicer.gameplay.spawner.GrowthResolver as GR

class Wave(var number: Int, val queue: CommandQueue) {

    val spawners = SpawnerLoader().load()

    init {
        for (spawner in spawners) spawner.evolveTo(number)
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
            spawner.evolveTo(number)
        }
    }
}