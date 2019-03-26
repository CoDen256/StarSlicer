package com.coden.starslicer.Commands

import com.badlogic.gdx.utils.Queue
import com.coden.starslicer.entities.EntityData

class CommandQueue(val data: EntityData) {
    val container = Queue<Command>()

    fun add(command: Command, amount: Int = 1){
        for(i in 0 until amount){
            container.addLast(command)
        }
    }

    fun executeNext(){
        if (container.size == 0) return
        container.removeFirst().execute(data)
    }

    val isEmpty get() = container.size == 0

}