package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serialize.StreamSerialize;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final StreamSerialize streamSerialize;

    protected FileStorage(File directory, StreamSerialize streamSerialize) {
        Objects.requireNonNull(directory, "directory mast be not NULL!");
        Objects.requireNonNull(streamSerialize, "algorithm mast be not NULL!");

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException("is not readable/writable");
        }

        this.directory = directory;
        this.streamSerialize = streamSerialize;
    }

    @Override
    protected List<Resume> getListResume() {
        return getFileList().stream().map(this::getResume).collect(Collectors.toList());
    }

    @Override
    protected void updateResume(Resume r, File file) {
        deleteResume(file);
        saveResume(r, file);
    }

    @Override
    protected void deleteResume(File file) {
        if (!file.delete()) {
            throw new StorageException(file.getName(), "File delete error!");
        }
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return streamSerialize.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException(file.getName(), "File read error!", e);
        }
    }

    @Override
    protected void saveResume(Resume r, File file) {
        try {
            if (file.createNewFile()) {
                streamSerialize.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
            } else {
                throw new StorageException(file.getName(), "The file was not created!");
            }
        } catch (IOException e) {
            throw new StorageException(file.getName(), "IO error", e);
        }
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public void clear() {
        getFileList().forEach(this::deleteResume);
    }

    @Override
    public int size() {
        return getFileList().size();
    }

    private List<File> getFileList() {
        List<File> fileList = Arrays.asList(directory.listFiles());
        if (fileList == null) {
            throw new StorageException(null, "No files found!");
        }
        return fileList;
    }

}
