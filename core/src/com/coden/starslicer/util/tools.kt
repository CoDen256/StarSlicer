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

fun generateRandomSpawnPoint(r: Float, shift: Float) : Vector2 {

    val angle = MathUtils.random(0, 360).toFloat()
    val radius = r+shift
    return Vector2(1f, 0f).setLength(radius).rotate(angle).add(center)
}

fun quater(pos: Vector2) = when {//0,1,2,3 - right, top, left, bottom
    pos.y <= linePos(pos.x) && pos.y <= lineNeg(pos.x)  -> {
        3
    }
    pos.y >= linePos(pos.x) && pos.y <= lineNeg(pos.x)  -> {
        2
    }
    pos.y <= linePos(pos.x) && pos.y >= lineNeg(pos.x)  -> {
        Log.info(" $pos ${linePos(pos.x)} ${lineNeg(pos.x)} ", Log.LogType.DEBUG)
        0
    }
    pos.y >= linePos(pos.x) && pos.y >= lineNeg(pos.x)  -> {
        Log.info(" $pos ${linePos(pos.x)} ${lineNeg(pos.x)} ", Log.LogType.DEBUG)
        1
    }
    else -> -1
}

fun radius(pos: Vector2, vec: Vector2, marginX: Float = 0f, marginY: Float = 0f, shiftX: Float = 10f, shiftY: Float = 10f, width: Float = w, height: Float = h)
        = when(quater(pos)){
    0 -> { // right
        val alpha = Math.toRadians(vec.angle() + 90.0) // TODO: PI
        val r = pos.x - (marginX + width)
        //Log.info("right $r ${Math.toDegrees(alpha)} $pos $vec", Log.LogType.DEBUG)
        (r + shiftX) / Math.sin(alpha)
    }

    1 -> { // top
        val alpha = vec.angleRad().toDouble()
        val r = pos.y - (marginY + height)
        //Log.info("top $r ${Math.toDegrees(alpha)} $pos $vec", Log.LogType.DEBUG)
        (r + shiftY) / Math.sin(alpha)
    }

    2 -> { // left
        val alpha = Math.toRadians(vec.angle() + 90.0)
        val r = marginX - pos.x
        //Log.info("left $r ${Math.toDegrees(alpha)} $pos $vec", Log.LogType.DEBUG)
        (r + shiftX) / Math.sin(alpha)
    }

    3 -> { // bottom
        val alpha = vec.angleRad().toDouble()
        val r = marginY - pos.y
        //Log.info("bottom $r ${Math.toDegrees(alpha)} $pos $vec", Log.LogType.DEBUG)
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

val linePos = {x: Float -> h/w * x}
val lineNeg = {x: Float -> -h/w * x}