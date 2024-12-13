package ru.yandex.javacource.korolyov.taskmanager;


import ru.yandex.javacource.korolyov.taskmanager.manager.InMemoryTaskManager;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Task;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        for (int i = 0; i < 20; i++) {
            taskManager.addNewTask(new Task("Some name", "Some description"));
        }

        List<Task> tasks = taskManager.getTasks();
        for (Task task : tasks) {
            taskManager.getTask(task.getId());
        }

        List<Task> list = taskManager.getHistory(); //Почему возвращается пустой лист? Помоги пожалуйста разобраться


    }
}