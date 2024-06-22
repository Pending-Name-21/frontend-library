package com.bridge;

import CoffeeTime.InputEvents.KeyboardEvent;
import com.bridge.ipc.Receiver;
import com.bridge.processinputhandler.IPublisher;
import com.bridge.processinputhandler.InputVerifier;
import com.bridge.processinputhandler.KeyboardEventPublisher;
import com.google.flatbuffers.FlatBufferBuilder;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Utils {
    public static InputVerifier makeEmptyInputVerifier() {
        KeyboardEventPublisher publisher =
                new KeyboardEventPublisher(new ConcurrentLinkedQueue<>());
        List<IPublisher> publishers = List.of(publisher);
        return new InputVerifier(publishers);
    }

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
