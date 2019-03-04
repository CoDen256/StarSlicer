package com.coden.util.swipe.simplify

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.coden.util.swipe.SwipeResolver


//Events that trigger a "slash"
//- Reached max number of points; slash stops and fades out
//- Delay from touchDown is too long

class ResolverRadialChaikin : SwipeResolver {

    private val tmp = Array<Vector2>(Vector2::class.java)

    override fun resolve(input: Array<Vector2>, output: Array<Vector2>) {
        var input = input
        var output = output
        output.clear()
        if (input.size <= 2) { //simple copy
            output.addAll(input)
            return
        }

        //simplify with squared tolerance
        if (simplifyTolerance > 0 && input.size > 3) {
            simplify(input, simplifyTolerance * simplifyTolerance, tmp)
            input = tmp
        }

        //perform smooth operations
        if (iterations <= 0) { //no smooth, just copy input to output
            output.addAll(input)
        } else if (iterations == 1) { //1 iteration, smooth to output
            smooth(input, output)
        } else { //multiple iterations.. ping-pong between arrays
            var iters = iterations
            //subsequent iterations
            do {
                smooth(input, output)
                tmp.clear()
                tmp.addAll(output)
                val old = output
                input = tmp
                output = old
            } while (--iters > 0)
        }
    }

    companion object {

        var iterations = 2
        var simplifyTolerance = 35f

        fun smooth(input: Array<Vector2>, output: Array<Vector2>) {
            //expected size
            output.clear()
            output.ensureCapacity(input.size * 2)

            //first element
            output.add(input[0])
            //average elements
            for (i in 0 until input.size - 1) {
                val p0 = input[i]
                val p1 = input[i + 1]


                val Q = Vector2(0.75f * p0.x + 0.25f * p1.x, 0.75f * p0.y + 0.25f * p1.y)
                val R = Vector2(0.25f * p0.x + 0.75f * p1.x, 0.25f * p0.y + 0.75f * p1.y)
                output.add(Q)
                output.add(R)
            }

            //last element
            output.add(input[input.size - 1])
        }

        //simple distance-based simplification
        //adapted from simplify.js
        fun simplify(points: Array<Vector2>, sqTolerance: Float, out: Array<Vector2>) {
            val len = points.size

            var point = Vector2()
            var prevPoint = points[0]

            out.clear()
            out.add(prevPoint)

            for (i in 1 until len) {

                if (points[i] == null){
                    break
                }

                point = points[i]

                if (distSq(point, prevPoint) > sqTolerance) {
                    out.add(point)
                    prevPoint = point
                }
            }
            if (prevPoint != point) {
                out.add(point)
            }
        }

        fun distSq(p1: Vector2, p2: Vector2): Float {
            val dx = p1.x - p2.x
            val dy = p1.y - p2.y
            return dx * dx + dy * dy
        }
    }

}

