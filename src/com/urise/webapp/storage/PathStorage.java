package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final StreamSerialize streamSerialize;

    protected PathStorage(String dir, StreamSerialize streamSerialize) {
        this.directory = Paths.get(dir);

        Objects.requireNonNull(directory, "Directory mast be not NULL!");
        Objects.requireNonNull(streamSerialize, "algorithm mast be not NULL!");

        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or not writable!");
        }

        this.streamSerialize = streamSerialize;
    }

    @Override
    protected List<Resume> getListResume() {
        List<Resume> resumes = new ArrayList<>();
        Stream<Path> list;

        try {
            list = Files.list(directory);
        } catch (IOException e) {
            throw new StorageException(directory.toString(), "Error read directory!", e);
        }

        Objects.requireNonNull(list).forEach(file -> {
            try {
                resumes.add(streamSerialize.doRead(new BufferedInputStream(Files.newInputStream(file))));
            } catch (IOException e) {
                throw new StorageException(file.toString(), "IO error", e);
            }
        });

        return resumes;
    }

    @Override
    protected void updateResume(Resume r, Path file) {
        deleteResume(file);
        saveResume(r, file);
    }

    @Override
    protected void deleteResume(Path file) {
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new StorageException(file.getFileName().toString(), "File delete error!");
        }
    }

    @Override
    protected Resume getResume(Path file) {
        try {
            return streamSerialize.doRead(new BufferedInputStream(Files.newInputStream(file)));
        } catch (IOException e) {
            throw new StorageException(file.getFileName().toString(), "File read error!", e);
        }
    }

    @Override
    protected void saveResume(Resume r, Path file) {
        try {
            Files.createFile(file);
        } catch (IOException e) {
            throw new StorageException(file.toString(), "The file was not created!", e);
        }

        try {
            streamSerialize.doWrite(r, new BufferedOutputStream(Files.newOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException(file.toString(), "IO error", e);
        }
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected boolean isExist(Path file) {
        return Files.isRegularFile(file);
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException(null, "Error clear!", e);
        }
    }

    @Override
    public int size() {
        Stream<Path> list = null;
        try {
            list = Files.list(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list != null ? (int) list.count() : 0;
    }

}


