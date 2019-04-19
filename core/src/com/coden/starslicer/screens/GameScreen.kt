package com.coden.starslicer.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Graphics
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Json
import com.coden.starslicer.Commands.spawnCommands.SpawnCommand
import com.coden.starslicer.Commands.spawnCommands.SpawnMissile
import com.coden.starslicer.Commands.spawnCommands.SpawnNuclearBomb
import com.coden.starslicer.Commands.spawnCommands.SpawnSatellite
import com.coden.starslicer.handlers.InputManager
import com.coden.starslicer.hud.HUD
import com.coden.starslicer.StarSlicerGame
import com.coden.starslicer.entities.EntityData
import com.coden.starslicer.entities.attackers.Attacker.Companion.attackers
import com.coden.starslicer.entities.powerups.PowerUp.Companion.shockwaves
import com.coden.starslicer.entities.spacecraft.SpaceCraft
import com.coden.starslicer.handlers.AttackerHandler
import com.coden.starslicer.gameplay.DifficultyController
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

    private lateinit var difficultyController: DifficultyController

    var randomPoint = generateRandomSpawnPoint(250f, 0f)

    val font = BitmapFont()

    var timePassed = 0f
    val bg = Texture("ui/backgrounds/background.png")

    var a = 90.0


    override fun show() {

        game.assets.finishLoading()// TODO: Initialize in starting screen

        Log.info("The screen is created",Log.LogType.SCREENS)
        Log.info("Size: $w x $h", Log.LogType.SCREENS)
        Log.info("xRatio: $xRatio, yRatio: $yRatio, sqRatio:$sqRatio", Log.LogType.SCREENS)

        data = EntityData(game.assets)

        hud = HUD(data)

        attackerHandler = AttackerHandler()
        powerUpHandler = PowerUpHandler()
        inputManager = InputManager(data)


        difficultyController = DifficultyController(data)
        difficultyController.addObserver(hud)

        add_observers(hud)
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
        difficultyController.update()
        update()

        // MAIN BATCH

        renderMainEntities()

        // HUD
        hud.render()

        // SWIPE RENDERER
        game.swipeRenderer.render(cam)

        // SHAPE RENDERER FOR DEBUG
        debugShapes(false)
    }

    fun renderMainEntities() {
        batch.begin()

        batch.draw(bg, 0f, 0f, w, h)

        renderScore(batch)
        renderFPS(batch)
        renderTimePassed(batch)
        difficultyController.render(batch, font)

        font.draw(batch, a.toString(), w-200, h-120)

        SpaceCraft.render(batch)
        attackerHandler.renderAll(batch)


        batch.end()
    }

    // UPDATE SECTION
    fun update() {

        timePassed += Gdx.graphics.deltaTime

        SpaceCraft.update(game.swipeRenderer.swipe)

        attackerHandler.updateAll()
        powerUpHandler.updateAll()


        updateInput()

        hud.update()

    }

    fun updateInput() {
        inputManager.debugSpawning()
        inputManager.updateSwiping()
        inputManager.updateClicking()

    }


    // DEBUG SECTION

    fun debugShapes(hitBoxRender: Boolean) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)

        if (SpaceCraft.isShielded){
            shapeRenderer.circle(SpaceCraft.x, SpaceCraft.y, SpaceCraft.shieldRadius)
        }

        for (shockwave in shockwaves) {
            if (shockwave.active){
                shapeRenderer.circle(spaceCraftX, spaceCraftY, shockwave.radius)
            }

        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            randomPoint = generateRandomSpawnPoint(250f, 0f)
            a = MathUtils.random(-45f,  45f).toDouble()
        }

        //val r = if (Math.tan(Math.toRadians(a+Math.atan(250/500.0))) <= 0) 250f/Math.sin(Math.toRadians(a)).toFloat() else 500f/Math.cos(Math.toRadians(a)).toFloat()
        val wi  = 1000f
        val he  = 500f
        var r = (he/2 - 10)/Math.sin(Math.toRadians(a)).toFloat()
        r = if (Math.abs(r) > dist2((he/2-2), wi/2-10)) (wi/2-10)/Math.cos(Math.toRadians(a)).toFloat() else r
       //Log.info("$r", Log.LogType.DEBUG)
       //renderVector(shapeRenderer, center, Vector2(1f,  0f).rotate(a.toFloat()).setLength(r))
        shapeRenderer.rect(340f ,170f, 400f, 200f)
        shapeRenderer.circle(centerX, centerY, 250f)

        val vec = center.cpy().sub(randomPoint).setLength(100f).rotate(a.toFloat())
        val shiftX = 10f
        val shiftY = 10f

        //val alpha = vec.angleRad().toDouble()
        //val shiftY = 10f
        //val dh = (540-200)/2 - randomPoint.y
        //val dh = -200-(540-200)/2 + randomPoint.y
        //val H = shiftY + dh
        //val radius = H/Math.sin(alpha)
        //Log.info("A:${Math.toDegrees(alpha)} DH:$dh, VEL:$vec POS:$randomPoint R:$radius", Log.LogType.DEBUG)
        //renderVector(shapeRenderer, randomPoint, vec)
        if (Gdx.input.isTouched){
            Log.info("${quater(Vector2(Gdx.input.getX().toFloat(), Gdx.input.getY().toFloat()))}", Log.LogType.DEBUG)
        }
        val alpha = Math.toRadians(vec.angle() + 90.0)
       // val dw = -400-(1080-400)/2 + randomPoint.x
        val dw = (1080-400)/2 - randomPoint.x
        val W = shiftX + dw
        val rad = W/Math.sin(alpha)
       // Log.info("A:${Math.toDegrees(alpha)} DH:$dw, VEL:$vec POS:$randomPoint R:$rad", Log.LogType.DEBUG)
        //renderVector(shapeRenderer, randomPoint, vec.setLength(radius(randomPoint, vec, (1080-400)/2f, (540-200)/2f, width = 400f, height = 200f).toFloat()))

        if (hitBoxRender) {
            renderRect(shapeRenderer, SpaceCraft.hitBox)
            renderCircle(shapeRenderer, SpaceCraft.hitSphere)


            for (attacker in attackers) {
                renderRect(shapeRenderer, attacker.hitBox)
                renderCircle(shapeRenderer, attacker.hitSphere)
            }

            for (hitBox in SpaceCraft.firstBlade.hitBoxes) {
                renderRect(shapeRenderer, hitBox)
            }
            for (hitBox in SpaceCraft.secondBlade.hitBoxes) {
                renderRect(shapeRenderer, hitBox)
            }

        }
        shapeRenderer.end()

    }
    fun renderFPS(batch: SpriteBatch) {
        font.draw(batch, Gdx.graphics.framesPerSecond.toString(), w-50, h-75)
    }

    fun renderTimePassed(batch: SpriteBatch){
        font.draw(batch, timePassed.toString(), w-75, h-100)
    }

    fun renderScore(batch: SpriteBatch){
        font.draw(batch, "Score: ${data.score}", w-75, h-150)
        font.draw(batch, "Coins: ${data.coins}", w-75, h-200)
    }

    fun renderVector(shapeRenderer: ShapeRenderer, pos: Vector2, vector: Vector2) {
        shapeRenderer.line(pos, pos.cpy().add(vector.cpy()))
    }

    fun renderRect(shapeRenderer: ShapeRenderer,rect: Rectangle) {
        shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height)
    }

    fun renderCircle(shapeRenderer: ShapeRenderer, circle: Circle){
        shapeRenderer.circle(circle.x, circle.y, circle.radius)
    }

    fun add_observers(hud: HUD){
        for (command in Locator.spawnServices.values){
            command.addObserver(hud)
        }
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

        Log.info("The screen is disposed", Log.LogType.SCREENS)
        batch.dispose()
        shapeRenderer.dispose()
        SpaceCraft.dispose()

    }

}