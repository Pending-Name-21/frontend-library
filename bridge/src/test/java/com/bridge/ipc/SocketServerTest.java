package com.bridge.ipc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class SocketServerTest {

    @Test
    @Disabled
    void startServer() {
        SocketServer socketServer = new SocketServer(new Receiver(), "events-socket.sock");
        socketServer.flush();
        socketServer.run();
        System.out.println("Closed socket server");
    }
}
