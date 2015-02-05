package com.mygdx.models;

import java.net.InetAddress;

/**
 * Created by Julie on 26/01/2015.
 */
public class Peer {
    public InetAddress IP;


    public Peer(InetAddress IP){

        this.IP=IP;


    }
    public InetAddress getAdress(){

        return IP;

    }
    public Peer(){

    }


}
