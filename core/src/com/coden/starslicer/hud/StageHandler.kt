package com.coden.starslicer.hud

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage

class StageHandler {
    val stage: Stage = Stage()

    init {
    }

    fun render() {
        stage.act()
        stage.draw()
    }

    fun add(actor: Actor) {
        stage.addActor(actor)
    }

}