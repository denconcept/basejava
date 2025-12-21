package com.basejava.exception;

public class NotExistStorageException extends StorageException {
    
    private static final long serialVersionUID = 1L;

    public NotExistStorageException(String uuid) {
        super("Resume " + uuid + " not exist", uuid);
    }
}