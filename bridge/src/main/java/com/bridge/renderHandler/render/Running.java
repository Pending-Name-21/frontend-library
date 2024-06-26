package com.bridge.renderHandler.render;

import com.bridge.ipc.SocketClient;
import com.bridge.ipc.Transmitter;
import com.bridge.renderHandler.Coord;
import com.bridge.renderHandler.Size;
import com.bridge.renderHandler.sound.Sound;
import com.bridge.renderHandler.sprite.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Running {
    public static void main(String[] args) {
        try {
            SocketClient socketClient = new SocketClient("/tmp/socket_console");
            Transmitter transmitter = new Transmitter(socketClient);
            for (int i = 0; i < 100; i++) {
                List<Sprite> listSprite = new ArrayList<>();
                List<Sound> listSound = new ArrayList<>();
                Sprite sprite = new Sprite(new Coord(i, 0), new Size(20, 20), "assets/images/pacman.jpeg");
                // sprite.setHidden(false);
                Sound sound = new SoundTest("assets/sounds/guitar.mp3");
                listSprite.add(sprite);
                listSound.add(sound);

                Frame frame = new Frame(listSprite, listSound);
                transmitter.send(frame);
                Thread.sleep(300);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
