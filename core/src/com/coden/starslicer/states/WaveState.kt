package com.coden.starslicer.states

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.coden.starslicer.Commands.CommandQueue
import com.coden.starslicer.gameplay.Spawner
import com.coden.starslicer.gameplay.Wave

interface WaveState {
    fun execute(): WaveState?
    fun update(): WaveState?
    fun render(batch: SpriteBatch, font: BitmapFont)
}