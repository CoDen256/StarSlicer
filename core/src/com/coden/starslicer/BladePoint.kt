package com.coden.starslicer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.coden.starslicer.entities.SpaceCraft
import com.coden.starslicer.util.sqRatio
import com.coden.util.swipe.SwipeHandler

class BladePoint(val pointer: Int, spaceCraft: SpaceCraft) {

    val damage = spaceCraft.damage
    val detectionRatio = 0.6f
    val size = 35f*sqRatio

    var active = false

    var hitBoxes = ArrayList<Rectangle>()

    fun update(swipe: SwipeHandler) {
        if (active) {
            hitBoxes.clear()
            val simplified = swipe.path(pointer)
            for (i in 0 until Math.round(simplified.size*detectionRatio)) {
                if (i % 2 == 0){
                    hitBoxes.add(Rectangle(simplified[i].x-size/2, simplified[i].y-size/2, size, size))
                }

            }

        }

        active = false
    }

    fun isSlicing(rect: Rectangle): Boolean {
        for (hB in hitBoxes){
            if(hB.overlaps(rect) && active)
                return true
        }
        return false

    }
}