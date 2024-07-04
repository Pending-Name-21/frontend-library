package com.bridge.renderHandler.file;

import com.bridge.core.exceptions.renderHandlerExceptions.NonExistentFilePathException;
import com.bridge.core.handlers.LogHandler;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Level;

/**
 * Handles file operations checking if a path exists and getting a Path object.
 */
public class FileHandler {

    /**
     * Checks if the file at the given path exists.
     *
     * @param filePath the file path.
     * @return true if the file exists, false otherwise.
     */
    public boolean pathExists(Path filePath) {
        return Files.exists(filePath);
    }

    /**
     * Gets the file path of a file as a Path object
     *
     * @param filePath the file path
     * @return a Path object with the filePath
     */
    public Path getFilePath(String filePath) throws NonExistentFilePathException {
        if (filePath == null) {
            throw new NonExistentFilePathException("");
        }

        Optional<Path> attempt = attemptFetchResource(filePath);
        if (attempt.isPresent()) {
            return attempt.get();
        }

        attempt = attemptFetchFullPath(filePath);
        if (attempt.isPresent()) {
            return attempt.get();
        }

        throw new NonExistentFilePathException(filePath);
    }

    private Optional<Path> attemptFetchResource(String filePath) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL resource = classloader.getResource(filePath);
        if (resource != null) {
            try {
                Path path = Paths.get(resource.toURI());
                return Optional.of(path);
            } catch (Exception e) {
                LogHandler.log(Level.WARNING, "Caught exception", e);
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    private Optional<Path> attemptFetchFullPath(String filePath) {
        Path path = Paths.get(filePath);
        if (pathExists(path)) {
            return Optional.of(path);
        }
        return Optional.empty();
    }
}
