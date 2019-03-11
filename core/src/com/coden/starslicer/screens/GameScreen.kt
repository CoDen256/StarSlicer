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
import com.coden.starslicer.BladePoint
import com.coden.starslicer.InputManager
import com.coden.starslicer.hud.HUD
import com.coden.starslicer.StarSlicerGame
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.SpaceCraft
import com.coden.starslicer.handlers.AttackerHandler
import com.coden.starslicer.handlers.PowerUpHandler
import com.coden.starslicer.util.*

class GameScreen(val game: StarSlicerGame) : Screen {

    private lateinit var cam: OrthographicCamera
    private lateinit var batch: SpriteBatch
    private lateinit var hud: HUD
    private lateinit var shapeRenderer: ShapeRenderer

    private lateinit var spaceCraft: SpaceCraft

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

        spaceCraft = SpaceCraft()


        data = EntityData(spaceCraft, game.assets)

        hud = HUD(data)

        attackerHandler = AttackerHandler(data)
        powerUpHandler = PowerUpHandler(data)
        inputManager = InputManager(data)

        Gdx.app.log("GameScreen", "The screen is created")
        Gdx.app.log("GameScreen", "Size: $w x $h")
        Gdx.app.log("GameScreen", "xRatio: $xRatio, yRatio: $yRatio, sqRatio:$sqRatio")


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

        spaceCraft.render(batch)
        attackerHandler.renderAll(batch)


        batch.end()
    }

    // UPDATE SECTION
    fun update() {

        spaceCraft.update(game.swipeRenderer.swipe)

        attackerHandler.updateAll()
        powerUpHandler.updateAll()


        updateInput()

        hud.update()

    }

    fun updateInput() {
        attackerHandler.debugSpawning()
        hud.updateInput()
        inputManager.updateSwiping()
        inputManager.updateClicking()

    }


    // DEBUG SECTION

    fun debugShapes() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        renderRect(shapeRenderer, spaceCraft.hitBox)
        if (spaceCraft.isShielded){
            shapeRenderer.circle(spaceCraft.x, spaceCraft.y, spaceCraft.shieldRadius)
        }

        for (shockwave in data.shockWaves) {
            if (shockwave.active){
                shapeRenderer.circle(spaceCraftX, spaceCraftY, shockwave.radius)
            }

        }

        for (attacker in data.attackers) {
            renderRect(shapeRenderer, attacker.hitBox)
            shapeRenderer.circle(attacker.roundHitBox.x, attacker.roundHitBox.y, attacker.roundHitBox.radius)
        }

        //shapeRenderer.circle(blade0.pos.x, blade0.pos.y, blade0.radius)
        for (hitBox in spaceCraft.firstBlade.hitBoxes){
            renderRect(shapeRenderer, hitBox)
        }
        for (hitBox in spaceCraft.secondBlade.hitBoxes){
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

        Gdx.app.log("GameScreen", "The screen is disposed")
        batch.dispose()
        shapeRenderer.dispose()
        game.assets.dispose()

    }

}