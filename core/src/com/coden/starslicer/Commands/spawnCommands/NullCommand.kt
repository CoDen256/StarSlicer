package com.coden.starslicer.Commands.spawnCommands

import com.coden.starslicer.Commands.Command
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.events.Observer

class NullCommand: SpawnCommand {
    override val subscribers = ArrayList<Observer>()
    override fun execute(data: EntityData) {}
}