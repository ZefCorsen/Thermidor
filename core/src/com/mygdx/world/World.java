package com.mygdx.world;

/**
 * Created by Jerem on 21/01/2015.
 */


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.player.Player;

public class World {


    private Array<Block> blocks = new Array();

    private Player player;

    // Getters -----------
    public Array<Block> getBlocks() {
        return blocks;
    }

    public Player getPlayer() {
        return player;
    }
    // --------------------

    public World() {
        createDemoWorld();
    }

    private void createDemoWorld() {
        player = new Player(new Vector2(0, 1));

        for (int col = 0; col < 10; col++) {
            blocks.add(new Block(new Vector2(col, 0)));
            blocks.add(new Block(new Vector2(col, 6)));
            if (col > 2)
                blocks.add(new Block(new Vector2(col, 1)));
        }

        blocks.add(new Block(new Vector2(9, 2)));
        blocks.add(new Block(new Vector2(9, 3)));
        blocks.add(new Block(new Vector2(9, 4)));
        blocks.add(new Block(new Vector2(9, 5)));

        blocks.add(new Block(new Vector2(6, 3)));
        blocks.add(new Block(new Vector2(6, 4)));
        blocks.add(new Block(new Vector2(6, 5)));
    }
}
