package com.mygdx.models;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by Julie on 05/02/2015.
 */
public class PeerList {

    private ArrayList<Peer> peerList;

    public PeerList(){

        peerList=new ArrayList<Peer>();


    }

    public ArrayList<Peer> getPeerList(){

        return this.peerList;

    }

    public void addPeer(Peer peer){

        this.peerList.add(peer);

    }

    public void removePeer(InetAddress adress) {

        for (Peer peer : peerList) {

            if (adress.equals(peer.getAdress())) {

                peerList.remove(peer);

            }

        }


    }

}
