package com.coden.starslicer.gameObjects.attackers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.gameObjects.DamageGiver
import com.coden.starslicer.graphics.Animator
import com.coden.starslicer.util.*

class NuclearBomb private constructor(override val initialPos: Vector2,
                  state: Int): Attacker(snapshot, state), DamageGiver {

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

    override val hitBox = Rectangle(0f, 0f, height/1.35f , height/1.35f)
    override val hitSphere = Circle(0f, 0f, minOf(width, height)/2)

    val animator = Animator("entities/animation/attackers/NuclearBomb/nuclearbomb_anim.png", 1, 22, 0.025f)
    val sprite2 = Sprite(animator.firstFrame)
    init {
        velocity = when (state) {
            0 -> targetVector.cpy().rotate(MathUtils.random(8, 45)*MathUtils.randomSign().toFloat()).setLength(maxMovementSpeed)
            1 -> targetVector.cpy().setLength(maxMovementSpeed)
            else -> Vector2()
        }

        Log.info("NB $id Launched at Vel:$velocity Angle:${velocity.angle()} Init:$initialPos State:$state", Log.LogType.ATTACKERS)

        sprite2.setCenter(pos.x,pos.y)
        sprite2.rotate(velocity.angle()+180f)
    }

    override fun render(batch: SpriteBatch) {
        animator.render(batch, sprite2)
    }

    override fun update() {
        updateLife()
        pos.add(velocity.cpy().scl(Gdx.graphics.deltaTime))
        sprite2.setScale(xRatio, yRatio)
        sprite2.setCenter(pos.x, pos.y)
    }

    fun damageAll() {
        for (attacker in attackers) {
            giveDamage(attacker)
        }

        if (Locator.spaceCraft.isShielded){
            giveDamage(Locator.spaceCraft, damage*shieldAbsorbPortion)
            Locator.spaceCraft.isShielded = false
        }else{
            giveDamage(Locator.spaceCraft, damage)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        damageAll()
    }

}