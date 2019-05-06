package com.coden.starslicer.gameplay.events

interface Subject {
    val subscribers : ArrayList<Observer>

    fun <T> notify(event: EventType, vararg params: T){
        subscribers.forEach { it.onNotify(event, *params) }
    }

    fun addObserver(observer: Observer){
        if (observer !in subscribers){
            subscribers.add(observer)
        }

    }

    fun removeObserver(observer: Observer){
        subscribers.remove(observer)
    }
}