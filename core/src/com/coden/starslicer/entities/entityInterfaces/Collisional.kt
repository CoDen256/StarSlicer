package com.coden.starslicer.entities.entityInterfaces

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle


interface Collisional {
    val hitBox: Rectangle
    val hitSphere: Circle
}