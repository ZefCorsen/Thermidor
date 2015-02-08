package com.mygdx.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.controller.ListenerClass;
import com.mygdx.player.Bomb;
import com.mygdx.player.Bullet;
import com.mygdx.player.Player;

import java.util.ArrayList;

/**
 * Created by Jerem on 04/02/2015.
 * Contient l'ensemble des informations sur la partie
 */

public class WorldImpl {
    private static WorldImpl instance;
    float w = Assets.ppuX / Assets.PIXELS_TO_METERS;
    float h = Assets.ppuY / Assets.PIXELS_TO_METERS;
    private FixtureDef fixtureDef;
    private ChainShape myChain;
    private World world;
    private Body bodyEdgeScreen;
    private BodyDef bodyDef;
    private ArrayList<Player> players = new ArrayList();
    private ArrayList<Bomb> bombs = new ArrayList();

private ArrayList<Bullet> bullets = new ArrayList();


    public static WorldImpl getInstance() {
        if(instance == null){
            instance = new WorldImpl();
        }
        return instance;
    }
   public static void setInstance(WorldImpl worldImpl){
       instance=worldImpl;
   }

    private WorldImpl() {

        world = new World(new Vector2(0f, 0f), false);
        world.setContactListener(new ListenerClass());
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
        bodyEdgeScreen = world.createBody(bodyDef);
    }

    private void setFixture() {
        fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = Assets.WORLD_ENTITY;
        fixtureDef.filter.maskBits = Assets.PHYSICS_ENTITY;
    }

    private void setEdge() {
        final Vector2[] myCoordinates = {
                new Vector2((-w) / 2, (h) / 2),
                new Vector2((-w) / 2, (-h) / 2),

                new Vector2((w / 2) - (w / 8), (-h) / 2),
                new Vector2((w / 2) - (w / 8), (h) / 2),
                new Vector2((-w) / 2, (h) / 2)
        };

        myChain = new ChainShape();
        myChain.createChain(myCoordinates);
        fixtureDef.shape = myChain;
        bodyEdgeScreen.createFixture(fixtureDef);
        myChain.dispose();

    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
    }

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }


    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }



    public Player getPlayer(String id) throws Exception {
        if (id == null || id.isEmpty()) throw new Exception("Id passer vide");
        for (Player player : players) {
            if (player.getId().equals(id)) {
                return player;
            }
        }
        throw new Exception("Player not Found");
    }

}
