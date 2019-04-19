package com.coden.starslicer.hud

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Logger
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.attackers.Attacker.Companion.attackers
import com.coden.starslicer.entities.attackers.NuclearBomb
import com.coden.starslicer.entities.spacecraft.SpaceCraft
import com.coden.starslicer.events.EventType
import com.coden.starslicer.events.Observer
import com.coden.starslicer.hud.HUDElements.ExclamationMark
import com.coden.starslicer.util.*


class HUD(data: EntityData): Observer {

    private val powerUpsBar = PowerUpsBar(Gdx.graphics.width*0.5f, Gdx.graphics.height*0.88f, data)

    private var batch: SpriteBatch = SpriteBatch()
    private var shapeRenderer: ShapeRenderer = ShapeRenderer()
    private var debugRenderer: ShapeRenderer = ShapeRenderer()
    private var countDownFont = BitmapFont()

    private val log = Logger("HUD", Logger.INFO)
    private val spaceCraftBar = HealthBar(SpaceCraft)

    var countDown = 0f
    val exclamations = ArrayList<ExclamationMark>()

    init {
        countDownFont.data.setScale(2f)

    }

    override fun <T> onNotify(event: EventType, vararg params: T) {
        if (event == EventType.START_GAME){
            countDown = if (countDown == 0f) params[0] as Float else countDown
        }
        if (event == EventType.SPAWNED){
            val attacker = params[0]
            if (attacker is NuclearBomb){
                renderExclamation(attacker.velocity)
            }
        }
    }




    fun update() {
        powerUpsBar.update()
        spaceCraftBar.update()
        val iterator = exclamations.iterator()
        while (iterator.hasNext()){
            val exlamation = iterator.next()
            exlamation.update()
            if (exlamation.isDead){
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

        exclamations.forEach { it.render(batch) }

        batch.end()


        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.setColor(1f, 1f, 1f, 1f)


        powerUpsBar.render(shapeRenderer)
        spaceCraftBar.render(shapeRenderer)

        for (attacker in attackers){
            attacker.renderHealthBar(shapeRenderer)

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


    fun debug() {
        debugRenderer.begin(ShapeRenderer.ShapeType.Line)

        powerUpsBar.debug(debugRenderer)

        debugRenderer.end()
    }

    fun renderExclamation(pos: Vector2){
        Log.info("$pos", Log.LogType.DEBUG)
        val targetVector = pos.cpy().sub(center)
        val alpha = 180.0 - targetVector.angle()//targetVector.angleRad().toDouble()
        val shiftX = 25
        val shiftY = 40

        var r = (centerY - shiftY)/Math.sin(Math.toRadians(alpha))
        r = if (Math.abs(r) > dist2((centerY-shiftY), centerX-shiftX)) (centerX-shiftX)/Math.cos(alpha) else r

        exclamations.add(ExclamationMark(center.cpy().add(targetVector.setLength(r.toFloat()))))

    }

}


