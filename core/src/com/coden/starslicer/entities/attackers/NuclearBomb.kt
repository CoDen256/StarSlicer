package com.coden.starslicer.entities.attackers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.entities.spacecraft.SpaceCraft
import com.coden.starslicer.entities.entityInterfaces.DamageGiver
import com.coden.starslicer.util.*

class NuclearBomb(override val initialPos: Vector2,
                  state: Int,
                  assets: Assets.AttackerAssets): Attacker(snapshot, state, assets), DamageGiver{

    companion object {
        val snapshot = EntityLoader.loadAttacker(AttackerType.NUCLEAR_BOMB)
        val current = arrayOf(0, 0)
    }

    val shieldAbsorbPortion = snapshot.shieldAbsorbPortion
    // Movement
    override var pos: Vector2 = initialPos
    override var velocity = Vector2()

    override val hitBox :Rectangle
        get() = Rectangle(pos.x - height/1.5f /2, pos.y - height/1.5f /2, height/1.5f , height/1.5f)

    override val hitSphere: Circle
        get() = Circle(pos.x, pos.y, minOf(width, height)/2)


    init {
        current[state] ++
        velocity = when (state) {
            0 -> targetVector.rotate(MathUtils.random(8, 45)*MathUtils.randomSign().toFloat()).setLength(maxMovementSpeed)
            1 -> targetVector.cpy().setLength(maxMovementSpeed)
            else -> Vector2()
        }

        Log.info("NB Launched at Vel:$velocity Angle:${velocity.angle()} Init:$initialPos State:$state")

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

    override fun kill() {
        super.kill()
        current[state] --
    }

}