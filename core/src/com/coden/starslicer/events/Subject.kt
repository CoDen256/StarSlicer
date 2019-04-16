package com.coden.starslicer.events

import com.coden.starslicer.entities.attackers.AttackerType
import com.coden.starslicer.entities.entityInterfaces.Mortal
import com.coden.starslicer.entities.powerups.PowerUp

open class Subject {
    val subscribers = ArrayList<Observer>()

    fun <T> notify(event: EventType, params: T){
        subscribers.forEach { it.onNotify(event, params) }
    }

    fun addObserver(observer: Observer){
        subscribers.add(observer)
    }

    fun removeObserver(observer: Observer){
        subscribers.remove(observer)
    }
}