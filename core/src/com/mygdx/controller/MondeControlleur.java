package com.mygdx.controller;

/**
 * Created by Jerem on 21/01/2015.
 */

import com.mygdx.player.Player;
import com.mygdx.world.World;

import java.util.HashMap;
import java.util.Map;

public class MondeControlleur {


    enum Keys {
        LEFT, RIGHT, JUMP, FIRE
    }

    private World world;
    private Player player;

    static Map<Keys, Boolean> keys =
            new HashMap<MondeControlleur.Keys, Boolean>();

    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.JUMP, false);
        keys.put(Keys.FIRE, false);
    }

    ;

    public MondeControlleur(World world) {
        this.world = world;
        this.player = world.getPlayer();
    }


    public void leftPressed() {
        keys.get(keys.put(Keys.LEFT, true));
    }

    public void rightPressed() {
        keys.get(keys.put(Keys.RIGHT, true));
    }

    public void jumpPressed() {
        keys.get(keys.put(Keys.JUMP, true));
    }

    public void firePressed() {
        keys.get(keys.put(Keys.FIRE, false));
    }

    public void leftReleased() {
        keys.get(keys.put(Keys.LEFT, false));
    }

    public void rightReleased() {
        keys.get(keys.put(Keys.RIGHT, false));
    }

    public void jumpReleased() {
        keys.get(keys.put(Keys.JUMP, false));
    }

    public void fireReleased() {
        keys.get(keys.put(Keys.FIRE, false));
    }

    public void update(float delta) {
        processInput();
        player.update(delta);
    }

    private void processInput() {
        if (keys.get(Keys.LEFT)) {
            player.setFacingLeft(true);
            player.setState(Player.State.WALKING);
            player.getVelocity().x = -Player.SPEED;
        }

        if (keys.get(Keys.RIGHT)) {
            player.setFacingLeft(false);
            player.setState(Player.State.WALKING);
            player.getVelocity().x = Player.SPEED;
        }

        if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
                (!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
            player.setState(Player.State.IDLE);
            player.getAcceleration().x = 0;
            player.getVelocity().x = 0;
        }
    }
}