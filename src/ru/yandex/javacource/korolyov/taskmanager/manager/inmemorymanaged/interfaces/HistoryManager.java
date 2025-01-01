package ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.interfaces;

import ru.yandex.javacource.korolyov.taskmanager.tasks.Task;

import java.util.List;

public interface HistoryManager {

    List<Task> getHistory();

    void add(Task task);

    void remove(int id);
}
