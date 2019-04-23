package com.coden.starslicer

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.screens.GameScreen
import com.coden.starslicer.util.AssetProvider
import com.coden.starslicer.util.Assets
import com.coden.starslicer.util.Log
import com.coden.starslicer.util.dist2
import com.coden.util.swipe.SwipeRenderer


class StarSlicerGame : Game() {
    lateinit var swipeRenderer: SwipeRenderer
    lateinit var assets: Assets
    lateinit var assetProvider: AssetProvider

    override fun create() {

        Log.info("created", Log.LogType.GAME)

        assets = Assets()
        assets.load()

        assetProvider = AssetProvider()
        assetProvider.load()

        swipeRenderer = SwipeRenderer(10, 10, 2,
                                    0.25f, 20f,
                                    10, assets.swipeTexture)
        swipeRenderer.create()

        //handle swipe input
        Gdx.input.inputProcessor = swipeRenderer.swipe
        setScreen(GameScreen(this))

    }

    override fun resume() {
        super.resume()
        Texture.setAssetManager(assets.getManager())
        Texture.setAssetManager(assetProvider.manager)
    }


    override fun render() {
        super.render()
        if (!assets.updateLoading()) {
            Log.info("assets loading, ${assets.progress}", Log.LogType.SCREENS)
        }

        if (!assetProvider.update()){
            Log.info("assets 2 loading, ${assets.progress}", Log.LogType.SCREENS)
        }
    }

    override fun dispose() {
        Log.info("disposing", Log.LogType.GAME)
        assets.dispose()
        assetProvider.dispose()
        swipeRenderer.dispose()
        //screen.dispose()
    }
}
