package com.bridge.processinputhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bridge.EventGenerator;
import com.bridge.ipc.Receiver;
import java.util.List;
import org.junit.jupiter.api.Test;

class InputVerifierTest {

    @Test
    void verifyEventPublication() {
        KeyboardEventManager keyboardManager = new KeyboardEventManager();
        keyboardManager.subscribe(
                event -> {
                    assertEquals("KeyPressed", event.type());
                    assertEquals("Return", event.key());
                });

        Receiver receiver = new Receiver();
        receiver.addBuffer(keyboardManager);
        receiver.handleMessage(new EventGenerator().makeEvent());

        InputVerifier inputVerifier = new InputVerifier(List.of(keyboardManager));
        inputVerifier.check();
    }
}
