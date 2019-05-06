package com.coden.starslicer.gameplay.wave

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.coden.starslicer.util.Log

class WaveBeginState(val wave: Wave, val time: Float = 5f): WaveState {
    var counter = 0f

    init {
        Log.info("Wave BEGINSTATE created with duration $time ", Log.LogType.SPAWN)
    }

    override fun execute(): WaveState? {
        if (counter >= time) {
            return WaveSpawningState(wave)
        }
        return null
    }

    override fun update(): WaveState? {
        counter += Gdx.graphics.deltaTime
        return execute()
    }

    override fun render(batch: SpriteBatch, font: BitmapFont) {
        font.draw(batch, "Begining in ${time-counter}", 50f, Gdx.graphics.height - 100f)
    }


}