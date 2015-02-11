package com.mygdx.player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.world.WorldImpl;

/**
 * Created by Jerem on 08/02/2015.
 */
public class BodyModel {


    protected Sprite sprite;
    protected Body body;
//    protected WorldImpl world;
    protected FixtureDef fixtureDef;
    protected String id;


    public BodyModel(String id) {
  //      this.world = world;
        this.id = id;
        fixtureDef = new FixtureDef();

    }

    public BodyModel() {
    }



    public Body getBody() {
        return body;
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

  /*  public Player getPlayer() {
        return player;
    }*/
}
