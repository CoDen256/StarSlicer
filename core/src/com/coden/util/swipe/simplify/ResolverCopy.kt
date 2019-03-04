package com.coden.util.swipe.simplify


import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.coden.util.swipe.SwipeResolver

class ResolverCopy : SwipeResolver {

    override fun resolve(input: Array<Vector2>, output: Array<Vector2>) {
        output.clear()
        output.addAll(input)
    }

}
