package ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.exceptions;

public class ManagerSaveException extends RuntimeException {

    public ManagerSaveException(final String message, Exception e) {
        super(message, e);
    }
}
