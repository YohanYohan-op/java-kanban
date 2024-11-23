package ru.yandex.javacource.korolyov.taskManager.manager;

import ru.yandex.javacource.korolyov.taskManager.tasks.Task;

import java.util.ArrayList;

public interface HistoryManager {

    ArrayList<Task> getHistory();

    void addHistory(Task task);

}
