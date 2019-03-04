package com.coden.starslicer.util

import com.badlogic.gdx.Gdx

fun dist4(x1:Float, y1:Float, x2:Float, y2:Float) = dist2(x2-x1, y2-y1)

fun dist2(x:Float, y:Float) = Math.sqrt(Math.pow(x.toDouble(), 2.0) + Math.pow(y.toDouble(), 2.0))

val xRatio = Gdx.graphics.width/2030f
val yRatio = Gdx.graphics.height/1080f