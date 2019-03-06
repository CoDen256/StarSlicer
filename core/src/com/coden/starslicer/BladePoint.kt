package com.coden.starslicer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

class BladePoint(val pointer: Int) {

    var active = false

    var pos: Vector2 = Vector2(0f,0f)

    val radius = 20f
    val size = 20f

    private var hitBoxC = Circle(0f,0f, radius)
            get() = Circle(pos.x, pos.y, radius)

    var hitBox = Rectangle(0f, 0f, 0f, 0f)
            get() = Rectangle(pos.x-size/2f, pos.y-size/2f, size, size)

    fun update() {
        if (active) {
            pos = Vector2(Gdx.input.getX(pointer).toFloat(), Gdx.graphics.height - Gdx.input.getY(pointer).toFloat())
        }

        active = false
    }

    fun isSlicing(rect: Rectangle): Boolean {
        Gdx.app.log("isSlicing", "$rect - $hitBox ${rect.contains(hitBox) && active}")
        return hitBox.overlaps(rect) && active
    }
}