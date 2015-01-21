package com.mygdx.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.player.Player;

/**
 * Created by Jerem on 21/01/2015.
 */
public class MondeRenderTexture {

    private static final float CAMERA_WIDTH = 10f;
    private static final float CAMERA_HEIGHT = 7f;

    private World world;
    private OrthographicCamera cam;

    /**
     * ShapeRenderer permet de dessiner facilement les
     * formes de base
     * Sera utilisé pour des fins de débogage
     * *
     */
    ShapeRenderer debugRenderer = new ShapeRenderer();

    /**
     * Textures *
     */
    private Texture playerTexture;
    private Texture fontTexture;
    private Texture blockTexture;

    private SpriteBatch spriteBatch;
    private boolean debug = false;
    private int width;
    private int height;
    private float ppuX; // pixels par unité pour X
    private float ppuY;

    public void setSize(int w, int h) {
        this.width = w;
        this.height = h;
        ppuX = (float) width / CAMERA_WIDTH;
        ppuY = (float) height / CAMERA_HEIGHT;
    }

    public MondeRenderTexture(World world, boolean debug) {
        this.world = world;
        this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
        this.cam.update();
        this.debug = debug;
        spriteBatch = new SpriteBatch();
        loadTextures();
    }

    private void loadTextures() {
        playerTexture = new Texture(Gdx.files.internal("images/bonhomme.png"));
        blockTexture = new Texture(Gdx.files.internal("images/block.png"));
        fontTexture = new Texture(Gdx.files.internal("images/arriereplan.png"));

    }

    public void render() {
        spriteBatch.begin();
        drawBlocks();
        drawBob();
       // spriteBatch.draw(fontTexture,0,0);
        spriteBatch.end();
        if (debug)
            drawDebug();
    }

    private void drawBlocks() {
        for (Block block : world.getBlocks()) {
            spriteBatch.draw(
                    blockTexture,
                    block.getPosition().x * ppuX,
                    block.getPosition().y * ppuY,
                    Block.SIZE * ppuX,
                    Block.SIZE * ppuY);
        }
    }

    private void drawBob() {
        Player player = world.getPlayer();
        spriteBatch.draw(playerTexture, player.getPosition().x * ppuX, player.getPosition().y * ppuY, Player.SIZE * ppuX, Player.SIZE * ppuY);
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
        Player player = world.getPlayer();
        Rectangle rect = player.getBounds();
        float x1 = player.getPosition().x + rect.x;
        float y1 = player.getPosition().y + rect.y;
        debugRenderer.setColor(new Color(0, 1, 0, 1));
        debugRenderer.rect(x1, y1, rect.width, rect.height);
        debugRenderer.end();
    }
}