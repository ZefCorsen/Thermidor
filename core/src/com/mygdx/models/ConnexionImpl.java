package com.mygdx.models;

import com.esotericsoftware.kryonet.Connection;

import java.net.InetAddress;

/**
 * Created by Jerem on 10/02/2015.
 */
public class ConnexionImpl {

    Connection connection;
    InetAddress addr;
    String playerId;

    public ConnexionImpl(Connection connection , InetAddress addr) {
        this.connection = connection;
        this.addr=addr;
    }

    public Connection getConnection() {
        return connection;
    }

    public InetAddress getAddr() {
        return addr;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setAddr(InetAddress addr) {
        this.addr = addr;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }


}
