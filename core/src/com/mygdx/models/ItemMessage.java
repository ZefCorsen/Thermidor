package com.mygdx.models;

/**
 * Created by Jerem on 10/02/2015.
 */

public class ItemMessage {
    String id;
    int type;

    public ItemMessage(String id, int type) {
        this.id = id;
        this.type = type;
    }

    public ItemMessage() {
    }

    public String getId() {
        return id;
    }

    public int getType() {
        return type;
    }

}
