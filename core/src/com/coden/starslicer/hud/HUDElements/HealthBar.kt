package com.coden.starslicer.hud.HUDElements

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.coden.starslicer.entities.entityInterfaces.DamageTaker
import com.coden.starslicer.entities.entityInterfaces.Mortal
import com.coden.starslicer.util.Log
import sun.java2d.pipe.SpanShapeRenderer

class HealthBar(val entity: DamageTaker): UIObject {

    val x  get() = entity.hitBox.x
    val y get() = entity.hitBox.y+entity.hitBox.height
    val width= entity.hitBox.width
    val height= entity.hitBox.height*0.1f

    override var isDead = false


    val margin = entity.hitBox.height * 0.18f
    val widthScale = 0.8f
    val scaledWidth = width*widthScale

    val health get() = entity.health
    val maxHealth  = entity.maxHealth


    init {
        Log.info("HealthBar for $entity was created", Log.LogType.HUD)
    }

    override fun update(){

        if (health <= 0) {
            kill()
        }

    }

    override fun render(batch: SpriteBatch) {

    }

    override fun render(shapeRenderer: ShapeRenderer){
        shapeRenderer.setAutoShapeType(true)

        shapeRenderer.set(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.setColor(0.75f, 0.2f, 0.2f, 1f)
        shapeRenderer.rect(x + (width - scaledWidth) /2, y+margin, scaledWidth * (health/maxHealth), height)

        shapeRenderer.set(ShapeRenderer.ShapeType.Line)
        shapeRenderer.setColor(1f, 1f, 1f, 1f)
        shapeRenderer.rect(x + (width - scaledWidth) /2, y+margin, scaledWidth, height)

    }

    fun dispose(){
    }
}