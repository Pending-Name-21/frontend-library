package com.bridge;

import CoffeeTime.InputEvents.KeyboardEvent;
import com.bridge.ipc.Receiver;
import com.google.flatbuffers.FlatBufferBuilder;
import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Utils {

    public static ByteBuffer makeKeyboardEvent() {
        FlatBufferBuilder builder = new FlatBufferBuilder();
        int eventState = builder.createString("Pressed");
        int eventName = builder.createString("Return");

        KeyboardEvent.startKeyboardEvent(builder);
        KeyboardEvent.addName(builder, eventName);
        KeyboardEvent.addState(builder, eventState);
        int event = KeyboardEvent.endKeyboardEvent(builder);
        builder.finish(event);
        return builder.dataBuffer();
    }

    public static Optional<ConcurrentLinkedQueue<KeyboardEvent>> createBuffer() {
        ConcurrentLinkedQueue<KeyboardEvent> eventQueue = new ConcurrentLinkedQueue<>();
        Receiver receiver = new Receiver(eventQueue);
        ByteBuffer binKeyboardEvent = makeKeyboardEvent();
        receiver.handleMessage(binKeyboardEvent);
        return Optional.of(eventQueue);
    }
}
