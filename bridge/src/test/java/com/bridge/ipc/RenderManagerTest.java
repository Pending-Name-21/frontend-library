package com.bridge.ipc;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import com.bridge.renderHandler.render.RenderManager;
import com.bridge.renderHandler.repository.SoundRepository;
import com.bridge.renderHandler.repository.SpriteRepository;
import org.junit.jupiter.api.Test;

public class RenderManagerTest {
    @Test
    void checkExceptionOnDisconnectedServer() {
        RenderManager renderManager =
                new RenderManager(new SpriteRepository(), new SoundRepository());
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
