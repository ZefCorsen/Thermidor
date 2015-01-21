package com.mygdx.player;

/**
 * Created by Jerem on 21/01/2015.
 */

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {




    public static final float SPEED = 4f;
    static final float JUMP_VELOCITY = 1f;
    public static final float SIZE = 0.5f; // Demi unité

    Vector2 position = new Vector2();
    Vector2 acceleration = new Vector2();
    Vector2 velocity = new Vector2();
    Rectangle bounds = new Rectangle();
    State state = State.IDLE;
    boolean facingLeft = true;

    public Player(Vector2 position) {
        this.position = position;
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
        IDLE, WALKING, JUMPING, DYING
    }

    public void setState(State newState) {
        this.state = newState;
    }

    public void update(float delta) {
        position.add(new Vector2(velocity.x*delta, velocity.y*delta));
    //    position.add(velocity.mul(delta));
    }
}