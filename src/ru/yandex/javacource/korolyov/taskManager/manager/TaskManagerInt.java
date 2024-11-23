package ru.yandex.javacource.korolyov.taskManager.manager;

import ru.yandex.javacource.korolyov.taskManager.tasks.Epic;
import ru.yandex.javacource.korolyov.taskManager.tasks.Subtask;
import ru.yandex.javacource.korolyov.taskManager.tasks.Task;

import java.util.ArrayList;

public interface TaskManagerInt {
    Integer addNewEpic(Epic epic);

    Integer addNewSubtask(Subtask subtask);

    int addNewTask(Task task);

    void deleteSubTask(int subtaskId);

    void deleteTask(int id);

    void deleteEpic(int id);

    void updateEpic(Epic epic, int id);

    void updateSubTask(Subtask subtask, int id);

    void updateTask(Task task, int id);

    void deleteTasks();

    void deleteEpics();

    void deleteSubtasks();

    ArrayList<Epic> getEpics();

    ArrayList<Task> getTasks();

    ArrayList<Subtask> getSubtasks();

    ArrayList<Subtask> getEpicSubtasks(int epicId);

    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);
}
