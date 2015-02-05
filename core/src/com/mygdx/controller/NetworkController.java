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
    private ArrayList<Peer> peers;
    private ArrayList<Client> clients;
    public World myWorld;
    public String myId;


    private NetworkController() {
        client = new Client();
        server = new Server();
        peers = new ArrayList<Peer>();
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
        kryo.register(ArrayList.class);
        kryo.register(com.mygdx.player.Player.class);




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
               // Log.info("peers number is : "+peers.size());
                if(myWorld!=null) {
                 //   System.out.println(": peers number is : " + peers.size());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            client.sendTCP(sent);
        }

    }
    public void sendJoinMessage(String id){
        for (Peer peer:peers){

            try {
                if(!client.isConnected())
                    client.connect(5000,peer.IP,TCP,UDP);
                Log.info("peers number is : "+peers.size());
                System.out.println("peers number is : " + peers.size());

            } catch (IOException e) {
                e.printStackTrace();
            }
            client.sendTCP(new JoinMessage(id));
        }




    }
    public void sendGameState(World world){

        for (Peer peer:peers){

            try {
                if(!client.isConnected())
                    client.connect(5000,peer.IP,TCP,UDP);
                Log.info("peers number is : "+peers.size());
                System.out.println("peers number is : " + peers.size());

            } catch (IOException e) {
                e.printStackTrace();
            }
            client.sendTCP(world);
        }

    }

    public void sendPosition(String id){
        for (Peer peer:peers){

            PositionMessage pos = null;
            try {
                if (myWorld!=null){
                pos = new PositionMessage(id,myWorld.getPlayer(id).getPosition());}
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                if(!client.isConnected())
                    client.connect(5000,peer.IP,TCP,UDP);
                // Log.info("peers number is : "+peers.size());
                if(myWorld!=null) {
                    System.out.println("Sending position");
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

                if (object instanceof SomeResponse) {
                    SomeResponse request = (SomeResponse) object;
                    if(request.text.equals("Join")){
                      //  myWorld.addPlayer(new Player());
                    }
                    System.out.println(request.text);

                    startEmitter();
                    SomeResponse response = new SomeResponse();
                    response.text = "Rep";
                    connection.sendTCP(response);
                }
                if (object instanceof World) {
                    System.out.println("Get initial position");
                    World request = (World) object;
                    myWorld=request;

                }

                if (object instanceof PositionMessage) {
                    PositionMessage positionMessage = (PositionMessage) object;
                    String id = positionMessage.getId();
                    System.out.println("From "+positionMessage.getId()+" Position reiceived" + positionMessage.getPosition().toString());

                    try {
                       Player player;
                       player = myWorld.getPlayer(id);
                       player.setWantedPosition(positionMessage.getPosition());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //connection.sendTCP(myWorld);
                }
                if (object instanceof JoinMessage) {
                    JoinMessage messageJoin = (JoinMessage) object;
                    System.out.println("Adding new player");
                    discoverPeers();
                    myWorld.addPlayer(new Player(new Vector2(0,0),messageJoin.getId()));
                    connection.sendTCP(myWorld);
                    System.out.print("Player Joining " + messageJoin.getId());
                    sendJoinMessage(myId);
                    sendGameState(myWorld);


                }

            }

        });

    }

    public void discoverPeers(){
       List<InetAddress> addr;
        addr = null;
      /*  try {
            addr = InetAddress.getByName("172.22.201.136");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }*/
        try {
            Log.info("Trying to discover host at port " + UDP);
             addr = client.discoverHosts(UDP, 10000);
            //addr = InetAddress.getByName("192.168.1.1");
        }catch(Exception e){
            Log.info(e.toString());
        }
        System.out.println(addr);
        if(addr == null) {
            System.exit(0);
        }
            for(InetAddress adress:addr){

                peers.add(new Peer(adress));
            }


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
        kryo.register(ArrayList.class);
        kryo.register(com.mygdx.player.Player.class);








    }

}
