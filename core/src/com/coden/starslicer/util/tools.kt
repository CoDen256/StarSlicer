package com.coden.starslicer.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.spacecraft.SpaceCraft


fun generateRandomSpawnPoint() : Vector2 {

    val angle = MathUtils.random(0, 360).toFloat()
    val radius = dist2(centerX, centerY)+50
    return Vector2(1f, 0f).setLength(radius).rotate(angle).add(center)
}

fun generateRandomSpawnPoint(radius: Float) : Vector2 {

    val angle = MathUtils.random(0, 360).toFloat()
    return Vector2(1f, 0f).setLength(radius).rotate(angle).add(center)
}

fun quater(pos: Vector2) = when {//0,1,2,3 - right, top, left, bottom
    pos.y <= linePos(pos.x) && pos.y <= lineNeg(pos.x) -> 3
    pos.y >= linePos(pos.x) && pos.y <= lineNeg(pos.x) -> 2
    pos.y <= linePos(pos.x) && pos.y >= lineNeg(pos.x) -> 0
    pos.y >= linePos(pos.x) && pos.y >= lineNeg(pos.x) -> 1
    else -> -1
}

fun radius(pos: Vector2, vec: Vector2, startX: Float = 0f, startY: Float = 0f, shiftX: Float = 10f, shiftY: Float = 10f, width: Float = w, height: Float = h)
        = when(quater(pos)){
    0 -> { // right
        val alpha = vec.angleRad() + MathUtils.PI
        val r = pos.x - (startX + width)
        (r + shiftX) / Math.sin(alpha.toDouble())
    }

    1 -> { // top
        val alpha = vec.angleRad().toDouble()
        val r = pos.y - (startY + height)
        (r + shiftY) / Math.sin(alpha)
    }

    2 -> { // left
        val alpha = vec.angleRad() + MathUtils.PI
        val r = startX - pos.x
        (r + shiftX) / Math.sin(alpha.toDouble())
    }

    3 -> { // bottom
        val alpha = vec.angleRad().toDouble()
        val r = startY - pos.y
        (r + shiftY) / Math.sin(alpha)
    }
    else -> 0.0
}

fun dist4(x1:Float, y1:Float, x2:Float, y2:Float) = dist2(x2-x1, y2-y1)

fun dist2(x:Float, y:Float) = Math.sqrt(Math.pow(x.toDouble(), 2.0) + Math.pow(y.toDouble(), 2.0)).toFloat()
fun dist2(v1:Vector2, v2:Vector2) = dist2(v1.x-v2.x, v1.y-v2.y)

val h = Gdx.graphics.height + 0f
val w = Gdx.graphics.width + 0f

val xRatio = Gdx.graphics.width/2030f
val yRatio = Gdx.graphics.height/1080f
val sqRatio = dist2(xRatio, yRatio)

val spaceCraftX = SpaceCraft.x
val spaceCraftY = SpaceCraft.y
val spaceCraftCenter = Vector2(spaceCraftX, spaceCraftY)

val centerX = Gdx.graphics.width/2f
val centerY = Gdx.graphics.height/2f
val center = Vector2(centerX, centerY)

val linePos = {x: Float -> h/w * (x - w/2) + h/2}
val lineNeg = {x: Float -> -h/w * (x - w/2) + h/2}
