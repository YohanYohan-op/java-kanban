package ru.yandex.javacource.korolyov.taskManager;


import ru.yandex.javacource.korolyov.taskManager.tasks.Epic;
import ru.yandex.javacource.korolyov.taskManager.tasks.Subtask;
import ru.yandex.javacource.korolyov.taskManager.tasks.Task;
import ru.yandex.javacource.korolyov.taskManager.manager.TaskManager;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        Epic epic = new Epic("Name1", "desc");
        taskManager.addNewEpic(epic);

        Epic epic1 = new Epic("Name2", "desc2");
        taskManager.addNewEpic(epic1);

        Task task = new Task("Name3", "desc3");
        taskManager.addNewTask(task);

        Task task2 = new Task("Name4", "desc4");
        taskManager.addNewTask(task2);

        Subtask subtask = new Subtask("Name5", "desc5", epic.getId());
        taskManager.addNewSubtask(subtask);

        Subtask subtask2 = new Subtask("Name6", "desc6", epic.getId());
        taskManager.addNewSubtask(subtask2);

        Subtask subtask3 = new Subtask("Name7", "desc7", epic1.getId());
        taskManager.addNewSubtask(subtask3);

        taskManager.getTasks();
        taskManager.getSubtasks();
        taskManager.getEpics();
        taskManager.getEpicSubtasks(epic1.getId());

        taskManager.getEpic(epic.getId());
        taskManager.getTask(task.getId());
        taskManager.getSubtask(subtask.getId());

        taskManager.deleteEpic(epic1.getId());
        taskManager.deleteSubTask(subtask2.getId());
        taskManager.deleteTask(task2.getId());

        taskManager.updateTask(task);
        taskManager.updateEpic(epic);
        taskManager.updateSubTask(subtask);

        taskManager.deleteTasks();
        taskManager.deleteEpics();
        taskManager.deleteSubtasks();


    }
}