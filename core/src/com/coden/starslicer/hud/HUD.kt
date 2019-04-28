package com.coden.starslicer.hud

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Logger
import com.coden.starslicer.entities.attackers.Attacker
import com.coden.starslicer.entities.attackers.Missile
import com.coden.starslicer.entities.attackers.NuclearBomb
import com.coden.starslicer.entities.powerups.PowerUp
import com.coden.starslicer.events.EventType
import com.coden.starslicer.events.Observer
import com.coden.starslicer.hud.HUDElements.ExclamationMark
import com.coden.starslicer.hud.HUDElements.HealthBar
import com.coden.starslicer.hud.HUDElements.Label
import com.coden.starslicer.hud.HUDElements.UIObject
import com.coden.starslicer.util.*


class HUD: Observer {

    private val powerUpsBar = PowerUpsBar(Gdx.graphics.width*0.5f, Gdx.graphics.height*0.88f)

    private var batch: SpriteBatch = SpriteBatch()
    private var shapeRenderer: ShapeRenderer = ShapeRenderer()
    private var debugRenderer: ShapeRenderer = ShapeRenderer()
    // TODO: Text class
    private var countDownLabel = Label(BitmapFont())
    private var statsLabel = Label(BitmapFont())

    private val log = Logger("HUD", Logger.INFO)
    private val spaceCraftBar = HealthBar(Locator.spaceCraft)
    private val healthBars = ArrayList<UIObject>()

    var countDown = 0f
    val exclamations = ArrayList<UIObject>()

    init {
        countDownLabel.setScale(2f)
        statsLabel.setScale(1f)

    }

    override fun <T> onNotify(event: EventType, vararg params: T) {
        if (event == EventType.START_GAME){
            countDown = if (countDown == 0f) params[0] as Float else countDown
        }
        if (event == EventType.ADDED){
            powerUpsBar.increaseAmount(params[0] as PowerUp.PowerUpType)
        }

        if (event == EventType.USED){
            powerUpsBar.decreaseAmount(params[0] as PowerUp.PowerUpType)

        }

        if (event == EventType.SPAWNED){
            val attacker = params[0] as Attacker
            if (attacker !is Missile){
                healthBars.add(HealthBar(attacker))
            }
            if (attacker is NuclearBomb){
                renderExclamation(attacker.initialPos, attacker.velocity)
            }
        }
    }




    fun update() {
        powerUpsBar.update()
        spaceCraftBar.update()
        UIObject.update(exclamations)
        UIObject.update(healthBars)

    }



    fun render() {
        batch.begin()

        powerUpsBar.render(batch)
        spaceCraftBar.render(batch)
        powerUpsBar.render(batch)
        updateCountDown(batch)
        renderStats(batch)

        exclamations.forEach { it.render(batch) }

        batch.end()


        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.setColor(1f, 1f, 1f, 1f)


        powerUpsBar.render(shapeRenderer)
        spaceCraftBar.render(shapeRenderer)

        for (healthBar in healthBars){
            healthBar.render(shapeRenderer)
        }

        shapeRenderer.end()

        debug()

    }

    fun updateCountDown(batch: SpriteBatch){
        if (countDown <= 0) {
            countDown = 0f
            return
        }

        countDownLabel.setPosition(50f, Gdx.graphics.height-400f)
        countDownLabel.render(batch, "$countDown")
        countDown -= Gdx.graphics.deltaTime
    }

    fun renderStats(batch: SpriteBatch){
        statsLabel.setPosition(w-100, h-200)
        statsLabel.render(batch, "Points:${Locator.getGameData().points}\nCoints: ${Locator.getGameData().coins}")
    }


    fun debug() {
        debugRenderer.begin(ShapeRenderer.ShapeType.Line)

        powerUpsBar.debug(debugRenderer)

        debugRenderer.end()
    }

    fun renderExclamation(pos: Vector2, vel: Vector2){
        val r = radius(pos.cpy(), vel.cpy(), shiftY = 50f, shiftX = 15f)
        exclamations.add(ExclamationMark(pos.cpy().add(vel.cpy().setLength(r.toFloat()))))

    }

}


