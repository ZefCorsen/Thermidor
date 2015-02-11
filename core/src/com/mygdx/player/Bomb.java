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
public class Bomb extends BodyModel{


    /**
     * Construit une bomb, ces limites, ses collision
     *
     * @param vectorPosition Vector2 de la position du joueur
     * @param world          WorldImp dans lequel sera créé le body du bomb
     * @param idPlayer       Id du joueur à ajouter
     */
    public Bomb(Vector2 vectorPosition, WorldImpl world, String idPlayer) {
        super(idPlayer);
        sprite = Assets.spriteBomb;
        sprite.setPosition(vectorPosition.x - (Assets.sprite.getWidth() / 2), vectorPosition.y - (Assets.sprite.getHeight() / 2) + sprite.getHeight() / 2);
        setBodyDef(world);
        setFixture();
        world.addBomb(this);
    }

    public Bomb(float x, float y, WorldImpl world, String idPlayer) {
        this(new Vector2(x, y), world, idPlayer);
    }

    public Bomb(WorldImpl world, String idPlayer) {
        this(world.getPlayerNS(idPlayer).getPosition(), world, idPlayer);
    }

    /**
     * Defini le corp de la bomb
     */
    private void setBodyDef(WorldImpl world) {
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
        fixtureDef.filter.maskBits = Assets.MASK_BULLET;
        Fixture fix = body.createFixture(fixtureDef);
        fix.setUserData(this);
        shape.dispose();
    }

}
