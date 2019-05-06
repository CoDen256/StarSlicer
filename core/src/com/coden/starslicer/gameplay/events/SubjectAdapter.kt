package com.coden.starslicer.gameplay.events

abstract class SubjectAdapter: Subject {
    override val subscribers = ArrayList<Observer>()
}