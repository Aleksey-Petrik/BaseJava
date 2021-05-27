package com.urise.webapp.exception;

public class NotExistStorageException extends StorageException{
    public NotExistStorageException(String uuid) {
        super(uuid, "Резюме с таким id в базе нет!");
    }
}
