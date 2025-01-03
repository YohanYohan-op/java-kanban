package ru.yandex.javacource.korolyov.taskmanager.manager;

import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.FileBackedTaskManager;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.InMemoryHistoryManager;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.InMemoryTaskManager;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.interfaces.HistoryManager;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.interfaces.TaskManager;

import java.io.File;

public class Managers {

    public static TaskManager getDefault() {
        return new FileBackedTaskManager(new File("C:\\Users\\korol\\IdeaProjects\\java-kanban\\task.csv"));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
