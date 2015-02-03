package com.mygdx.player;

/**
 * Created by Jerem on 21/01/2015.
 */

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.models.Peer;
import com.mygdx.models.State;
import com.mygdx.world.Assets;

public class Player {


    public static final float SPEED = 4f;
    public static final float SIZE = 0.5f; // Demi unité



    private String id;
    private Peer peer;
    private   Vector2 position = new Vector2();
    private  Vector2 wantedPosition = new Vector2();
    private  Vector2 velocity = new Vector2();
    private   State state = State.IDLE;

    public Player(Vector2 position) {
        this.position = position;
        this.wantedPosition = position;

    }

    public Player(Vector2 position, String id) {
        this.id = id;
        this.position = position;
        this.wantedPosition = position;

    }
    public Player() {

    }


    public Vector2 getPosition() {
        return position;
    }


    public Vector2 getVelocity() {
        return velocity;
    }
    public String getId() {
        return id;
    }

    public Peer getPeer() {
        return peer;
    }



    public void setState(State newState) {
        this.state = newState;
    }

    public void setWantedPosition(Vector2 wantedPosition) {
        this.wantedPosition = wantedPosition;
    }




    public void update(float delta) {
        boolean sapX = false;
        boolean sapY = false;
        Assets.round = Player.SPEED * delta;
        if (!position.epsilonEquals(wantedPosition, Assets.round)) {
            if (Math.abs(position.x - wantedPosition.x) > Assets.round) {
                if (position.x > wantedPosition.x) {
                    setState(State.WALKING);
                    getVelocity().x = -Player.SPEED;

                } else if (position.x < wantedPosition.x) {
                    setState(State.WALKING);
                    getVelocity().x = Player.SPEED;
                }
            }else{
                sapX = true;
            }

            if (Math.abs(position.y - wantedPosition.y) > Assets.round) {
                if (position.y > wantedPosition.y) {
                    setState(State.WALKING);
                    getVelocity().y = -Player.SPEED;
                } else if (position.y < wantedPosition.y) {
                    setState(State.WALKING);
                    getVelocity().y = Player.SPEED;
                }
            }else{
                sapY = true;
            }

            position.add(new Vector2(velocity.x * delta, velocity.y * delta));
        }
        if(sapX)position.x = wantedPosition.x;
        if(sapY)position.y = wantedPosition.y;
    }
}