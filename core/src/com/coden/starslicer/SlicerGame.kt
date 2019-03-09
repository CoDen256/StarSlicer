package com.coden.starslicer

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.screens.GameScreen
import com.coden.starslicer.util.Assets
import com.coden.starslicer.util.dist2
import com.coden.util.swipe.SwipeRenderer


class StarSlicerGame : Game() {
    lateinit var swipeRenderer: SwipeRenderer
    lateinit var assets: Assets

    override fun create() {

        assets = Assets()

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
        if (assets.manager.update()) {
            Gdx.app.log("splash screen", "assets loading")
        }
    }

    override fun dispose() {
        swipeRenderer.dispose()
    }
}
