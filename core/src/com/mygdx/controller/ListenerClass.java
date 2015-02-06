package com.mygdx.controller;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.player.Bomb;
import com.mygdx.player.Player;


/**
 * Created by Jerem on 06/02/2015.
 */
public class ListenerClass implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        System.out.println("Things touch !!!");
        Object obA= contact.getFixtureA().getUserData();
        if(obA!= null){
            if(obA instanceof Player){
                Player pA = (Player) obA;
                System.out.println("Player A : "+pA.getId());
            }
            if(obA instanceof Bomb){
                Bomb bA = (Bomb) obA;
                System.out.println(" Bom A drop by :"+bA.getIdPlayer());
            }
        }
        Object obB= contact.getFixtureB().getUserData();
        if(obB!= null){
            if(obB instanceof Player){
                Player pA = (Player) obB;
                System.out.println("Player B: "+pA.getId());
            }
            if(obB instanceof Bomb){
                Bomb bA = (Bomb) obB;
                System.out.println("Bom B drop by :"+ bA.getIdPlayer());
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

