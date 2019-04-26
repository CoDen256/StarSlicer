package com.coden.starslicer.hud.HUDElements

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.coden.starslicer.entities.entityInterfaces.Mortal

interface UIObject: Mortal {
    companion object {
        fun update(list: ArrayList<UIObject>){
            val iterator = list.iterator()
            while (iterator.hasNext()){
                val uiObject = iterator.next()
                uiObject.update()
                if (uiObject.isDead){
                    iterator.remove()
                }
            }
        }
    }
    fun update()
    fun render(batch: SpriteBatch)
    fun render(shapeRenderer: ShapeRenderer)
    fun dispose()
}