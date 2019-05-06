package com.coden.starslicer.gameplay.wave

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.coden.starslicer.gameObjects.attackers.Attacker.Companion.attackers
import com.coden.starslicer.util.Log

class WaveWaitingState(val wave: Wave): WaveState {

    init {
        Log.info("Wave WAITINGSTATE created", Log.LogType.SPAWN)
    }

    override fun execute(): WaveState? {
        if (attackers.size <= 3){
            return WaveDelayNextState(wave)
        }
        return null
    }

    override fun update(): WaveState? {
        return execute()
    }

    override fun render(batch: SpriteBatch, font: BitmapFont) {
        font.draw(batch, "Waiting until all attackers are dead", 50f, Gdx.graphics.height - 100f)
    }

}