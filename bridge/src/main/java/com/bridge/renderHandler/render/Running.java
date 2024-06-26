package com.bridge.renderHandler.render;

import com.bridge.ipc.SocketClient;
import com.bridge.ipc.Transmitter;
import com.bridge.renderHandler.Coord;
import com.bridge.renderHandler.Size;
import com.bridge.renderHandler.sound.Sound;
import com.bridge.renderHandler.sprite.Sprite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Running {
    public static void main(String[] args) {
        SocketClient socketClient = null;
        try {
            socketClient = new SocketClient("/tmp/socket_console");
            Transmitter transmitter = new Transmitter(socketClient);

            long frameTime = 1000 / 60;

            for (int i = 0; i < 300; i += 1) {
                long startTime = System.currentTimeMillis();

                List<Sprite> listSprite = new ArrayList<>();
                List<Sound> listSound = new ArrayList<>();
                Sprite sprite = new Sprite(new Coord(i + 15  , 0), new Size(20, 20), "assets/images/pacman.jpeg");
                Sound sound = new SoundTest("assets/sounds/guitar.mp");
                listSprite.add(sprite);
                listSound.add(sound);

                Frame frame = new Frame(listSprite, listSound);
                transmitter.send(frame);
                System.out.println(frame);

                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;

                if (elapsedTime < frameTime) {
                    try {
                        Thread.sleep(frameTime - elapsedTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socketClient != null) {
                try {
                    socketClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}