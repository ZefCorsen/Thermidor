package com.mygdx.controller;

/**
 * Created by Jerem on 21/01/2015.
 */

import com.badlogic.gdx.math.Vector2;
import com.mygdx.player.BodyModel;
import com.mygdx.player.Bomb;
import com.mygdx.player.Bullet;
import com.mygdx.player.Player;
import com.mygdx.world.Assets;
import com.mygdx.world.WorldImpl;

import java.net.InetAddress;

public class MondeControlleur {
    private static MondeControlleur instance;

    public static MondeControlleur getInstance() {
        if (instance == null) {
            instance = new MondeControlleur();
        }
        return instance;
    }

    public MondeControlleur() {
        WorldImpl.getInstance();
    }

    public void update(float delta) {
        if (!WorldImpl.getInstance().getWorld().isLocked() && !WorldImpl.getInstance().getRemoveList().isEmpty()) {
            for (BodyModel model : WorldImpl.getInstance().getRemoveList()) {
                WorldImpl.getInstance().getWorld().destroyBody(model.getBody());
            }
            WorldImpl.getInstance().getRemoveList().clear();
        }
        for (Player player : WorldImpl.getInstance().getPlayers()) {
            player.update(delta);
        }
    }

    public boolean createBomb(String id) {
        try {
            new Bomb(WorldImpl.getInstance().getPlayer(id).getPosition(), WorldImpl.getInstance(), id);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return true;
    }

    public boolean createBullet(String id) {
        try {
            new Bullet(WorldImpl.getInstance().getPlayer(id), WorldImpl.getInstance());
        } catch (Exception e) {
            e.printStackTrace();

        }
        return true;
    }

    public boolean setPlayerInPosition(String id, float x, float y) {
        try {
            WorldImpl.getInstance().getPlayer(id).setWantedPosition(new Vector2(x, y));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void addPlayerToWorld(String idPlayer, InetAddress addr) throws Exception {
        for (Player playerExist : WorldImpl.getInstance().getPlayers()) {
            if (playerExist.getId().equals(idPlayer)) {
                throw new Exception("Player existe déjà : " + idPlayer);
            }
        }
        Player p = new Player(10 / Assets.PIXELS_TO_METERS, 10 / Assets.PIXELS_TO_METERS, WorldImpl.getInstance(), idPlayer);
    }

    public void deletePlayerToWorld(String idPlayer) {

        try {
            WorldImpl.getInstance().deletePlayer(WorldImpl.getInstance().getPlayer(idPlayer));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}