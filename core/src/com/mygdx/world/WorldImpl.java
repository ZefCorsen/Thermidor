package com.mygdx.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.controller.ListenerClass;
import com.mygdx.models.BombMessage;
import com.mygdx.models.BulletMessage;
import com.mygdx.models.PlayerMessage;
import com.mygdx.player.BodyModel;
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
    private World world;
    private Body bodyEdgeScreen;
    private ArrayList<Player> players = new ArrayList();
    private ArrayList<Bomb> bombs = new ArrayList();
    private ArrayList<BodyModel> removeList = new ArrayList();
    private ArrayList<Bullet> bullets = new ArrayList();

    public static WorldImpl getInstance() {
        if (instance == null) {
            instance = new WorldImpl();
        }
        return instance;
    }

    public static void setInstance(WorldImpl worldImpl) {
        instance = worldImpl;
    }

    public static void setPlayers(PlayerMessage[] playersTab) {
        if (instance == null) {
            instance = new WorldImpl();
        }
        System.out.println("Size Player : " + playersTab.length);
        for (int i = 0; i < playersTab.length; i++) {
            PlayerMessage player = playersTab[i];
            new Player(player.getPosition(), instance, player.getIdPlayer(), player.getWantedPosition(), player.getOldLinareVelocity(), player.getLife(), player.getBulletPosition());
        }
    }

    public static void setBullets(BulletMessage[] bulletsTab) {
        if (instance == null) {
            instance = new WorldImpl();
        }
        for (BulletMessage bullet : bulletsTab) {
            new Bullet(bullet.getId(), instance);
        }
    }

    public static void setBombs(BombMessage[] bombsTab) {
        if (instance == null) {
            instance = new WorldImpl();
        }
        for (BombMessage bom : bombsTab) {
            new Bomb(instance, bom.getId());
        }
    }

    private WorldImpl() {
        world = new World(new Vector2(0f, 0f), true);
        world.setContactListener(new ListenerClass());
        setBody();
        setFixture();
        world.step(1f / 60f, 6, 2);
    }


    public World getWorld() {
        return world;
    }

    private void setBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);
        bodyEdgeScreen = world.createBody(bodyDef);

    }

    private void setFixture() {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = Assets.WORLD_ENTITY;
        fixtureDef.filter.maskBits = Assets.MASK_WORLD;

        final Vector2[] myCoordinates = {
                new Vector2((-w) / 2, (h) / 2),
                new Vector2((-w) / 2, (-h) / 2),

                new Vector2((w / 2) - (w / 8), (-h) / 2),
                new Vector2((w / 2) - (w / 8), (h) / 2),
                new Vector2((-w) / 2, (h) / 2)
        };

        ChainShape myChain = new ChainShape();
        myChain.createChain(myCoordinates);
        fixtureDef.shape = myChain;
        bodyEdgeScreen.createFixture(fixtureDef);
        bodyEdgeScreen.setUserData("World");
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


    public BombMessage[] getBombsTab() {
        BombMessage[] bombMessages = new BombMessage[bombs.size()];
        for (int i = 0; i < bombs.size(); i++) {
            bombMessages[i] = new BombMessage(bombs.get(i).getId());
        }
        return bombMessages;
    }

    public PlayerMessage[] getPlayersTab() {
        PlayerMessage[] playerMessages = new PlayerMessage[players.size()];
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            playerMessages[i] = new PlayerMessage(p.getPosition(), p.getWantedPosition(), p.getOldLinareVelocity(), p.getLife(), p.getBulletPosition(), p.getId());
        }
        return playerMessages;
    }

    public BulletMessage[] getBulletsTab() {
        BulletMessage[] bulletMessage = new BulletMessage[bullets.size()];
        for (int i = 0; i < bullets.size(); i++) {
            bulletMessage[i] = new BulletMessage(bullets.get(i).getId());
        }
        return bulletMessage;
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
        throw new Exception("Player not Found : " + id);
    }

    public Player getPlayerNS(String id) {
        for (Player player : players) {
            if (player.getId().equals(id)) {
                return player;
            }

        }
        return null;
    }

    public void deleteBullet(Bullet bullet) {
        removeList.add(bullet);
        bullets.remove(bullet);
    }

    public void addToRemoveList(BodyModel md) {
        removeList.add(md);
    }

    public void deletePlayer(Player player) {
        removeList.add(player);
        players.remove(player);
    }

    public void deleteBomb(Bomb bomb) {
        removeList.add(bomb);
        bombs.remove(bomb);
    }

    public ArrayList<BodyModel> getRemoveList() {
        return removeList;
    }


}
