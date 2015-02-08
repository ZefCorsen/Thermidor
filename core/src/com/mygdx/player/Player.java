package com.mygdx.player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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
    private Vector2 oldLinareVelocity;
    private String id;
    private FixtureDef fixtureDef;


    /**
     * Construit un joueur, ces limites, ses collision
     *
     * @param px    position x
     * @param py    position y
     * @param world WorldImp dans lequel sera créé le body du joueur
     */
    public Player(float px, float py, WorldImpl world, String id) {
        fixtureDef = new FixtureDef();
        this.id = id;
        this.world = world;
        sprite = Assets.sprite;
        sprite.setPosition(px, py);
        setBodyDef();
        setFixture();
        wantedPosition = body.getPosition();
        oldLinareVelocity = new Vector2();
        world.addPlayer(this);
    }

    /**
     * Construit un joueur, ces limites, ses collision
     *
     * @param vectorPosition Vector2 de la position du joueur
     * @param world          WorldImp dans lequel sera créé le body du joueur
     * @param id             Id du joueur à ajouter
     */
    public Player(Vector2 vectorPosition, WorldImpl world, String id) {
        this(vectorPosition.x, vectorPosition.y, world, id);

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

        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.filter.categoryBits = Assets.PHYSICS_ENTITY;
        fixtureDef.filter.maskBits = Assets.WORLD_ENTITY | Assets.PHYSICS_ENTITY | Assets.BOMB_ENTITY;

        Fixture fix = body.createFixture(fixtureDef);
        fix.setUserData(this);

        shape.dispose();
    }

    public Body getBody() {
        return body;
    }

    /**
     * Donne la position voulu d'un joueur
     *
     * @param wantedPosition
     */
    public void setWantedPosition(Vector2 wantedPosition) {
        this.wantedPosition = wantedPosition;
    }

    /**
     * Met à jour la velocité d'un joueur si la position voulu n'est pas encore atteinte
     *
     * @param delta
     */
    public void update(float delta) {
        float round = 0.02f;//((1f/60f) * Assets.PLAYER_SPEED);

        if (!body.getPosition().epsilonEquals(wantedPosition, round)) {
            oldLinareVelocity = body.getLinearVelocity();
            if (Math.abs(body.getPosition().x - wantedPosition.x) > round) {
                body.setActive(true);
                body.setType(BodyDef.BodyType.DynamicBody);
                if (body.getPosition().x > wantedPosition.x) {
                    if (Math.abs(body.getPosition().y - wantedPosition.y) > round) {
                        if (body.getPosition().y > wantedPosition.y) {
                            body.setLinearVelocity(-Assets.PLAYER_SPEED, -Assets.PLAYER_SPEED);
                        } else if (body.getPosition().y < wantedPosition.y) {
                            body.setLinearVelocity(-Assets.PLAYER_SPEED, Assets.PLAYER_SPEED);
                        }
                    } else {
                        body.setLinearVelocity(-Assets.PLAYER_SPEED, 0f);
                    }

                } else if (body.getPosition().x < wantedPosition.x) {
                    if (Math.abs(body.getPosition().y - wantedPosition.y) > round) {
                        if (body.getPosition().y > wantedPosition.y) {
                            body.setLinearVelocity(Assets.PLAYER_SPEED, -Assets.PLAYER_SPEED);
                        } else if (body.getPosition().y < wantedPosition.y) {
                            body.setLinearVelocity(Assets.PLAYER_SPEED, Assets.PLAYER_SPEED);
                        }
                    } else {
                        body.setLinearVelocity(Assets.PLAYER_SPEED, 0f);
                    }
                }
            } else {
                if (body.getPosition().y > wantedPosition.y) {
                    body.setLinearVelocity(0f, -Assets.PLAYER_SPEED);
                } else if (body.getPosition().y < wantedPosition.y) {
                    body.setLinearVelocity(0f, Assets.PLAYER_SPEED);
                } else {
                    body.setLinearVelocity(-Assets.PLAYER_SPEED, 0f);
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

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public boolean isSameFixture(Fixture fix) {
        return body.getFixtureList().get(0).equals(fix);
    }

    public Vector2 getOldLinareVelocity() {
        return oldLinareVelocity;
    }


}
