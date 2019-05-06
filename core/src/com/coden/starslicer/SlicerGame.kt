package com.coden.starslicer

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.Texture
import com.coden.starslicer.screens.GameScreen
import com.coden.starslicer.util.*
import com.coden.starslicer.assets.assetUtils.AssetProvider


class StarSlicerGame : Game() {
    lateinit var assetProvider: AssetProvider

    override fun create() {

        Log.info("created", Log.LogType.GAME)

        assetProvider = AssetProvider()
        assetProvider.load()

        setScreen(GameScreen(this))

    }

    override fun resume() {
        super.resume()
        Texture.setAssetManager(assetProvider.manager)
    }


    override fun render() {
        super.render()

        if (!assetProvider.update()){
            Log.info("assets 2 loading, ${assetProvider.progress}", Log.LogType.SCREENS)
        }
    }

    override fun dispose() {
        Log.info("disposing", Log.LogType.GAME)
        assetProvider.dispose()
        //screen.dispose()
    }
}
