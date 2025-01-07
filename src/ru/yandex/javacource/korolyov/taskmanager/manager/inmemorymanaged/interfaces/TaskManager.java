package ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.interfaces;

import ru.yandex.javacource.korolyov.taskmanager.tasks.Epic;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Subtask;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Task;

import java.util.List;

public interface TaskManager {
    Integer addNewEpic(Epic epic);

    Integer addNewSubtask(Subtask subtask);

    Integer addNewTask(Task task);

    void deleteSubTask(int subtaskId);

    void deleteTask(int id);

    void deleteEpic(int id);

    void updateEpic(Epic epic);

    void updateSubTask(Subtask subtask);

    void updateTask(Task task);

    void deleteTasks();

    void deleteEpics();

    void deleteSubtasks();

    List<Epic> getEpics();

    List<Task> getTasks();

    List<Subtask> getSubtasks();

    List<Subtask> getEpicSubtasks(int epicId);

    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    List<Task> getHistory();
}
