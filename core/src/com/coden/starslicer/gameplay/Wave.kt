package com.coden.starslicer.gameplay

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.coden.starslicer.Commands.*
import com.coden.starslicer.gameplay.waveStates.WaveBeginState
import com.coden.starslicer.gameplay.waveStates.WaveState
import com.coden.starslicer.util.Assets
import com.coden.starslicer.util.loaders.SpawnerLoader
import com.coden.starslicer.gameplay.GrowthResolver as GR

class Wave(var number: Int, val queue: CommandQueue) {

    val spawners = SpawnerLoader().loadAllConfigs(Assets.spawnerConfigList)

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