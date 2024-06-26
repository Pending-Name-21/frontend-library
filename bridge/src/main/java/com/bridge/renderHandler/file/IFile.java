package com.bridge.renderHandler.file;

import java.nio.file.Path;

/**
 * IFile interface that represents a file with a path.
 */
public interface IFile {

    /**
     * Gets the file path.
     *
     * @return the file path
     */
    Path getPath();
}
