package com.coden.starslicer.entities.attackers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.spacecraft.SpaceCraft
import com.coden.starslicer.entities.entityInterfaces.DamageGiver
import com.coden.starslicer.util.*

class NuclearBomb private constructor(override val initialPos: Vector2,
                  state: Int): Attacker(snapshot, state), DamageGiver{

    companion object {
        val snapshot = Attacker.loader.load(AttackerType.NUCLEAR_BOMB)
        fun spawn(state: Int): Attacker{
            val initialPos = generateRandomSpawnPoint()
            val nuclearBomb = NuclearBomb(initialPos, state)
            attackers.add(nuclearBomb)
            return nuclearBomb
        }
    }


    override val id = "nuc$state"

    val shieldAbsorbPortion = snapshot.shieldAbsorbPortion
    // Movement
    override var pos: Vector2 = initialPos
    override var velocity = Vector2()

    override val hitBox = Rectangle(0f, 0f, height/1.5f , height/1.5f)
    override val hitSphere = Circle(0f, 0f, minOf(width, height)/2)


    init {
        velocity = when (state) {
            0 -> targetVector.cpy().rotate(MathUtils.random(8, 45)*MathUtils.randomSign().toFloat()).setLength(maxMovementSpeed)
            1 -> targetVector.cpy().setLength(maxMovementSpeed)
            else -> Vector2()
        }

        Log.info("NB $id Launched at Vel:$velocity Angle:${velocity.angle()} Init:$initialPos State:$state", Log.LogType.ATTACKERS)

        sprite.setCenter(pos.x,pos.y)
        sprite.rotate(velocity.angle()+90)
    }

    override fun update() {
        updateLife()
        pos.add(velocity.cpy().scl(Gdx.graphics.deltaTime))
        sprite.setScale(xRatio, yRatio)
        sprite.setCenter(pos.x, pos.y)
    }

    fun damageAll() {
        for (attacker in attackers) {
            giveDamage(attacker)
        }

        if (SpaceCraft.isShielded){
            giveDamage(SpaceCraft, damage*shieldAbsorbPortion)
            SpaceCraft.isShielded = false
        }else{
            giveDamage(SpaceCraft, damage)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        damageAll()
    }

}