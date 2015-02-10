package com.mygdx.controller;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.mygdx.game.MyGdxGame;
import com.mygdx.models.BombMessage;
import com.mygdx.models.BulletMessage;
import com.mygdx.models.ItemMessage;
import com.mygdx.models.PlayerMessage;
import com.mygdx.models.PositionMessage;
import com.mygdx.world.WorldImpl;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Julie on 25/01/2015.
 */
public class NetworkController {

    final static int UDP = 6969;
    final static int TCP = 6868;
    private Server server;
    private ArrayList<Client> endPoints;
    private ArrayList<Thread> threads;
    private List<InetAddress> addr;
    private Timer timer;
    private static boolean getWorldInfo = true;
    private boolean getPlayer, getBomb, getBullet, getBodyModel = false;


    private NetworkController() {
        timer = new Timer();
        endPoints = new ArrayList<Client>();
        threads = new ArrayList<Thread>();
        addr = new ArrayList<InetAddress>();
    }

    private static class NetworkControllerHolder {
        private final static NetworkController instance = new NetworkController();
    }

    public static NetworkController getInstance() {
        return NetworkControllerHolder.instance;
    }

    public static boolean worldIsLoad() {
        return getWorldInfo;
    }

    public void startEmitters() {
        getWorldInfo = false;
        System.out.println("Starting Initial Emitters");
        for (InetAddress add : addr) {
            Client client = new Client();

            startEndPoint(client);
            register(client.getKryo());

            client.addListener(new Listener() {
                public void received(Connection connection, Object object) {
                    if (!getWorldInfo) {

                        if (object instanceof PlayerMessage[]) {
                            System.out.println("EMMITER :Players receive");
                            WorldImpl.setPlayers((PlayerMessage[]) object);
                            System.out.println("EMMITER :Players push");
                            getPlayer = true;
                        } else if (object instanceof BombMessage[]) {
                            System.out.println("EMMITER :Bombs receive");
                            WorldImpl.setBombs((BombMessage[]) object);
                            System.out.println("EMMITER :Bomb push");
                            getBomb = true;
                        } else if (object instanceof BulletMessage[]) {
                            System.out.println("EMMITER :Bullets receive");
                            WorldImpl.setBullets((BulletMessage[]) object);
                            System.out.println("EMMITER :Bullets push");
                            getBullet = true;
                        }

                        System.out.println("EMMITER : etat du jeu " + getPlayer + "," + getBomb + "," + getBullet);
                        getWorldInfo = getPlayer && getBomb && getBullet;
                    }
                }
            });
            try {
                client.connect(5000, add, TCP, UDP);
                System.out.println("Ajout du nouveau client");
                client.sendTCP(new ItemMessage(MyGdxGame.getInstance().id, 3));
                try {
                    MondeControlleur.getInstance().addPlayerToWorld(MyGdxGame.getInstance().id);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                System.out.println("Can't connect to " + add);
                e.printStackTrace();
            }
        }


    }

    public void startReceiver() {
        server = new Server();
        register(server.getKryo());
        startEndPoint(server);
        try {
            System.out.println("Trying to bind reicever at these ports, UDP: " + UDP + "///TCP: " + TCP);
            server.bind(TCP, UDP);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                super.connected(connection);

                if (!addr.contains(connection.getRemoteAddressTCP().getAddress())) {
                    Client client = new Client();
                    startEndPoint(client);
                    register(client.getKryo());
                    try {
                        client.connect(5000, connection.getRemoteAddressTCP().getAddress(), TCP, UDP);
                    } catch (IOException e) {
                        System.out.println("Server Can't launch a Client connection to " + connection.getRemoteAddressTCP().getAddress());
                        e.printStackTrace();
                        addr.add(connection.getRemoteAddressTCP().getAddress());
                    }
                }
                System.out.println("SERVER : Nouvelle connexion :" + connection.getRemoteAddressTCP());
                System.out.println("Plyer 1 : "+WorldImpl.getInstance().getPlayersTab()[0]);
                connection.sendTCP(WorldImpl.getInstance().getPlayersTab());
                connection.sendTCP(WorldImpl.getInstance().getBombsTab());
                connection.sendTCP(WorldImpl.getInstance().getBulletsTab());
            }

            @Override
            public void received(Connection connection, Object object) {
                System.out.println("SERVER :Message recu de :" + connection.getRemoteAddressTCP());
                if (object instanceof PositionMessage) {
                    PositionMessage positionMessage = (PositionMessage) object;
                    MondeControlleur.getInstance().setPlayerInPosition(positionMessage.getId(), positionMessage.getPosition().x, positionMessage.getPosition().y);

                } else if (object instanceof ItemMessage) {
                    ItemMessage itemMessage = (ItemMessage) object;
                    switch (itemMessage.getType()) {
                        //bullet
                        case 1:
                            MondeControlleur.getInstance().createBullet(itemMessage.getId());
                            break;
                        //bomb
                        case 2:
                            MondeControlleur.getInstance().createBomb(itemMessage.getId());
                            break;
                        case 3:
                            try {
                                MondeControlleur.getInstance().addPlayerToWorld(itemMessage.getId());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            break;

                    }


                } else {

                }
            }

        });

    }

    public void discoverPeers() {
        Client client = new Client();
        startEndPoint(client);
        register(client.getKryo());
        client.start();
        System.out.println("Starting Discover");
        try {
            Log.info("Trying to discover host at port " + UDP);
            addr = client.discoverHosts(UDP, 2000);
        } catch (Exception e) {
            Log.info(e.toString());
        }
        System.out.println("Serveur found at @:" + addr.size());
        if (addr == null) {
            System.out.println("Serveur NOT found ");
            stopEndPoints();
            System.exit(0);
        }
        client.close();

    }

    private void register(Kryo kryo) {
        kryo.register(PositionMessage.class);
        kryo.register(ItemMessage.class);
        kryo.register(com.badlogic.gdx.math.Vector2.class);
        kryo.register(BombMessage[].class);
        kryo.register(PlayerMessage[].class);
        kryo.register(BulletMessage[].class);
        kryo.register(BombMessage.class);
        kryo.register(PlayerMessage.class);
        kryo.register(BulletMessage.class);

    }

    public void startEndPoint(EndPoint endPoint) {
        if (endPoint instanceof Client) endPoints.add((Client) endPoint);
        Thread thread = new Thread(endPoint, endPoint.getClass().getSimpleName());
        threads.add(thread);
        thread.start();
    }

    public void stopEndPoints() {
        stopEndPoints(0);
    }

    public void stopEndPoints(int stopAfterMillis) {
        timer.schedule(new TimerTask() {
            public void run() {
                for (EndPoint endPoint : endPoints)
                    endPoint.stop();
                endPoints.clear();
            }
        }, stopAfterMillis);
    }

    public void sendToAll(Object message) {
        for (Client client : endPoints) {
            client.sendTCP(message);
        }
    }

}
