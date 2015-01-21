package com.mygdx.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.player.Player;

/**
 * Created by Jerem on 21/01/2015.
 */
public class MondeRender {

    private World world;
    private OrthographicCamera cam;


    ShapeRenderer debugRenderer = new ShapeRenderer();

    public MondeRender(World world) {
        this.world = world;
        this.cam = new OrthographicCamera(20, 14);
        this.cam.position.set(5, 3.5f, 0);
        this.cam.update();
    }

    public void render() {
        // Rendu des blocs
        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Block block : world.getBlocks()) {
            Rectangle rect = block.getBounds();
            float x1 = block.getPosition().x;
            float y1 = block.getPosition().y;
            debugRenderer.setColor(new Color(1, 0, 0, 1));
            debugRenderer.rect(x1, y1, rect.width, rect.height);
        }
        // Rendu de Bob
        Player player = world.getPlayer();
        Rectangle rect = player.getBounds();
        float x1 = player.getPosition().x;
        float y1 = player.getPosition().y;
        debugRenderer.setColor(new Color(0, 1, 0, 1));
        debugRenderer.rect(x1, y1, rect.width, rect.height);
        debugRenderer.end();
    }
}
