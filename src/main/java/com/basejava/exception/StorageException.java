package com.basejava.exception;

public class StorageException extends RuntimeException {

    private static final long serialVersionUID = 1L;
	private final String uuid;

    public StorageException(Exception e) {
        this(e.getMessage(), e);
    }

    public StorageException(String msg, String uuid) {
        super(msg);
        this.uuid = uuid;
    }

    public StorageException(String msg, Exception e) {
        this(msg, null, e);
    }

    public StorageException(String msg, String uuid, Exception e) {
        super(msg, e);
        this.uuid = uuid;
    }

    @SuppressWarnings("unused")
    public String getUuid() {
        return uuid;
    }
}