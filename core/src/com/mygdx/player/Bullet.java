package com.mygdx.player;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.world.Assets;
import com.mygdx.world.WorldImpl;

/**
 * Created by Jerem on 04/02/2015.
 */
public class Bullet extends BodyModel {


    /**
     * Construit une bomb, ces limites, ses collision
     *
     * @param player Vector2 de la position du joueur
     * @param world  WorldImp dans lequel sera créé le body du bomb
     */
    public Bullet(Player player, WorldImpl world) {
        super(world,player.getId());
        fixtureDef = new FixtureDef();
        this.world = world;
        this.player = player;
        sprite = Assets.spriteBullet;
        sprite.setPosition(player.getPosition().x - (sprite.getWidth() / 2), player.getPosition().y - (sprite.getHeight() / 2));
        //sprite.setRotation(player.getBody().getAngle());
        setBodyDef();
        setFixture();
        body.setLinearVelocity(player.getOldLinareVelocity().x * 2, player.getOldLinareVelocity().y * 2);
        world.addBullet(this);
    }

    /**
     * Defini le corp de la bomb
     */
    private void setBodyDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
       // bodyDef.bullet = true;
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
