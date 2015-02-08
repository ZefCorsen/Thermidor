package com.mygdx.controller;

/**
 * Created by Jerem on 21/01/2015.
 */

import com.badlogic.gdx.math.Vector2;
import com.mygdx.player.BodyModel;
import com.mygdx.player.Bomb;
import com.mygdx.player.Bullet;
import com.mygdx.player.Player;
import com.mygdx.world.WorldImpl;

public class MondeControlleur {


    private WorldImpl world;


    public MondeControlleur(WorldImpl world) {
        this.world = world;
    }

    public MondeControlleur() {
        this.world = WorldImpl.getInstance();
    }

    public void update(float delta) {
        if (!world.getWorld().isLocked() && !world.getRemoveList().isEmpty()) {
            for (BodyModel model : world.getRemoveList()) {
                world.getWorld().destroyBody(model.getBody());
            }
            world.getRemoveList().clear();
        }
        for (Player player : world.getPlayers()) {
            player.update(delta);
        }
    }

    public boolean createBomb(String id) {
        try {
            new Bomb(world.getPlayer(id).getPosition(), world, id);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return true;
    }

    public boolean createBullet(String id) {
        try {
            new Bullet(world.getPlayer(id), world);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return true;
    }

    public boolean setPlayerInPosition(String id, float x, float y) {
        try {
            world.getPlayer(id).setWantedPosition(new Vector2(x, y));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void addPlayerToWorld(Player player) throws Exception {
        for (Player playerExist : world.getPlayers()) {
            if (playerExist.getId().equals(player.getId())) {
                throw new Exception("Player existe déjà");
            }
        }
        new Player(player.getPosition(), world, player.getId());
    }
}