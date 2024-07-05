package com.bridge.ipc;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.SocketServerMock;
import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import com.bridge.renderHandler.render.RenderManager;
import org.junit.jupiter.api.Test;

public class RenderManagerTest {

    @Test
    void checkExceptionOnDisconnectedServer() {
        RenderManager renderManager = new RenderManager();
        try {
            renderManager.render();
        } catch (RenderException e) {
            assertInstanceOf(RenderException.class, e);
        }
    }

    @Test
    void checkScoutingDuringInit() {
        SocketServerMock.cleanup(SocketClient.NAMESPACE);
        Thread socketServer = SocketServerMock.makeServerThread(SocketClient.NAMESPACE);
        socketServer.start();

        RenderManager renderManager = new RenderManager();
        try {
            renderManager.init();
            renderManager.render();
        } catch (RenderException e) {
            fail(e);
        }

        socketServer.interrupt();
        try {
            socketServer.join(100);
        } catch (InterruptedException e) {
            fail(e);
        }
        SocketServerMock.cleanup(SocketClient.NAMESPACE);
    }

    @Test
    void checkScoutOnInterruptedServer() {
        SocketServerMock.cleanup(SocketClient.NAMESPACE);
        Thread socketServer = SocketServerMock.makeServerThread(SocketClient.NAMESPACE);
        socketServer.start();
        RenderManager renderManager = new RenderManager();
        try {
            renderManager.init();
        } catch (RenderException e) {
            fail(e);
        }
        Thread renderThread =
                new Thread(
                        () -> {
                            while (true) {
                                try {
                                    renderManager.render();
                                } catch (RenderException e) {
                                    fail(e);
                                }
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    fail(e);
                                }
                            }
                        });
        renderThread.start();

        socketServer.interrupt();
        try {
            socketServer.join(100);
        } catch (InterruptedException e) {
            fail(e);
        }
        SocketServerMock.cleanup(SocketClient.NAMESPACE);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            fail(e);
        }

        socketServer = SocketServerMock.makeServerThread(SocketServerMock.NAMESPACE);
        renderThread.interrupt();
        try {
            renderThread.join(100);
        } catch (InterruptedException e) {
            fail(e);
        }

        socketServer.interrupt();
        try {
            socketServer.join(100);
        } catch (InterruptedException e) {
            fail(e);
        }
        SocketServerMock.cleanup(SocketClient.NAMESPACE);

        assertFalse(renderThread.isAlive());
    }
}
