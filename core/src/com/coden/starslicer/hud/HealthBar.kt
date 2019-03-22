package com.coden.starslicer.hud

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.coden.starslicer.entities.entityInterfaces.DamageTaker
import com.coden.starslicer.util.Log
import sun.java2d.pipe.SpanShapeRenderer

class HealthBar(val entity: DamageTaker) {

    val x  get() = entity.hitBox.x
    val y get() = entity.hitBox.y+entity.hitBox.height
    val width get()  = entity.hitBox.width
    val height get() = entity.hitBox.height*0.2f

    val health get() = entity.health
    val maxHealth get() = entity.maxHealth


    fun update(){


    }

    fun render(batch: SpriteBatch) {

    }

    fun render(shapeRenderer: ShapeRenderer){
        shapeRenderer.setAutoShapeType(true)

        shapeRenderer.set(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.setColor(1.0f, 0f, 0f, 0f)
        shapeRenderer.rect(x, y, width * (health/maxHealth), height)

        shapeRenderer.set(ShapeRenderer.ShapeType.Line)
        shapeRenderer.rect(x, y, width, height)


    }
}