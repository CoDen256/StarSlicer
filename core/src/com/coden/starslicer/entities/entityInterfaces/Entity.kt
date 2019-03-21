package com.coden.starslicer.entities.entityInterfaces

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2


interface Entity: DamageGiver, DamageTaker {
    companion object {
        val entities = ArrayList<Entity>()
    }

    var hitBox: Rectangle
    var hitCircle: Circle
    var pos: Vector2

}