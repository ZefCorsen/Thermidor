package com.mygdx.controller;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.player.Bullet;
import com.mygdx.player.Player;
import com.mygdx.world.WorldImpl;

import java.util.Objects;


/**
 * Created by Jerem on 06/02/2015.
 */
public class ListenerClass implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
   //     System.out.println("Things touch !!!");
        Object obA = contact.getFixtureA().getUserData();
    //    if (obA != null)
    //        System.out.println("System A:" + obA.getClass());

        Object obB = contact.getFixtureB().getUserData();
   //     if (obB != null)
     //       System.out.println("System B:" + obB.getClass());


        if (obA == null && obB != null && obB instanceof Bullet) {
    //        System.out.println("Bullet VS World");
            Bullet bulletB = (Bullet) obB;
            WorldImpl.getInstance().deleteBullet(bulletB);

        }

        if (obA != null && obB != null) {
        //    System.out.println("idA : " + obA + " ,idB : " + obB);
            if (obA instanceof Bullet && obB instanceof Bullet) {
                Bullet bulletA = (Bullet) obA;
                Bullet bulletB = (Bullet) obB;
          //      System.out.println("Bullet VS Bullet ");
                WorldImpl.getInstance().deleteBullet(bulletA);
                WorldImpl.getInstance().deleteBullet(bulletB);

            } else if (obA instanceof Bullet && obB instanceof Player) {
                Bullet bulletA = (Bullet) obA;
                Player player = (Player) obB;
                if (!bulletA.getId().equals(player.getId())) {
                    WorldImpl.getInstance().deleteBullet(bulletA);
                    try {
                        player.removeLife();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (obB instanceof Bullet && obA instanceof Player) {
                Bullet bulletA = (Bullet) obB;
                Player player = (Player) obA;
                if (!bulletA.getId().equals(player.getId())) {
                    WorldImpl.getInstance().deleteBullet(bulletA);
                    try {
                        player.removeLife();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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

