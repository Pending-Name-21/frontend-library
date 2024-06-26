package com.bridge.renderHandler.file;
import com.bridge.core.exceptions.renderHandlerExceptions.NonExistentFilePathException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        Path path = Paths.get(filePath);
        if (!pathExists(path)) {
            throw new NonExistentFilePathException(filePath);
        }
        return path;
    }
}
