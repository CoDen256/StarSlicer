package com.coden.starslicer.hud

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Logger
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.attackers.Attacker.Companion.attackers
import com.coden.starslicer.entities.spacecraft.SpaceCraft
import com.coden.starslicer.events.EventType
import com.coden.starslicer.events.Observer
import com.coden.starslicer.util.Log
import com.coden.starslicer.util.centerX


class HUD(data: EntityData): Observer {

    private val powerUpsBar = PowerUpsBar(Gdx.graphics.width*0.5f, Gdx.graphics.height*0.88f, data)

    private var batch: SpriteBatch = SpriteBatch()
    private var shapeRenderer: ShapeRenderer = ShapeRenderer()
    private var debugRenderer: ShapeRenderer = ShapeRenderer()
    private var countDownFont = BitmapFont()

    private val log = Logger("HUD", Logger.INFO)


    private val spaceCraftBar = HealthBar(SpaceCraft)

    var countDown = 0f

    init {
        countDownFont.data.setScale(2f)
    }

    override fun <T> onNotify(event: EventType, vararg params: T) {
        if (event == EventType.START_GAME){
            countDown = if (countDown == 0f) params[0] as Float else countDown
        }
        if (event == EventType.SPAWNED){

        }
    }




    fun update() {
        powerUpsBar.update()
        spaceCraftBar.update()

    }

    fun render() {
        batch.begin()

        powerUpsBar.render(batch)
        spaceCraftBar.render(batch)
        powerUpsBar.render(batch)
        updateCountDown(batch)

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

}


