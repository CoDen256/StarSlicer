package com.mygdx.util.swipe.mesh

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array

class SwipeTriStrip {

    var previousInput = Array<Vector2>()

    internal var texcoord = Array<Vector2>()
    internal var tristrip = Array<Vector2>()
    internal var batchSize: Int = 0
    internal var perp = Vector2()
    var thickness = 15f
    var endcap = 8.5f
    var color = Color(Color.BLUE)
    internal var gl20: ImmediateModeRenderer20 = ImmediateModeRenderer20(false, true, 1)

    fun draw(cam: Camera) {
        if (tristrip.size <= 0)
            return

        gl20.begin(cam.combined, GL20.GL_TRIANGLE_STRIP)
        for (i in 0 until tristrip.size) {
            if (i == batchSize) {
                gl20.end()
                gl20.begin(cam.combined, GL20.GL_TRIANGLE_STRIP)
            }
            val point = tristrip.get(i)
            val tc = texcoord.get(i)
            gl20.color(color.r, color.g, color.b, color.a)
            gl20.texCoord(tc.x, 0f)
            gl20.vertex(point.x, point.y, 0f)
        }
        gl20.end()
    }

    private fun generate(input: Array<Vector2>, mult: Int): Int {
        val c = tristrip.size
        if (endcap <= 0) {
            tristrip.add(input.get(0))
        } else {
            val p = input.get(0)
            val p2 = input.get(1)
            perp.set(p).sub(p2).mul(endcap)
            tristrip.add(Vector2(p.x + perp.x, p.y + perp.y))
        }
        texcoord.add(Vector2(0f, 0f))

        for (i in 1 until input.size - 1) {
            val p = input.get(i)
            val p2 = input.get(i + 1)

            //get direction and normalize it
            perp.set(p).sub(p2).nor()

            //get perpendicular
            perp.set(-perp.y, perp.x)

            val thick = thickness * (1f - i / input.size.toFloat())

            //move outward by thickness
            perp.mul(thick / 2f)

            //decide on which side we are using
            perp.mul(mult.toFloat())

            //add the tip of perpendicular
            tristrip.add(Vector2(p.x + perp.x, p.y + perp.y))
            //0.0 -> end, transparent
            texcoord.add(Vector2(0f, 0f))

            //add the center point
            tristrip.add(Vector2(p.x, p.y))
            //1.0 -> center, opaque
            texcoord.add(Vector2(1f, 0f))
        }

        //final point
        if (endcap <= 0) {
            tristrip.add(input.get(input.size - 1))
        } else {
            val p = input.get(input.size - 2)
            val p2 = input.get(input.size - 1)
            perp.set(p2).sub(p).mul(endcap)
            tristrip.add(Vector2(p2.x + perp.x, p2.y + perp.y))
        }
        //end cap is transparent
        texcoord.add(Vector2(0f, 0f))
        return tristrip.size - c
    }

    fun update(input: Array<Vector2>) {
        tristrip.clear()
        texcoord.clear()

        if (input.size < 2)
            return

        batchSize = generate(input, 1)
        val b = generate(input, -1)
    }

}

private fun Vector2.mul(endcap: Float) {
    scl(endcap)
}
