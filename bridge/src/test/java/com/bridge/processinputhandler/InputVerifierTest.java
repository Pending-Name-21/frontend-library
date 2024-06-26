package com.bridge.processinputhandler;

import com.bridge.EventGenerator;
import com.bridge.ipc.Receiver;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputVerifierTest {

    @Test
    void verifyEventPublication() {
        KeyboardEventManager keyboardManager = new KeyboardEventManager();
        keyboardManager.subscribe(event -> {
            assertEquals("KeyPressed", event.type());
            assertEquals("Return", event.type());
        });

        Receiver receiver = new Receiver();
        receiver.addBuffer(keyboardManager);
        receiver.handleMessage(new EventGenerator().makeEvent());

        InputVerifier inputVerifier = new InputVerifier(List.of(keyboardManager));
        inputVerifier.check();
    }
}
