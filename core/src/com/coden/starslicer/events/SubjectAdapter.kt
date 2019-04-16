package com.coden.starslicer.events

abstract class SubjectAdapter: Subject {
    override val subscribers = ArrayList<Observer>()
}