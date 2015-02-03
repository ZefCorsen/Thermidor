package com.mygdx.world;

/**
 * Created by Jerem on 21/01/2015.
 */


import com.mygdx.models.Peer;
import com.mygdx.player.Player;

import java.util.ArrayList;


public class World {


    private ArrayList<Player> players = new ArrayList<Player>();




    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(Peer peer) throws Exception {
        for(Player player : players){
            if (player.getPeer().equals(peer)){ return player;}
        }
        throw new Exception("Players missing");
    }

    public Player getPlayer(String id) throws Exception {
        for(Player player : players) {

                if (player.getId().equals(id)) {
                    return player;
                }
            }

        throw new Exception("Players missing");
    }

    public World() {
        createDemoWorld();
    }

    public void addPlayer(Player player){
        players.add(player);
    }
    private void createDemoWorld() {

    }
}
