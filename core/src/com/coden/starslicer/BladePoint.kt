package com.coden.starslicer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

class BladePoint(pointer: Int) {

    var active = false

    private var pos: Vector2 = Vector2(0f,0f)

    private val width = 5f
    private val hitBox = Circle(pos.x, pos.y, width)

    fun update() {
        if (active) {
            pos.x = Gdx.input.x+0f
            pos.y = Gdx.input.y+0f
        }
    }

    fun isSlicing(rect: Rectangle): Boolean {
        return rect.contains(hitBox)
    }
}