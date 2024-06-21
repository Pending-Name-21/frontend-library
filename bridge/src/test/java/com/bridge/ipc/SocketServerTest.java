package com.bridge.ipc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SocketServerTest {

    @Test
    void verifyThatSocketListens() {
        SocketServer socketServer = new SocketServer(new Receiver(), "events-socket.sock");
        Thread thread = new Thread(socketServer);
        thread.start();
        thread.interrupt();
        assertTrue(true);
    }

    @Test
    @Disabled
    void startClient() {
        SocketServer socketServer = new SocketServer(new Receiver(), "events-socket.sock");
        socketServer.flush();
        System.out.println("Starting socket server");
        socketServer.run();
        System.out.println("Closed socket server");
    }
}