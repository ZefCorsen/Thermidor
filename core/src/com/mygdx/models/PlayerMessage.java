package com.mygdx.models;

import com.badlogic.gdx.math.Vector2;

import java.net.InetAddress;

/**
 * Created by Jerem on 10/02/2015.
 */
public class PlayerMessage {

    private Vector2 position;
    private Vector2 wantedPosition;
    private Vector2 oldLinareVelocity;
    private int life = 6;
    private int bulletPosition;
    private String idPlayer;

    public PlayerMessage() {
    }

    public PlayerMessage(Vector2 position, Vector2 wantedPosition, Vector2 oldLinareVelocity, int life, int bulletPosition, String idPlayer) {
        this.position = position;
        this.wantedPosition = wantedPosition;
        this.oldLinareVelocity = oldLinareVelocity;
        this.life = life;
        this.bulletPosition = bulletPosition;
        this.idPlayer = idPlayer;

    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getWantedPosition() {
        return wantedPosition;
    }

    public Vector2 getOldLinareVelocity() {
        return oldLinareVelocity;
    }

    public int getLife() {
        return life;
    }

    public int getBulletPosition() {
        return bulletPosition;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

}
