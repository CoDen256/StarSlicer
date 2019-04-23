package com.coden.starslicer.Commands.spawnCommands

import com.coden.starslicer.Commands.Command
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.events.Observer

class NullSpawnCommand: SpawnCommand() {
    override fun execute() {}
}