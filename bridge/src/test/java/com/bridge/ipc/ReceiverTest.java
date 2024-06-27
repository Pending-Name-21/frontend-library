package com.bridge.ipc;

import com.bridge.EventGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReceiverTest {

    @Test
    void simpleEventDeserialization() {
        Receiver receiver = new Receiver();
        receiver.addBuffer(Assertions::assertNotNull);
        receiver.handleMessage(new EventGenerator().makeEvent());

        receiver.addBuffer(Assertions::assertNotNull);
        receiver.handleMessage(new EventGenerator().makeEvent());
    }
}
