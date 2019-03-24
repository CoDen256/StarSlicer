package com.coden.starslicer.entities.spacecraft

import com.badlogic.gdx.math.Rectangle
import com.coden.starslicer.util.Log
import com.coden.starslicer.util.sqRatio
import com.coden.util.swipe.SwipeHandler

class BladePoint(val pointer: Int) {

    val detectionRatio = 0.7f
    val cutRatio = 2
    val firstIgnored = 3
    val maxPoints = 40


    var hitBoxes = ArrayList<Rectangle>()

    val damageFade get() = if (hitBoxes.size > firstIgnored) (hitBoxes.size)/(detectionRatio*maxPoints/cutRatio) else 0f

    val damage get() = SpaceCraft.damage * damageFade

    var active = false
    val size = 35f*sqRatio

    fun update(swipe: SwipeHandler) {
        if (active) {
            hitBoxes.clear()
            val simplified = swipe.path(pointer)
            for (i in 0 until Math.round(simplified.size*detectionRatio)) {
                if (i % cutRatio == 0){
                    hitBoxes.add(Rectangle(simplified[i].x-size/2, simplified[i].y-size/2, size, size))
                }

            }
            //Log.info(hitBoxes.size.toString())
            //Log.info(simplified.size.toString())
            //Log.info("Damage fade: $damageFade")

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