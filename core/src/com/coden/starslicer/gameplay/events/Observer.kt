package com.coden.starslicer.gameplay.events

interface Observer {
    fun <T> onNotify(event: EventType, vararg params: T)
}