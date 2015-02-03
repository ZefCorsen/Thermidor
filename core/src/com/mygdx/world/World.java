package com.mygdx.world;

/**
 * Created by Jerem on 21/01/2015.
 */


import com.badlogic.gdx.utils.Array;
import com.mygdx.models.Peer;
import com.mygdx.player.Player;


public class World {


    private Array<Block> blocks = new Array();
    private Array<Player> players = new Array();


    public Array<Block> getBlocks() {
        return blocks;
    }

    public Array<Player> getPlayers() {
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
