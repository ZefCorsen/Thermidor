package com.mygdx.models;

/**
 * Created by Julie on 03/02/2015.
 */
public class JoinMessage {
    String id;
    public JoinMessage(String id){
        this.id=id;
    }
    public JoinMessage(){

    }
    public String getId(){
        return id;
    }
}
