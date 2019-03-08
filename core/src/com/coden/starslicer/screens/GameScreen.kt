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
import com.coden.starslicer.hud.HUD
import com.coden.starslicer.StarSlicerGame
import com.coden.starslicer.entities.SpaceCraft
import com.coden.starslicer.handlers.AttackerHandler
import com.coden.starslicer.handlers.PowerUpHandler
import com.coden.starslicer.util.spaceCraftX
import com.coden.starslicer.util.spaceCraftY

class GameScreen(val game: StarSlicerGame) : Screen {

    private lateinit var cam: OrthographicCamera
    private lateinit var batch: SpriteBatch
    private lateinit var hud: HUD
    private lateinit var shapeRenderer: ShapeRenderer

    private lateinit var spaceCraft: SpaceCraft

    private lateinit var attackerHandler: AttackerHandler
    private lateinit var powerUpHandler: PowerUpHandler

    private var blades = ArrayList<BladePoint>()

    val font = BitmapFont()

    val bg = Texture("bg.png")

    val w = Gdx.graphics.width + 0f
    val h = Gdx.graphics.height + 0f



    override fun show() {

        spaceCraft = SpaceCraft()
        blades.add(BladePoint(0, spaceCraft))
        blades.add(BladePoint(1, spaceCraft))

        attackerHandler = AttackerHandler()
        powerUpHandler = PowerUpHandler(spaceCraft)



        Gdx.app.log("GameScreen", "The screen is created")
        Gdx.app.log("GameScreen", "Size: $w x $h")

        cam = OrthographicCamera()

        cam.setToOrtho(false, w, h)

        batch = SpriteBatch()
        hud = HUD()
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

        // SWIPE RENDERER
        game.swipeRenderer.render(cam)

        // HUD
        hud.render()

        // SHAPE RENDERER FOR DEBUG
        debugShapes()
    }

    fun renderMainEntities() {
        batch.begin()

        batch.draw(bg, 0f, 0f, w, h)

        renderFPS(batch)

        spaceCraft.render(batch)
        attackerHandler.renderAll(batch)
        powerUpHandler.renderAll(batch)

        batch.end()
    }

    // UPDATE SECTION
    fun update() {

        spaceCraft.update()

        attackerHandler.updateAll(spaceCraft)
        powerUpHandler.updateAll()

        for (blade in blades){
            blade.update(game.swipeRenderer.swipe)
        }

        updateInput()
        updateSlicing()

        hud.update()
    }

    fun updateInput() {
        attackerHandler.updateInput()
        powerUpHandler.updateInput()

        if (Gdx.input.isTouched(0)) {
            blades[0].active = true

        }
        if (Gdx.input.isTouched(1)) {
            blades[1].active = true
        }

    }

    fun updateSlicing() {

        attackerHandler.updateSlicing(blades)

    }


    // DEBUG SECTION

    fun debugShapes() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        renderRect(shapeRenderer, spaceCraft.hitBox)
        if (spaceCraft.isShielded){
            shapeRenderer.circle(spaceCraft.x, spaceCraft.y, spaceCraft.shieldRadius)
        }

        for (shockwave in powerUpHandler.shockWaves) {
            if (shockwave.active){
                shapeRenderer.circle(spaceCraftX, spaceCraftY, shockwave.radius)
            }

        }

        for (attacker in attackerHandler.attackers) {
            renderRect(shapeRenderer, attacker.hitBox)
        }

        //shapeRenderer.circle(blade0.pos.x, blade0.pos.y, blade0.radius)
        for (hitBox in blades[0].hitBoxes){
            renderRect(shapeRenderer, hitBox)
        }
        for (hitBox in blades[1].hitBoxes){
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
        hud.dispose()

    }

}