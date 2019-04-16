package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.coden.starslicer.Commands.*
import com.coden.starslicer.events.Subject
import com.coden.starslicer.gameplay.waveStates.WaveBeginState
import com.coden.starslicer.gameplay.waveStates.WaveState
import com.coden.starslicer.util.GrowthResolver as GR
import com.coden.starslicer.util.JSONLoader

class Wave(var number: Int, val queue: CommandQueue) {

    val spawners = JSONLoader().loadAllSpanwers()

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