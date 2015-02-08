package com.mygdx.controller;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
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
        Object obA = contact.getFixtureA().getUserData();
        if (obA != null)
            System.out.println("System :" + obA.getClass());

        Object obB = contact.getFixtureB().getUserData();
        if (obB != null)
            System.out.println("System :" + obB.getClass());


        if (obA != null && obB != null) {
            if (obA instanceof Bullet && obB instanceof Bullet) {
                Bullet bulletA = (Bullet) obA;
                Bullet bulletB = (Bullet) obB;
                System.out.println("Bullet VS Bullet ");
                WorldImpl.getInstance().deleteBullet(bulletA);
                WorldImpl.getInstance().deleteBullet(bulletB);

            } else if (obA instanceof Bullet && obB instanceof Player) {
                Bullet bulletA = (Bullet) obA;
                Player player = (Player) obB;
                System.out.println("Bullet VS Player ");
                WorldImpl.getInstance().deleteBullet(bulletA);
                try {
                    player.removeLife();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (obB instanceof Bullet && obA instanceof Player) {
                Bullet bulletA = (Bullet) obB;
                Player player = (Player) obA;
                System.out.println("Player VS bullet ");
                WorldImpl.getInstance().deleteBullet(bulletA);
                try {
                    player.removeLife();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (obB instanceof Bullet) {
                Bullet pA = (Bullet) obB;
                //out.println("Bullet B: "+pA.getId());
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

