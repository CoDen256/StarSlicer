package com.coden.starslicer.gameplay.spawnerStates

import com.coden.starslicer.commands.CommandQueue

interface SpawnerState {
    fun execute(queue: CommandQueue): SpawnerState?
    fun update(queue: CommandQueue) : SpawnerState?
}