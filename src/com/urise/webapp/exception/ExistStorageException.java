package com.urise.webapp.exception;

public class ExistStorageException extends StorageException{
    public ExistStorageException(String uuid) {
        super(uuid, "Резюме с таким id уже есть в базе!");
    }
}
