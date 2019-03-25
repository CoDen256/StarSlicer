package com.coden.starslicer.Commands

import com.badlogic.gdx.utils.Queue
import com.coden.starslicer.entities.EntityData

class CommandQueue(val data: EntityData) {
    val container = Queue<Command>()

    fun update(){
    }

    fun add(command: Command){
        container.addLast(command)
    }

    fun executeNext(){
        if (container.size == 0) return
        container.removeFirst().execute(data)
    }

}