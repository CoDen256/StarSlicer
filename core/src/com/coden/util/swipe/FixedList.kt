package com.coden.util.swipe

import com.badlogic.gdx.utils.Array

/**
 * A simple extension of Array that allows inserting an element
 * at the head (index 0) without ever growing the backing array.
 * Elements are shifted right and eventually discarded to make
 * way for new additions.
 *
 * @author mattdesl
 * @param <T> generic type
</T> */
class FixedList<T>
/**
 * Safely creates a list that is backed by an array which
 * can be directly accessed.
 *
 * @param capacity the fixed-size capacity of this list
 * @param type the class type of the elements in this list
 */
(capacity: Int, type: Class<T>) : Array<T>(false, capacity, type) {

    /**
     * Inserts the item into index zero, shifting all items to the right,
     * but without increasing the list's size past its array capacity.
     * @param t the element to insert
     */
    fun insert(t: T) {
        val items = this.items

        // increase size if we have a new point
        size = Math.min(size + 1, items.size)

        // shift elements right
        for (i in size - 1 downTo 1) {
            items[i] = items[i - 1]
        }

        // insert new item at first index
        items[0] = t
    }


}
