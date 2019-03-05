package com.coden.starslicer.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2

fun dist4(x1:Float, y1:Float, x2:Float, y2:Float) = dist2(x2-x1, y2-y1)

fun dist2(x:Float, y:Float) = Math.sqrt(Math.pow(x.toDouble(), 2.0) + Math.pow(y.toDouble(), 2.0)).toFloat()
fun dist2(v1:Vector2, v2:Vector2) = dist2(v1.x-v2.x, v1.y-v2.y)

val xRatio = Gdx.graphics.width/2030f
val yRatio = Gdx.graphics.height/1080f

val centerX = Gdx.graphics.width/2f
val centerY = Gdx.graphics.height/2f
val center = Vector2(centerX, centerY)