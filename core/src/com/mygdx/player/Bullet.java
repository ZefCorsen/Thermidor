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
        super(player.getId());
        fixtureDef = new FixtureDef();
        sprite = Assets.spriteBullet;
        float bX=0,bY=0;
        switch (player.getBulletPosition()) {
            case 0: {
                bX=-Assets.sprite.getWidth()/1.75f;
                bY=0;
                break;
            }
            case 1: {
                bX=-Assets.sprite.getWidth()/1.75f;
                bY=Assets.sprite.getHeight()/1.75f;
                break;
            }
            case 2: {
                bX=0;
                bY=Assets.sprite.getHeight()/1.75f;
                break;
            }
            case 3: {
                bX=Assets.sprite.getWidth()/1.75f;
                bY=Assets.sprite.getHeight()/1.75f;
                break;
            }
            case 4: {
                bX=Assets.sprite.getWidth()/1.75f;
                bY=0;
                break;
            }
            case 5: {
                bX=Assets.sprite.getWidth()/1.75f;
                bY=-Assets.sprite.getHeight()/1.75f;
                break;
            }
            case 6: {
                bX=0;
                bY=-Assets.sprite.getHeight()/1.75f;
                break;
            }
            case 7: {
                bX=-Assets.sprite.getWidth()/1.75f;
                bY=-Assets.sprite.getHeight()/1.75f;
                break;
            }
        }


        sprite.setPosition(player.getPosition().x+bX, player.getPosition().y+bY);
        setBodyDef(world);
        setFixture();
        body.setLinearVelocity(player.getOldLinareVelocity().x * 2.5f, player.getOldLinareVelocity().y * 2.5f);
        world.addBullet(this);
    }

    public Bullet(String idPlayer , WorldImpl world){
        this(world.getPlayerNS(idPlayer),world);
    }
    /**
     * Defini le corp de la bomb
     */
    private void setBodyDef(WorldImpl world) {
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
