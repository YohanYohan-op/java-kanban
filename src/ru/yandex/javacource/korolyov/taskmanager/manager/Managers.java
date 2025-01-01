package ru.yandex.javacource.korolyov.taskmanager.manager;

import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.InMemoryHistoryManager;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.InMemoryTaskManager;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.interfaces.HistoryManager;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.interfaces.TaskManager;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
