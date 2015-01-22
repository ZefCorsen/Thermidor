package com.mygdx.controller;

/**
 * Created by Jerem on 21/01/2015.
 */

import com.badlogic.gdx.math.Vector2;
import com.mygdx.player.Player;
import com.mygdx.world.Assets;
import com.mygdx.world.World;

import java.util.HashMap;
import java.util.Map;

public class MondeControlleur {


    enum Keys {
        LEFT, RIGHT, JUMP, FIRE, TOP, DOWN
    }

    private World world;
    private Player player;

    static Map<Keys, Boolean> keys =
            new HashMap<MondeControlleur.Keys, Boolean>();

    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.TOP, false);
        keys.put(Keys.DOWN, false);
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

    public void topPressed() {
        keys.get(keys.put(Keys.TOP, true));
    }

    public void downPressed() {
        keys.get(keys.put(Keys.DOWN, true));
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

    public void topReleased() {
        keys.get(keys.put(Keys.TOP, false));
    }

    public void downReleased() {
        keys.get(keys.put(Keys.DOWN, false));
    }

    public void jumpReleased() {
        keys.get(keys.put(Keys.JUMP, false));
    }

    public void fireReleased() {
        keys.get(keys.put(Keys.FIRE, false));
    }

    public void update(float delta) {
        player.update(delta);
    }

    public boolean setPlayerInPosition(float x, float y) {

        player.setWantedPosition(new Vector2(x,y));
        return true;
    }

    public void dontMove() {
        player.setState(Player.State.IDLE);
        player.getVelocity().y = 0;
        player.getAcceleration().y = 0;
        player.getVelocity().x = 0;
        player.getAcceleration().x = 0;

    }

}