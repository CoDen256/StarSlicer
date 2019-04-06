package com.coden.starslicer.states.spawner

import com.coden.starslicer.Commands.CommandQueue

interface SpawnerState {
    fun execute(queue: CommandQueue): SpawnerState?
    fun update(queue: CommandQueue) : SpawnerState?
}