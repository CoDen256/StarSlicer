package com.coden.starslicer.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.attackers.Attacker
import com.coden.starslicer.entities.powerups.PowerUp


fun generateRandomSpawnPoint() : Vector2 {

    val angle = MathUtils.random(0, 360).toFloat()
    val radius = dist2(centerX, centerY)+50
    return Vector2(1f, 0f).setLength(radius).rotate(angle).add(center)
}

fun dist4(x1:Float, y1:Float, x2:Float, y2:Float) = dist2(x2-x1, y2-y1)

fun dist2(x:Float, y:Float) = Math.sqrt(Math.pow(x.toDouble(), 2.0) + Math.pow(y.toDouble(), 2.0)).toFloat()
fun dist2(v1:Vector2, v2:Vector2) = dist2(v1.x-v2.x, v1.y-v2.y)

val xRatio = Gdx.graphics.width/2030f
val yRatio = Gdx.graphics.height/1080f
val sqRatio = dist2(xRatio, yRatio)

val spaceCraftX = Gdx.graphics.width*0.5f
val spaceCraftY = Gdx.graphics.height*0.5f
val spaceCraftCenter = Vector2(spaceCraftX, spaceCraftY)

val centerX = Gdx.graphics.width/2f
val centerY = Gdx.graphics.height/2f
val center = Vector2(centerX, centerY)