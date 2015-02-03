package com.mygdx.models;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Julie on 03/02/2015.
 */
public class PositionMessage {
    String id;
    Vector2 position = new Vector2();

    public PositionMessage(){

    }
    public PositionMessage(String id,Vector2 position){
        this.id=id;
        this.position=position;
    }
    public String getId(){
        return id;
    }
    public Vector2 getPosition(){
        return position;
    }

}
