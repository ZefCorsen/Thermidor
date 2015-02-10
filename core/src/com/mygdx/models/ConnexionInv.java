package com.mygdx.models;

import com.esotericsoftware.kryonet.Connection;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by Jerem on 10/02/2015.
 */
public class ConnexionInv {
    ArrayList<ConnexionImpl> inv = new ArrayList<ConnexionImpl>();

    public ConnexionInv() {
    }

    public void addNewConnexion(Connection con,InetAddress addr) {
        inv.add(new ConnexionImpl(con ,addr));
    }

    public void setId(String id, Connection con) {
        for (ConnexionImpl conected : inv) {
            if (conected.getConnection().getID() == (con.getID())) conected.setPlayerId(id);
        }
    }

    public void setINet(InetAddress addr, Connection con) {
        for (ConnexionImpl conected : inv) {
            if (conected.getConnection().getID() == (con.getID())) conected.setAddr(addr);
        }
    }
    public ConnexionImpl getConnexionImp(Connection con){
        for (ConnexionImpl conected : inv) {
            if (conected.getConnection().getID() == (con.getID())) return conected;
        }
        return null;
    }

}
