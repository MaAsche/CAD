package com.mabeto.backend.fileio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FilesystemPictureHandler {
    private static final String directory = "static/images";

    public FilesystemPictureHandler() throws IOException {
        final Path imageDirectory = Paths.get(directory);
        Files.createDirectories(imageDirectory);
    }

    public void putImage(Long id, byte[] image) throws IOException {
        final Path imageFile = getPath(id);
        Files.write(imageFile, image, StandardOpenOption.CREATE);
    }

    public void deleteImage(Long id) throws IOException {
        final Path imageFile = getPath(id);
        Files.delete(imageFile);
    }

    private Path getPath(Long id) {
        final String fileName = id.toString();
        return Paths.get(directory, fileName);
    }
}
