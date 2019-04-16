package com.coden.starslicer.events

interface Observer {
    fun <T> onNotify(event: EventType, vararg params: T)
}