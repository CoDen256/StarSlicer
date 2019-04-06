package com.coden.starslicer.states.wave

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

interface WaveState {
    fun execute(): WaveState?
    fun update(): WaveState?
    fun render(batch: SpriteBatch, font: BitmapFont)
}