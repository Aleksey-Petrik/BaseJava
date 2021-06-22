package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected abstract Resume doRead(InputStream is) throws IOException;

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory mast be not NULL!");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException("is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected List<Resume> getListResume() {
        List<Resume> resumes = new ArrayList<>();
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            try {
                resumes.add(doRead(new BufferedInputStream(new FileInputStream(file))));
            } catch (IOException e) {
                throw new StorageException(file.getName(), "IO error", e);
            }
        }
        return resumes;
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
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException(file.getName(), "File read error!", e);
        }
    }

    @Override
    protected void saveResume(Resume r, File file) {
        try {
            if (file.createNewFile()) {
                doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
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
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            deleteResume(file);
        }
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.listFiles()).length;
    }

}
