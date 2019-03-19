package com.coden.starslicer

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.screens.GameScreen
import com.coden.starslicer.util.Assets
import com.coden.starslicer.util.dist2
import com.coden.util.swipe.SwipeRenderer


class StarSlicerGame : Game() {
    lateinit var swipeRenderer: SwipeRenderer
    lateinit var assets: Assets

    override fun create() {

        Gdx.app.log("StarSlicerGame", "Created")

        assets = Assets()
        assets.load()

        swipeRenderer = SwipeRenderer(10, 10, 2,
                                    0.25f, 20f,
                                    10, "gradient2.png")
        swipeRenderer.create()

        //handle swipe input
        Gdx.input.inputProcessor = swipeRenderer.swipe
        setScreen(GameScreen(this))

    }

    override fun resume() {
        super.resume()
        Texture.setAssetManager(assets.getManager())
    }


    override fun render() {
        super.render()
        if (!assets.updateLoading()) {
            Gdx.app.log("splash screen", "assets loading, ${assets.progress}")
        }
    }

    override fun dispose() {
        Gdx.app.log("SlicerGame", "disposing...")
        assets.dispose()
        swipeRenderer.dispose()
        //screen.dispose()
    }
}
