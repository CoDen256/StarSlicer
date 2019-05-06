package com.coden.starslicer.gameplay.spawner

import com.coden.starslicer.gameplay.commands.CommandQueue

interface SpawnerState {
    fun execute(queue: CommandQueue): SpawnerState?
    fun update(queue: CommandQueue) : SpawnerState?
}