package com.mygdx.util.swipe

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array

interface SwipeResolver {

    /**
     * Simplifies and smoothes the input.
     *
     * @param input the input of the swipe event
     * @param output the output instance
     */
    fun resolve(input: Array<Vector2>, output: Array<Vector2>)
}
