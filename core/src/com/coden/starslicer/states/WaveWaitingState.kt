package com.coden.starslicer.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.coden.starslicer.Commands.CommandQueue
import com.coden.starslicer.entities.attackers.Attacker.Companion.attackers
import com.coden.starslicer.gameplay.Spawner
import com.coden.starslicer.gameplay.Wave
import com.coden.starslicer.util.Log

class WaveWaitingState(val wave: Wave): WaveState {

    init {
        Log.info("Wave WAITINGSTATE created", Log.LogType.SPAWN)
    }

    override fun execute(): WaveState? {
        if (attackers.isEmpty()){
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