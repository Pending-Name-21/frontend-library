package com.bridge.renderHandler.file;

import static org.junit.jupiter.api.Assertions.*;

import com.bridge.core.exceptions.renderHandlerExceptions.NonExistentFilePathException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileHandlerTest {
    private FileHandler fileHandler;

    @BeforeEach
    void setUp() {
        fileHandler = new FileHandler();
    }

    @Test
    public void testPathExists_FileExists() throws IOException {
        Path tempFile = Files.createTempFile("testFile", ".txt");
        Path path = Paths.get(tempFile.toString());
        try {
            assertTrue(fileHandler.pathExists(path));
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    public void testPathExists_FileDoesNotExist() {
        Path tempFile = Paths.get("nonexistentFile.txt");
        assertFalse(fileHandler.pathExists(tempFile));
    }

    @Test
    public void testGetFilePath_FileExists() throws IOException, NonExistentFilePathException {
        Path tempFile = Files.createTempFile("testFile", ".txt");
        try {
            assertEquals(tempFile, fileHandler.getFilePath(tempFile.toString()));
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    public void testGetFilePath_FileDoesNotExist() {
        assertThrows(
                NonExistentFilePathException.class,
                () -> fileHandler.getFilePath("nonexistentFile.txt"));
    }
}
