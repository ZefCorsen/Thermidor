package com.mygdx.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.player.Player;

import java.util.ArrayList;

/**
 * Created by Jerem on 04/02/2015.
 * Contient l'ensemble des informations sur la partie
 */
public class WorldImpl {
    float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();
    private FixtureDef fixtureDef;
    private ChainShape myChain;
    private World world;
    private Body bodyEdgeScreen;
    private BodyDef bodyDef;


    private ArrayList<Player> players = new ArrayList();

    public WorldImpl() {
        world = new World(new Vector2(0.2f, 0), false);
        setBody();
        setFixture();
        setEdge();
        world.step(1f / 60f, 6, 2);
    }

    public World getWorld() {
        return world;
    }

    private void setBody() {
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);
    }

    private void setFixture() {
        fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = Assets.WORLD_ENTITY;
        fixtureDef.filter.maskBits = Assets.WORLD_ENTITY | Assets.PHYSICS_ENTITY;
    }

    private void setEdge() {
        final Vector2[] myCoordinates = {
                new Vector2((-w) / 2, (h) / 2),
                new Vector2((-w) / 2, (-h) / 2),

                new Vector2((w) / 2, (-h) / 2),
                new Vector2((w) / 2, (h) / 2),
                new Vector2((-w) / 2, (h) / 2)
        };

        myChain = new ChainShape();
        myChain.createChain(myCoordinates);
        fixtureDef.shape = myChain;

        bodyEdgeScreen = world.createBody(bodyDef);
        bodyEdgeScreen.createFixture(fixtureDef);
        myChain.dispose();

    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(String id) throws Exception {
        if(id == null ||id.isEmpty())throw new Exception("Id passer vide");
        System.out.println(players.size());
        for (Player player : players) {
            System.out.println(player.getId());
            if (player.getId().equals(id)) {
                System.out.println("return player :" + player.getId());
                return player;
            }
        }
        throw new Exception("Player not Found");
    }
}
