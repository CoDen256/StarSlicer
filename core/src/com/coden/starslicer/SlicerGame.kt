package com.mygdx.slicer

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.slicer.screens.GameScreen
import com.mygdx.slicer.screens.MainMenuScreen

import com.mygdx.util.swipe.SwipeRenderer


class SlicerGame : Game() {
    lateinit var swipeRenderer: SwipeRenderer


    override fun create() {

        swipeRenderer = SwipeRenderer(10, 10, 2,
                                    0.25f, 20f,
                                    10, "gradient2.png")
        swipeRenderer.create()

        //handle swipe input
        Gdx.input.inputProcessor = swipeRenderer.swipe
        setScreen(GameScreen(this))

    }


    override fun render() {
        super.render()
    }

    override fun dispose() {
        swipeRenderer.dispose()
    }
}
