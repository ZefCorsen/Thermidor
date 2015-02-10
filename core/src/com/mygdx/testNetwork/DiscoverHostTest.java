package com.mygdx.testNetwork;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.net.InetAddress;

public class DiscoverHostTest extends KyronetTestCase {
    public void testBroadcast() throws IOException {

        final Server broadcastServer = new Server();
        startEndPoint(broadcastServer);
        broadcastServer.bind(0, udpPort);
        final Server server = new Server();
        startEndPoint(server);
        server.bind(54555);
        server.addListener(new Listener() {
            public void disconnected(Connection connection) {
                broadcastServer.stop();
                server.stop();
            }

            @Override
            public void connected(Connection connection) {
                super.connected(connection);
                System.out.println("Client connected");
            }

            public void received(Connection connection, Object object) {
                if (object instanceof String) {
                    String o = (String) object;
                    System.out.println("Server : Message  du client :" + o);
                    System.out.println("Server : Nbr de connection :" + server.getConnections().length);
                    server.sendToAllTCP("Client in : " + o);
                }

            }
        });
// ----
        Client client = new Client();
        InetAddress host = client.discoverHost(udpPort, 2000);
        if (host == null) {
            stopEndPoints();
            fail("No servers found.");
            return;
        }
        startEndPoint(client);
        client.connect(2000, host, tcpPort);

        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof String) {
                    String o = (String) object;
                    System.out.println("Message  du server a client 1 :" + o);
                }

            }
        });
        client.sendTCP("1");


        Client client2 = new Client();
        startEndPoint(client2);
        client2.connect(2000, host, tcpPort);
        client2.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof String) {
                    String o = (String) object;
                    System.out.println("Message  du server a client 2 :" + o);
                }

            }
        });
        client2.sendTCP("2");


        client.stop();
        client2.stop();
        waitForThreads();
    }

}
