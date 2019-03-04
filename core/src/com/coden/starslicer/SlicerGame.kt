package com.coden.starslicer

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.coden.starslicer.screens.GameScreen
import com.coden.util.swipe.SwipeRenderer


class StarSlicerGame : Game() {
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
