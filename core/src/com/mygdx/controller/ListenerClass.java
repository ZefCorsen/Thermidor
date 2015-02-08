package com.mygdx.controller;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.player.Bomb;
import com.mygdx.player.Bullet;
import com.mygdx.player.Player;
import com.mygdx.world.WorldImpl;


/**
 * Created by Jerem on 06/02/2015.
 */
public class ListenerClass implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        System.out.println("Things touch !!!");
        Object obA= contact.getFixtureA().getUserData();
        Object obB= contact.getFixtureB().getUserData();

        if(obA!= null && obB != null){
            if(obA instanceof Bullet && obB instanceof Bullet){
                Bullet bulletA = (Bullet) obA;
                Bullet bulletB = (Bullet) obB;
                System.out.println("Bullet A : Bullet B ");
                WorldImpl.getInstance().deleteBullet(bulletA);
                WorldImpl.getInstance().deleteBullet(bulletB);
            }
            if(obB instanceof Bullet){
                Bullet pA = (Bullet) obB;
                System.out.println("Bullet B: "+pA.getId());
            }
        }



    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

