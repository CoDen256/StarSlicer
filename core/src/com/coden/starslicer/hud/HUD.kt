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
import com.coden.starslicer.hud.HUDElements.UIObject
import com.coden.starslicer.util.*


class HUD: Observer {

    private val powerUpsBar = PowerUpsBar(Gdx.graphics.width*0.5f, Gdx.graphics.height*0.88f)

    private var batch: SpriteBatch = SpriteBatch()
    private var shapeRenderer: ShapeRenderer = ShapeRenderer()
    private var debugRenderer: ShapeRenderer = ShapeRenderer()
    // TODO: Text class
    private var countDownFont = BitmapFont()
    private var statsFont = BitmapFont()

    private val log = Logger("HUD", Logger.INFO)
    private val spaceCraftBar = HealthBar(Locator.spaceCraft)
    private val healthBars = ArrayList<UIObject>()

    var countDown = 0f
    val exclamations = ArrayList<UIObject>()

    init {
        countDownFont.data.setScale(2f)
        statsFont.data.setScale(1f)

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
        updateUIObjects(exclamations)
        updateUIObjects(healthBars)

    }

    fun updateUIObjects(container: ArrayList<UIObject>){
        val iterator = container.iterator()
        while (iterator.hasNext()){
            val uiObject = iterator.next()
            uiObject.update()
            if (uiObject.isDead){
                iterator.remove()
            }
        }
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

        countDownFont.draw(batch, "$countDown", 50f, Gdx.graphics.height-400f)
        countDown -= Gdx.graphics.deltaTime
    }

    fun renderStats(batch: SpriteBatch){
        statsFont.draw(batch, "Points:${Locator.getGameData().points}\nCoints: ${Locator.getGameData().coins}", w-100, h-200)
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


