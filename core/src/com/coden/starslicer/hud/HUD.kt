package com.coden.starslicer.hud

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Logger
import com.coden.starslicer.entities.EntityData


class HUD(data: EntityData) {

    private val powerUpsBar = PowerUpsBar(Gdx.graphics.width*0.5f, Gdx.graphics.height*0.88f, data) // TODO: Move HUD to the top,
                                                                                                    // since its uncomfortable to slice ther

    private var batch: SpriteBatch = SpriteBatch()
    private var shapeRenderer: ShapeRenderer = ShapeRenderer()

    private val log = Logger("HUD", Logger.INFO)



    fun update() {
        powerUpsBar.update()

    }

    fun render() {
        batch.begin()

        powerUpsBar.render(batch)

        batch.end()


        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)

        powerUpsBar.render(shapeRenderer)

        debug(shapeRenderer)

        shapeRenderer.end()
    }

    fun debug(shapeRenderer: ShapeRenderer) {
        powerUpsBar.debug(shapeRenderer)
    }

}


