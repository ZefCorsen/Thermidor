package com.mygdx.player;

/**
 * Created by Jerem on 21/01/2015.
 */

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.world.Assets;

public class Player {


    public static final float SPEED = 4f;
    public static final float SIZE = 0.5f; // Demi unité

    Vector2 position = new Vector2();
    Vector2 wantedPosition = new Vector2();
    Vector2 acceleration = new Vector2();
    Vector2 velocity = new Vector2();
    Rectangle bounds = new Rectangle();
    State state = State.IDLE;
    boolean facingLeft = true;

    public Player(Vector2 position) {
        this.position = position;
        this.wantedPosition = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;
        this.bounds.x = this.position.x;
        this.bounds.y = this.position.y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setFacingLeft(boolean facingLeft) {
        this.facingLeft = facingLeft;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public enum State {
        IDLE, WALKING, DYING
    }

    public void setState(State newState) {
        this.state = newState;
    }

    public Vector2 getWantedPosition() {
        return wantedPosition;
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
                    setState(Player.State.WALKING);
                    getVelocity().x = -Player.SPEED;

                } else if (position.x < wantedPosition.x) {
                    setState(Player.State.WALKING);
                    getVelocity().x = Player.SPEED;
                }
            }else{
                sapX = true;
            }

            if (Math.abs(position.y - wantedPosition.y) > Assets.round) {
                if (position.y > wantedPosition.y) {
                    setState(Player.State.WALKING);
                    getVelocity().y = -Player.SPEED;
                } else if (position.y < wantedPosition.y) {
                    setState(Player.State.WALKING);
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