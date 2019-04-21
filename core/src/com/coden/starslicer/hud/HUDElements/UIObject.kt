package com.coden.starslicer.hud.HUDElements

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.coden.starslicer.entities.entityInterfaces.Mortal

interface UIObject: Mortal {
    fun update()
    fun render(batch: SpriteBatch)
    fun render(shapeRenderer: ShapeRenderer)
}