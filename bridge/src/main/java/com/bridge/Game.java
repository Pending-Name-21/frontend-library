package com.bridge;

import com.bridge.core.exceptions.GameException;
import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import com.bridge.core.handlers.LogHandler;
import com.bridge.gamesettings.AGameSettings;
import com.bridge.initializerhandler.GameInitializer;
import com.bridge.ipc.Receiver;
import com.bridge.ipc.SocketServer;
import com.bridge.processinputhandler.InputVerifier;
import com.bridge.processinputhandler.KeyboardEventManager;
import com.bridge.processinputhandler.MouseEventManager;
import com.bridge.renderHandler.render.RenderManager;
import com.bridge.renderHandler.repository.IRepository;
import com.bridge.renderHandler.sound.Sound;
import com.bridge.renderHandler.sprite.Sprite;
import com.bridge.updatehandler.UpdatePublisher;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

/**
 * Main game loop class.
 */
public class Game {
    private final InputVerifier inputVerifier;
    private final AGameSettings gameSettings;
    private final UpdatePublisher updatePublisher;
    private final RenderManager renderManager;
    private final GameInitializer gameInitializer;

    private final AtomicBoolean atomicBoolean;
    private final KeyboardEventManager keyboardEventManager;
    private final MouseEventManager mouseEventManager;
    private final SocketServer socketServer;
    private double framesCount;

    /**
     * Constructs a Game with the specified input verifier.
     *
     * @param gameSettings the game settings to use
     */
    public Game(AGameSettings gameSettings) {
        this.gameSettings = gameSettings;
        this.updatePublisher = new UpdatePublisher();
        this.gameInitializer = new GameInitializer();

        atomicBoolean = new AtomicBoolean();
        keyboardEventManager = new KeyboardEventManager();
        mouseEventManager = new MouseEventManager();
        Receiver receiver = new Receiver();
        receiver.addBuffer(keyboardEventManager);
        receiver.addBuffer(mouseEventManager);
        socketServer = new SocketServer(new Receiver(), SocketServer.NAMESPACE, atomicBoolean);

        inputVerifier = new InputVerifier(List.of(keyboardEventManager, mouseEventManager));
        renderManager = new RenderManager();
    }

    /**
     * Initializes game subscribers initializers.
     */
    public void initialize() throws GameException {
        Thread thread = new Thread(socketServer);
        thread.start();
        gameInitializer.initializeSubscribers();
    }

    /**
     * Processes input by calling the check method on the input verifier.
     */
    private void processInput() throws GameException {
        inputVerifier.check();
    }

    /**
     * Updates the game state and notifies subscribers.
     */
    public void update() throws GameException {
        updatePublisher.notifySubscribers();
    }

    /**
     * Renders sprites and plays sounds
     */
    public void render() {
        try {
            renderManager.render();
        } catch (RenderException e) {
            LogHandler.log(Level.SEVERE, "Frames threshold has been reached", e);
        }
    }

    /**
     * Runs the main game loop.
     */
    public void run() throws GameException {
        initialize();
        while (!gameSettings.isGameOver()) {
            processInput();
            update();
            render();
            Thread.yield();

            framesCount += 7.5;

            try {
                Thread.sleep(125);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        atomicBoolean.set(false);
    }

    public KeyboardEventManager getKeyboardEventManager() {
        return keyboardEventManager;
    }

    public MouseEventManager getMouseEventManager() {
        return mouseEventManager;
    }

    public IRepository<Sound> getSoundIRepository() {
        return renderManager.getSoundIRepository();
    }

    public IRepository<Sprite> getSpriteIRepository() {
        return renderManager.getSpriteIRepository();
    }

    public UpdatePublisher getUpdatePublisher() {
        return updatePublisher;
    }

    public GameInitializer getGameInitializer() {
        return gameInitializer;
    }

    /**
     * Gets the current frames count since game was run
     * @return the frames count
     */
    public double getFramesCount() {
        return framesCount;
    }
}
