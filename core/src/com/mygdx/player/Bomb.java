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
public class Bomb {

    private Sprite sprite;
    private Body body;
    private WorldImpl world;
    private String idPlayer;
    private FixtureDef fixtureDef;

    /**
     * Construit une bomb, ces limites, ses collision
     *
     * @param vectorPosition Vector2 de la position du joueur
     * @param world          WorldImp dans lequel sera créé le body du bomb
     * @param idPlayer       Id du joueur à ajouter
     */
    public Bomb(Vector2 vectorPosition, WorldImpl world, String idPlayer) {
        fixtureDef = new FixtureDef();
        this.world = world;
        this.idPlayer = idPlayer;
        sprite = Assets.spriteBomb;
        sprite.setPosition(vectorPosition.x - (Assets.sprite.getWidth() / 2), vectorPosition.y - (Assets.sprite.getHeight() / 2) + sprite.getHeight() / 2);
        setBodyDef();
        setFixture();
        world.addBomb(this);
    }

    public Bomb(float x, float y, WorldImpl world, String idPlayer) {
        this(new Vector2(x, y), world, idPlayer);
    }

    /**
     * Defini le corp de la bomb
     */
    private void setBodyDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((sprite.getX() + sprite.getWidth() / 2),
                (sprite.getY() + sprite.getHeight() / 2));
        body = world.getWorld().createBody(bodyDef);
    }

    /**
     * Defini les collision,la masse, d'une bomb
     */
    private void setFixture() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight()
                / 2);
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.filter.categoryBits = Assets.BOMB_ENTITY;
        fixtureDef.filter.maskBits = Assets.WORLD_ENTITY | Assets.BOMB_ENTITY | Assets.PHYSICS_ENTITY;
        Fixture fix = body.createFixture(fixtureDef);
        fix.setUserData(this);
        shape.dispose();
    }

    public Body getBody() {
        return body;
    }

    /**
     * Met à jour la velocité d'un joueur si la position voulu n'est pas encore atteinte
     */
   /* public void update(float delta) {
        float round = 0.02f;//((1f/60f) * Assets.PLAYER_SPEED);

        if (!body.getPosition().epsilonEquals(wantedPosition, round)) {
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
                    }else{
                        body.setLinearVelocity(-Assets.PLAYER_SPEED,0f);
                    }

                } else if (body.getPosition().x < wantedPosition.x) {
                    if (Math.abs(body.getPosition().y - wantedPosition.y) > round) {
                        if (body.getPosition().y > wantedPosition.y) {
                            body.setLinearVelocity(Assets.PLAYER_SPEED, -Assets.PLAYER_SPEED);
                        } else if (body.getPosition().y < wantedPosition.y) {
                            body.setLinearVelocity(Assets.PLAYER_SPEED, Assets.PLAYER_SPEED);
                        }                     }
                    else{
                        body.setLinearVelocity(Assets.PLAYER_SPEED,0f);
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

    }*/
    public String getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }
}
