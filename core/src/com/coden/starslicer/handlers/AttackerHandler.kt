package com.coden.starslicer.handlers


import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.coden.starslicer.entities.attackers.Attacker.Companion.attackers
import com.coden.starslicer.util.Log


class AttackerHandler {

    fun renderAll(batch: SpriteBatch) {
        for (attacker in attackers) {
            attacker.render(batch)
        }
    }

    fun updateAll() {
        val iterator = attackers.iterator()
        while (iterator.hasNext()) {
            val attacker = iterator.next()
            attacker.update()
            attacker.updateCollision()
            if (attacker.isDead) {
                Log.info("${attacker.name} is dead so removed from attackers", Log.LogType.ATTACKERS)
                iterator.remove()
            }
        }
    }


}
