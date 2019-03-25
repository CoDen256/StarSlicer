package com.coden.starslicer.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Logger
import com.coden.starslicer.entities.*
import com.coden.starslicer.entities.spacecraft.SpaceCraft
import com.coden.starslicer.entities.attackers.*
import com.coden.starslicer.entities.attackers.Attacker.Companion.attackers
import com.coden.starslicer.entities.attackers.AttackerType.*
import com.coden.starslicer.entities.attackers.PowerUpContainer
import com.coden.starslicer.entities.attackers.Satellite
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.util.Log
import com.coden.starslicer.util.centerX
import com.coden.starslicer.util.centerY
import com.coden.starslicer.util.generateRandomSpawnPoint
import java.lang.IllegalArgumentException

class AttackerHandler(private val data: EntityData) {

    private val log = Logger("AttackerHandler", Logger.NONE)


    fun renderAll(batch: SpriteBatch) {
        for (attacker in attackers) {
            attacker.render(batch)
        }
    }

    fun updateAll() {
        Log.info("NB: ${NuclearBomb.maxAlive}, MISSILE: ${Missile.maxAlive},Meteor:  ${Meteor.maxAlive},Satellite: ${Satellite.maxAlive},PUC: ${PowerUpContainer.maxAlive}")
        Log.info("NB: ${NuclearBomb.current.toList()}, MISSILE: ${Missile.current.toList()},Meteor: ${Meteor.current.toList()},Satellite:${Satellite.current.toList()},PUC:${PowerUpContainer.current.toList()}")
        val iterator = attackers.iterator()
        while (iterator.hasNext()) {
            val attacker = iterator.next()
            attacker.update()
            updateCollision(attacker)
            if (attacker.isDead) {
                Log.info("${attacker.name} is dead")
                iterator.remove()
            }
        }
    }

    private fun updateCollision(attacker: Attacker) {
        if (SpaceCraft.isShielded) {
            if (SpaceCraft.shieldCircle.overlaps(attacker.hitSphere)) {
                attacker.onDestroy()
            }
        } else if (SpaceCraft.hitBox.overlaps(attacker.hitBox) && attacker.collisional) {
            attacker.giveDamage(SpaceCraft)
            attacker.onDestroy()
        }
    }
}
