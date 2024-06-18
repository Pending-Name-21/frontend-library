package com.bridge.initializerhandler;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.core.exceptions.initializerhandler.NotPossibleToInitializeSubscribersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameInitializerTest {
    private GameInitializer gameInitializer;
    private TestInitializer subscriber1;
    private TestInitializer subscriber2;

    @BeforeEach
    void setUp() {
        gameInitializer = new GameInitializer();
        subscriber1 = new TestInitializer();
        subscriber2 = new TestInitializer();
    }

    @Test
    void testNotifySubscribers() throws NotPossibleToInitializeSubscribersException {
        gameInitializer.subscribe(subscriber1);
        gameInitializer.subscribe(subscriber2);

        gameInitializer.initializeSubscribers();

        assertTrue(subscriber1.initialized);
        assertTrue(subscriber2.initialized);
    }

    static class TestInitializer implements IIinitializerSubscriber {
        boolean initialized = false;

        @Override
        public void init() {
            initialized = true;
        }
    }
}
