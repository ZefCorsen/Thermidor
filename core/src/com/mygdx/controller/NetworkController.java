package com.mygdx.controller;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.mygdx.models.JoinMessage;
import com.mygdx.models.Peer;
import com.mygdx.models.PeerList;
import com.mygdx.models.PositionMessage;
import com.mygdx.models.SomeRequest;
import com.mygdx.models.SomeResponse;
import com.mygdx.models.State;

import com.mygdx.player.Player;
import com.mygdx.world.Block;
import com.mygdx.world.World;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Julie on 25/01/2015.
 */
public class NetworkController {

    final static int UDP = 6969;
    final static int TCP = 6868;
    private Server server;
    private Client client;
    private PeerList peers;
    private ArrayList<Client> clients;
    public World myWorld;
    public String myId;


    private NetworkController() {
        client = new Client();
        server = new Server();
        peers = new PeerList();
        // Registering methods
        Kryo kryo = server.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);
        kryo.register(com.mygdx.world.Block.class);
        kryo.register(com.badlogic.gdx.math.Rectangle.class);
        kryo.register(Peer.class);
        kryo.register(State.class);
        kryo.register(com.mygdx.world.World.class);
        kryo.register(JoinMessage.class);
        kryo.register(com.mygdx.models.PositionMessage.class);
        kryo.register(com.badlogic.gdx.math.Vector2.class);
        kryo.register(Object[].class);
        kryo.register(byte[].class);
        kryo.register(ArrayList.class);
        kryo.register(java.net.Inet4Address.class);
        kryo.register(com.mygdx.player.Player.class);
        kryo.register(com.mygdx.models.PeerList.class);




    }

    // Classes needed to make it a Singleton
    private static class NetworkControllerHolder {

        private final static NetworkController instance = new NetworkController();


    }

    public static NetworkController getInstance() {

        return NetworkControllerHolder.instance;
    }

    public void sendMessage(String message){

        for (Peer peer:peers.getPeerList()){

            SomeResponse sent = new SomeResponse();
            sent.text = message;
            try {
                if(!client.isConnected())
                client.connect(5000,peer.IP,TCP,UDP);
                if(myWorld!=null) {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            client.sendTCP(sent);
        }

    }
    public void sendJoinMessage(String id){
        for (Peer peer:peers.getPeerList()){

            try {
                if(!client.isConnected())
                    client.connect(5000,peer.IP,TCP,UDP);

                Log.info("peers number is : "+peers.getPeerList().size());
                System.out.println("peers number is : " + peers.getPeerList().size());


            } catch (IOException e) {
                e.printStackTrace();
            }
            client.sendTCP(new JoinMessage(id));
        }




    }
    public void sendGameState(World world){

        for (Peer peer:peers.getPeerList()){

            try {


                if(!client.isConnected()) {
                    System.out.print("Peer connection is " + peer.IP);
                    client.connect(5000, peer.IP, TCP, UDP);

                }
                Log.info("peers number is : "+peers.getPeerList().size());
                System.out.println("peers number is : " + peers.getPeerList().size());


            } catch (IOException e) {
                e.printStackTrace();
                System.out.print("\nerreur lors de l'envoi du world: "+e.getMessage()+"\n");
            }
            System.out.print("\nEnvoi du monde\n");
            client.sendTCP(world);
        }

    }

    public void sendPeers(){
        for (Peer peer:peers.getPeerList()){

            try {


                if(!client.isConnected()) {
                    System.out.print("Peer connection is " + peer.IP);
                    client.connect(5000, peer.IP, TCP, UDP);

                }
                Log.info("peers number is : "+peers.getPeerList().size());
                System.out.println("peers number is : " + peers.getPeerList().size());


            } catch (IOException e) {
                e.printStackTrace();
                System.out.print("\nerreur lors de l'envoi des peers: "+e.getMessage()+"\n");
            }
            System.out.print("\nEnvoi des peers\n");
            client.sendTCP(peers);
        }
    }

    public void sendPosition(String id){
        for (Peer peer:peers.getPeerList()){

            PositionMessage pos = null;
            try {
                if (myWorld!=null){
                pos = new PositionMessage(id,myWorld.getPlayer(id).getWantedPosition());}
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                if(!client.isConnected())
                    client.connect(5000,peer.IP,TCP,UDP);
                // Log.info("peers number is : "+peers.size());
                if(myWorld!=null) {
                    //System.out.println("Sending position");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            client.sendTCP(pos);
        }

    }



    public void startEmitter() {
        client.start() ;
        RegisterClient(client);
        System.out.println("Starting Emitter");

        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {

                if (object instanceof World) {
                    World messageWorld = (World) object;
                    myWorld = messageWorld;
                    connection.sendTCP(myWorld);
                }
                if (object instanceof PeerList) {
                    PeerList peerMessage = (PeerList) object;
                    peers.MergePeerList(peerMessage);
                    System.out.println("Peer received : " + peers.getPeerList().toString());
                }
            }

        });
    }

    public void startReceiver(World world) {
        this.myWorld=world;
        server.start();
        try {
            System.out.println("Trying to bind reicever at these ports, UDP: "+UDP+"///TCP: "+TCP);
            Log.info("Trying to bind reicever at these ports, UDP: "+UDP+"///TCP: "+TCP);
            server.bind(TCP, UDP);

        } catch (IOException e) {
            e.printStackTrace();
        }

        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {


                if (object instanceof World) {
                    System.out.println("Get initial position");
                    World request = (World) object;
                    myWorld=request;
                    System.out.println("World du rsx : "+myWorld.toString());

                }


                if (object instanceof PositionMessage) {
                    PositionMessage positionMessage = (PositionMessage) object;
                    String id = positionMessage.getId();
                    //System.out.println("From "+positionMessage.getId()+" Position reiceived" + positionMessage.getPosition().toString());

                    try {

                        myWorld.getPlayer(id).setWantedPosition(positionMessage.getPosition());


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("myWorld after receiving position" +myWorld.toString());
                }
                if (object instanceof JoinMessage) {
                    startEmitter();
                    JoinMessage messageJoin = (JoinMessage) object;
                    System.out.println("Adding new player");

                    peers.getPeerList().add(new Peer(connection.getRemoteAddressTCP().getAddress()));
                    sendPeers();
                    myWorld.addPlayer(new Player(new Vector2(0, 0), messageJoin.getId()));
                    System.out.print("Player Joining " + messageJoin.getId());
                    sendGameState(myWorld);

                }

            }

        });

    }

    public void discoverPeers() {
        InetAddress addr;
        addr = null;
      /*  try {
            addr = InetAddress.getByName("172.22.201.136");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }*/
        try {
            Log.info("Trying to discover host at port " + UDP);
            addr = client.discoverHost(UDP, 1000);
            //addr = InetAddress.getByName("192.168.1.1");
        } catch (Exception e) {
            Log.info(e.toString());
        }

        System.out.println(addr);
        if (addr == null) {

            System.exit(0);
        }


        peers.getPeerList().add(new Peer(addr));
    }

    public World getWorld(){
        return myWorld;
    }

    public String getLocalPeer(){
        System.out.println("============================================Builder=================================:");

        StringBuilder sb = new StringBuilder();
        try {
            System.out.println("Interfaces :");
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            server.toString();
            System.out.println(interfaces.hasMoreElements());
           NetworkInterface ni= interfaces.nextElement();
            byte[] mac = ni.getHardwareAddress();
                for (int i = 0; i < mac.length; i++) {
                   sb.append( String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }

        } catch (SocketException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    private static void RegisterClient(Client client){

        Kryo kryo = client.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);
        kryo.register(com.mygdx.world.Block.class);
        kryo.register(com.badlogic.gdx.math.Rectangle.class);
        kryo.register(Peer.class);
        kryo.register(State.class);
        kryo.register(com.mygdx.world.World.class);
        kryo.register(JoinMessage.class);
        kryo.register(com.mygdx.models.PositionMessage.class);
        kryo.register(com.badlogic.gdx.math.Vector2.class);
        kryo.register(Object[].class);
        kryo.register(byte[].class);
        kryo.register(ArrayList.class);
        kryo.register(java.net.Inet4Address.class);
        kryo.register(com.mygdx.player.Player.class);
        kryo.register(com.mygdx.models.PeerList.class);








    }

}
