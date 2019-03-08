package com.coden.starslicer.hud

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

class HUD {

    private var batch: SpriteBatch = SpriteBatch()
    private var shapeRenderer: ShapeRenderer = ShapeRenderer()

    var stage = StageHandler()

    var button = Button("shield", Vector2(50f, 50f), stage)

    init {
        var click = ClickListener()
        button.imageButton.addListener {
            Gdx.app.log("He", "listens")
            true
        }
    }


    fun update() {

    }

    fun render() {
        batch.begin()

        batch.end()

        stage.render()

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)

        shapeRenderer.end()
    }

    fun dispose() {

    }
}


