package com.mygdx.player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.models.Peer;
import com.mygdx.models.PositionMessage;
import com.mygdx.world.Assets;
import com.mygdx.world.WorldImpl;

/**
 * Created by Jerem on 04/02/2015.
 */
public class Player {

    private Sprite sprite;
    private Body body;
    private WorldImpl world;
    private Vector2 wantedPosition;
    private String id;

    /**
     *Construit un joueur, ces limites, ses collision
     * @param px position x
     * @param py position y
     * @param world WorldImp dans lequel sera créé le body du joueur
     */
    public Player(float px, float py, WorldImpl world) {
        this.world = world;
        sprite = Assets.sprite;
        sprite.setPosition(px, py);
        setBodyDef();
        setFixture();
        wantedPosition = body.getPosition();
        world.addPlayer(this);
    }

    /**
     * Construit un joueur, ces limites, ses collision
     * @param vectorPosition Vector2 de la position du joueur
     * @param world WorldImp dans lequel sera créé le body du joueur
     * @param id Id du joueur à ajouter
     */
    public Player(Vector2 vectorPosition, WorldImpl world, String id) {
        this.world = world;
        this.id = id;
        sprite = Assets.sprite;
        sprite.setPosition(vectorPosition.x,vectorPosition.y);
        setBodyDef();
        setFixture();
        wantedPosition = body.getPosition();
        world.addPlayer(this);
    }
    /**
     * Defini le corp du joueur
     */
    private void setBodyDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((sprite.getX() + sprite.getWidth() / 2),
                (sprite.getY() + sprite.getHeight() / 2));
        body = world.getWorld().createBody(bodyDef);
    }
    /**
     * Defini les collision,la masse, d'un joueur
     */
    private void setFixture() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight()
                / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.filter.categoryBits = Assets.PHYSICS_ENTITY;
        fixtureDef.filter.maskBits = Assets.WORLD_ENTITY | Assets.PHYSICS_ENTITY;
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public Body getBody() {
        return body;
    }
    /**
     * Donne la position voulu d'un joueur
     * @param wantedPosition
     */
    public void setWantedPosition(Vector2 wantedPosition) {
        this.wantedPosition = wantedPosition;
    }


    public void update(float delta) {
        float round = 2f;
        System.out.println("Update position");
        if (!body.getPosition().epsilonEquals(wantedPosition, round)) {
            System.out.println("Set position x :"+getPosition().x+" => "+wantedPosition.x);
            System.out.println("Set position y :"+getPosition().y+" => "+wantedPosition.y);

            body.setActive(true);
            body.setType(BodyDef.BodyType.DynamicBody);
            if (body.getPosition().x > wantedPosition.x) {
                if (body.getPosition().y > wantedPosition.y) {
                    body.setLinearVelocity(-Assets.PLAYER_SPEED, -Assets.PLAYER_SPEED);
                } else if (body.getPosition().y < wantedPosition.y) {
                    body.setLinearVelocity(-Assets.PLAYER_SPEED, Assets.PLAYER_SPEED);
                } else {
                    body.setLinearVelocity(-Assets.PLAYER_SPEED, 0f);
                }

            } else if (body.getPosition().x < wantedPosition.x) {
                if (body.getPosition().y > wantedPosition.y) {
                    body.setLinearVelocity(Assets.PLAYER_SPEED, -Assets.PLAYER_SPEED);
                } else if (body.getPosition().y < wantedPosition.y) {
                    body.setLinearVelocity(Assets.PLAYER_SPEED, Assets.PLAYER_SPEED);
                } else {
                    body.setLinearVelocity(Assets.PLAYER_SPEED, 0f);
                }
            }
        } else {
            body.setLinearVelocity(0f, 0f);
            body.setActive(false);
        }

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vector2 getPosition(){
        return body.getPosition();
    }
    public Peer getPeer(){
        return null;
    }
}
