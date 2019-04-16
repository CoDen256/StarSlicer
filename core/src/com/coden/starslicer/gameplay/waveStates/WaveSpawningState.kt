package com.coden.starslicer.gameplay.waveStates

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.coden.starslicer.gameplay.Wave
import com.coden.starslicer.util.Log

class WaveSpawningState(val wave: Wave): WaveState {

    init {
        Log.info("Wave SPAWNINGSTATE created", Log.LogType.SPAWN)
    }

    override fun execute(): WaveState? {
        if (wave.spawners.all{it.isDead} ){
            Log.info("All spawners are dead at ${wave.life}", Log.LogType.SPAWN)
            return WaveWaitingState(wave)
        }
        return null
    }

    override fun update(): WaveState? {
        for (spawner in wave.spawners){
            spawner.update(wave.queue)
        }
        return execute()
    }

    override fun render(batch: SpriteBatch, font: BitmapFont) {
        font.draw(batch, "Spawning attackers", 50f, Gdx.graphics.height - 100f)
    }

}