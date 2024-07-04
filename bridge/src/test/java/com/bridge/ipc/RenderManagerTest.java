package com.bridge.ipc;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import com.bridge.renderHandler.render.RenderManager;
import org.junit.jupiter.api.Test;

public class RenderManagerTest {
    @Test
    void checkExceptionOnDisconnectedServer() {
        RenderManager renderManager = new RenderManager();
        for (int i = 0; i < 60; i++) {
            try {
                renderManager.render();
            } catch (RenderException e) {
                assertInstanceOf(RenderException.class, e);
                assertEquals(59, i);
            }
        }
    }
}
