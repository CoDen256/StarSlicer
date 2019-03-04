package com.mygdx.slicer.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.slicer.SlicerGame

class MainMenuScreen(val game: SlicerGame) : Screen {

    lateinit var cam: OrthographicCamera
    lateinit var batch: SpriteBatch


    override fun show() {
        Gdx.app.log("MainMenuScreen", "The screen is created")

        cam = OrthographicCamera()

        cam.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

        batch = SpriteBatch()


    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        cam.update()
        batch.projectionMatrix = cam.combined

        update()

        game.swipeRenderer.render(cam)

        batch.begin()
        batch.draw(Texture("gradient.png"), Gdx.graphics.width/2 + 0f, Gdx.graphics.height/2 + 0f)
        batch.end()
    }

    fun update() {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            game.setScreen(GameScreen(game))
        }
    }

    override fun pause() {

    }

    override fun hide() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun dispose() {
        Gdx.app.log("MainMenuScreen", "The screen is disposed")
        batch.dispose()
    }

}