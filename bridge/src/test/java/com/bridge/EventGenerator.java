package com.bridge;

import CoffeeTime.InputEvents.Event;
import CoffeeTime.InputEvents.Keyboard;
import CoffeeTime.InputEvents.Mouse;
import CoffeeTime.InputEvents.Position;
import com.google.flatbuffers.FlatBufferBuilder;

import java.nio.ByteBuffer;

public class EventGenerator {
    private final FlatBufferBuilder builder;

    public EventGenerator() {
        builder = new FlatBufferBuilder();
    }

    public int mouseGenerator(String type, String button) {
        int position = Position.createPosition(builder, 1, 1);
        int typeOffset = builder.createString(type);
        int buttonOffset = builder.createString(button);
        return Mouse.createMouse(builder, typeOffset, buttonOffset, position);
    }

    public int keyboardGenerator(String type, String key) {
        int typeOffset = builder.createString(type);
        int keyOffset = builder.createString(key);
        return Keyboard.createKeyboard(builder, typeOffset, keyOffset);
    }

    public ByteBuffer makeEvent() {
        int mouseOffset = mouseGenerator("MousePressed", "Left");
        int keyboardOffset = keyboardGenerator("KeyPressed", "Return");
        int eventOffset = Event.createEvent(builder, mouseOffset, keyboardOffset);
        builder.finish(eventOffset);
        return builder.dataBuffer();
    }

    public ByteBuffer makeEvent(String mouseType, String buttonName, String keyboardType, String keyName) {
        int mouseOffset = mouseGenerator(mouseType, buttonName);
        int keyboardOffset = keyboardGenerator(keyboardType, keyName);
        int eventOffset = Event.createEvent(builder, mouseOffset, keyboardOffset);
        builder.finish(eventOffset);
        return builder.dataBuffer();
    }
}
