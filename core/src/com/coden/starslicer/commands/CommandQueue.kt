package com.coden.starslicer.commands

import com.badlogic.gdx.utils.Queue
import com.coden.starslicer.commands.spawnCommands.NullSpawnCommand

class CommandQueue {
    val container = Queue<Command>()

    fun add(command: Command, amount: Int = 1){
        for(i in 0 until amount){
            container.addLast(command)
        }
    }

    fun executeNext(): Command{
        if (container.size == 0) return NullSpawnCommand()
        val command = container.removeFirst()
        command.execute()
        return command
    }

    val isEmpty get() = container.size == 0

}