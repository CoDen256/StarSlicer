package com.coden.starslicer.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.coden.starslicer.handlers.InputManager
import com.coden.starslicer.hud.HUD
import com.coden.starslicer.StarSlicerGame
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.spacecraft.SpaceCraft
import com.coden.starslicer.handlers.AttackerHandler
import com.coden.starslicer.handlers.PowerUpHandler
import com.coden.starslicer.util.*

class GameScreen(val game: StarSlicerGame) : Screen {

    private lateinit var cam: OrthographicCamera
    private lateinit var batch: SpriteBatch
    private lateinit var hud: HUD
    private lateinit var shapeRenderer: ShapeRenderer

    private lateinit var attackerHandler: AttackerHandler
    private lateinit var powerUpHandler: PowerUpHandler

    private lateinit var data: EntityData
    private lateinit var inputManager: InputManager

    val font = BitmapFont()

    val bg = Texture("bg.png")

    val w = Gdx.graphics.width + 0f
    val h = Gdx.graphics.height + 0f



    override fun show() {

        game.assets.finishLoading()// TODO: Initialize in starting screen


        data = EntityData(game.assets)

        hud = HUD(data)

        attackerHandler = AttackerHandler(data)
        powerUpHandler = PowerUpHandler(data)
        inputManager = InputManager(data)

        Log.info("The screen is created")
        Log.info("Size: $w x $h")
        Log.info("xRatio: $xRatio, yRatio: $yRatio, sqRatio:$sqRatio")

        Log.info("${SpaceCraft}")

        cam = OrthographicCamera()
        cam.setToOrtho(false, w, h)

        batch = SpriteBatch()
        shapeRenderer = ShapeRenderer()

        cam.update()
        batch.projectionMatrix = cam.combined


    }
    // RENDER SECTION

    override fun render(delta: Float) {
        // UPDATING EVENTS
        update()

        // MAIN BATCH

        renderMainEntities()

        // HUD
        hud.render()

        // SWIPE RENDERER
        game.swipeRenderer.render(cam)

        // SHAPE RENDERER FOR DEBUG
        debugShapes()
    }

    fun renderMainEntities() {
        batch.begin()

        batch.draw(bg, 0f, 0f, w, h)

        renderFPS(batch)

        SpaceCraft.render(batch)
        attackerHandler.renderAll(batch)


        batch.end()
    }

    // UPDATE SECTION
    fun update() {

        SpaceCraft.update(game.swipeRenderer.swipe)

        attackerHandler.updateAll()
        powerUpHandler.updateAll()


        updateInput()

        hud.update()

    }

    fun updateInput() {
        attackerHandler.debugSpawning()
        inputManager.updateSwiping()
        inputManager.updateClicking()

    }


    // DEBUG SECTION

    fun debugShapes() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        renderRect(shapeRenderer, SpaceCraft.hitBox)
        if (SpaceCraft.isShielded){
            shapeRenderer.circle(SpaceCraft.x, SpaceCraft.y, SpaceCraft.shieldRadius)
        }

        for (shockwave in data.shockWaves) {
            if (shockwave.active){
                shapeRenderer.circle(spaceCraftX, spaceCraftY, shockwave.radius)
            }

        }

        for (attacker in data.attackers) {
            renderRect(shapeRenderer, attacker.hitBox)
            shapeRenderer.circle(attacker.hitSphere.x, attacker.hitSphere.y, attacker.hitSphere.radius)
        }

        for (hitBox in SpaceCraft.firstBlade.hitBoxes){
            renderRect(shapeRenderer, hitBox)
        }
        for (hitBox in SpaceCraft.secondBlade.hitBoxes){
            renderRect(shapeRenderer, hitBox)
        }


        shapeRenderer.end()

    }
    fun renderFPS(batch: SpriteBatch) {
        font.draw(batch, Gdx.graphics.framesPerSecond.toString(), w-50, h-75)
    }

    fun renderVector(shapeRenderer: ShapeRenderer, pos: Vector2, vector: Vector2) {
        shapeRenderer.line(pos, pos.cpy().add(vector.cpy().setLength(50f)))
    }

    fun renderRect(shapeRenderer: ShapeRenderer,rect: Rectangle) {
        shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun dispose() {

        Log.info("The screen is disposed")
        batch.dispose()
        shapeRenderer.dispose()
        SpaceCraft.dispose()

    }

}