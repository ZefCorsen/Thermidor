package com.mygdx.controller;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.mygdx.models.Peer;
import com.mygdx.models.SomeRequest;
import com.mygdx.models.SomeResponse;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by Julie on 25/01/2015.
 */
public class NetworkController {

    final static int UDP = 6969;
    final static int TCP = 6868;
    private Server server;
    private Client client;
    private ArrayList<Peer> peers;

    private NetworkController() {
        client = new Client();
        server = new Server();
        peers = new ArrayList<Peer>();
        // Registering methods
        Kryo kryo = server.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);
        kryo.register(Peer.class);
        kryo = client.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);
        kryo.register(Peer.class);

    }

    // Classes needed to make it a Singleton
    private static class NetworkControllerHolder {

        private final static NetworkController instance = new NetworkController();


    }

    public static NetworkController getInstance() {

        return NetworkControllerHolder.instance;
    }

    public void sendMessage(String message){

        for (Peer peer:peers){

            SomeResponse sent = new SomeResponse();
            sent.text = message;
            try {
                if(!client.isConnected())
                client.connect(5000,peer.IP,TCP,UDP);

            } catch (IOException e) {
                e.printStackTrace();
            }
            client.sendUDP(sent);
        }

    }

    public void startEmitter() {
        client.start() ;
        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof SomeRequest) {
                    SomeRequest request = (SomeRequest) object;
                    System.out.println(request.text);
                    startEmitter();
                    SomeResponse response = new SomeResponse();
                    response.text = "Rep";
                    connection.sendUDP(response);
                }
            }

        });
    }

    public void startReceiver() {

        server.start();
        try {
            Log.info("Trying to bind reicever at these ports, UDP: "+UDP+"///TCP: "+TCP);
            server.bind(TCP, UDP);

        } catch (IOException e) {
            e.printStackTrace();
        }

        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof SomeResponse) {
                    SomeResponse request = (SomeResponse) object;
                    System.out.println(request.text);
                    startEmitter();
                    SomeResponse response = new SomeResponse();
                    response.text = "Rep";
                    connection.sendUDP(response);
                }


            }

        });

    }

    public void discoverPeers(){
        InetAddress addr= null;
      /*  try {
            addr = InetAddress.getByName("172.22.201.136");
            //addr = InetAddress.getByName("192.168.56.1");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }*/
        try {
            Log.info("Trying to discover host at port " + UDP);
            addr = client.discoverHost(UDP, 10000);
        }catch(Exception e){
            Log.info(e.toString());
        }
        System.out.println(addr);
        if(addr == null) {
            System.exit(0);
        }
        try {
            peers.add(new Peer(addr));
            client.connect(5000, addr, TCP, UDP);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
