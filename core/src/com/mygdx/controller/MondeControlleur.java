package com.mygdx.controller;

/**
 * Created by Jerem on 21/01/2015.
 */

import com.badlogic.gdx.math.Vector2;
import com.mygdx.models.Peer;
import com.mygdx.player.Player;
import com.mygdx.world.World;

public class MondeControlleur {


    private World world;


    public MondeControlleur(World world) {
        this.world = world;
    }

    public void update(float delta) {
        for(Player player : world.getPlayers()) {
            player.update(delta);
        }
    }

    public boolean setPlayerInPosition(String id ,float x, float y) {
        try {
            world.getPlayer(id).setWantedPosition(new Vector2(x,y));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean setPlayerInPosition(Peer peer ,float x, float y) {
        try {
            world.getPlayer(peer).setWantedPosition(new Vector2(x,y));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public void addPlayerToWorld(Player player){
        world.addPlayer(player);
    }


}