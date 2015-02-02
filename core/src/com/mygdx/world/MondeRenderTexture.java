package com.mygdx.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.player.Player;

/**
 * Created by Jerem on 21/01/2015.
 */
public class MondeRenderTexture {


    private World world;
    private OrthographicCamera cam;

    /**
     * debug
     */
    ShapeRenderer debugRenderer = new ShapeRenderer();


    private SpriteBatch spriteBatch;
    private boolean debug = false;
    private int width;
    private int height;
    public float ppuX; // pixels par unité pour X
    public float ppuY; // pixels par unité pour Y

    public void setSize(int w, int h) {
        this.width = w;
        this.height = h;
        ppuX = (float) width / Assets.CAMERA_WIDTH;
        ppuY = (float) height / Assets.CAMERA_HEIGHT;
    }

    public MondeRenderTexture(World world, boolean debug) {
        this.world = world;
        this.cam = new OrthographicCamera(Assets.CAMERA_WIDTH, Assets.CAMERA_HEIGHT);
        this.cam.position.set(Assets.CAMERA_WIDTH / 2f, Assets.CAMERA_HEIGHT / 2f, 0);
        this.cam.update();
        this.debug = debug;
        spriteBatch = new SpriteBatch();
    }

    public void render() {
        spriteBatch.begin();
        spriteBatch.draw(Assets.background,0,0,width,height);
        drawBlocks();
        drawPlayer();
        spriteBatch.end();
        if (debug)
            drawDebug();
    }

    private void drawBlocks() {
        for (Block block : world.getBlocks()) {
            spriteBatch.draw(
                    Assets.block,
                    block.getPosition().x * ppuX,
                    block.getPosition().y * ppuY,
                    Block.SIZE * ppuX,
                    Block.SIZE * ppuY);
        }
    }

    private void drawPlayer() {

        for (Player player : world.getPlayers()) {
            spriteBatch.draw(Assets.player, player.getPosition().x * ppuX, player.getPosition().y * ppuY, Player.SIZE * ppuX, Player.SIZE * ppuY);
        }
    }

    private void drawDebug() {
        // Démarrage du renderer
        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // render blocks
        for (Block block : world.getBlocks()) {
            Rectangle rect = block.getBounds();
            float x1 = block.getPosition().x + rect.x;
            float y1 = block.getPosition().y + rect.y;
            debugRenderer.setColor(new Color(1, 0, 0, 1));
            debugRenderer.rect(x1, y1, rect.width, rect.height);
        }
        // Rendre Bob
        for (Player player : world.getPlayers()) {
            Rectangle rect = player.getBounds();
            float x1 = player.getPosition().x + rect.x;
            float y1 = player.getPosition().y + rect.y;
            debugRenderer.setColor(new Color(0, 1, 0, 1));
            debugRenderer.rect(x1, y1, rect.width, rect.height);
            debugRenderer.end();
        }


    }
}