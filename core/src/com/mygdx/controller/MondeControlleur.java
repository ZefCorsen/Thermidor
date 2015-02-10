package com.mygdx.controller;

/**
 * Created by Jerem on 21/01/2015.
 */

import com.badlogic.gdx.math.Vector2;
import com.mygdx.models.PositionMessage;
import com.mygdx.player.BodyModel;
import com.mygdx.player.Bomb;
import com.mygdx.player.Bullet;
import com.mygdx.player.Player;
import com.mygdx.world.Assets;
import com.mygdx.world.WorldImpl;

public class MondeControlleur {
    private static MondeControlleur instance;
    private WorldImpl world;

    public static MondeControlleur getInstance() {
        if (instance == null) {
            instance = new MondeControlleur();
        }
        return instance;
    }

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

    public void addPlayerToWorld(String idPlayer) throws Exception {
        for (Player playerExist : world.getPlayers()) {
            if (playerExist.getId().equals(idPlayer)) {
                throw new Exception("Player existe déjà");
            }
        }
        new Player(10/ Assets.PIXELS_TO_METERS,10/Assets.PIXELS_TO_METERS, world,idPlayer);
    }
}