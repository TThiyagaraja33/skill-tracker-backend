package com.cts.skilltracker.persist.exception;

public class NotValidUpdateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotValidUpdateException(String msg) {
        super(msg);
    }
}
