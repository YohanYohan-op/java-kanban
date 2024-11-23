package manager;

import org.junit.jupiter.api.Test;


import ru.yandex.javacource.korolyov.taskManager.manager.InMemoryTaskManager;
import ru.yandex.javacource.korolyov.taskManager.tasks.Epic;
import ru.yandex.javacource.korolyov.taskManager.tasks.Subtask;
import ru.yandex.javacource.korolyov.taskManager.tasks.Task;

import static org.junit.jupiter.api.Assertions.*;


class InMemoryTaskManagerTest {

    @Test
    public void AnyTasksShouldBeAddedToListsAndGotBack(){
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        taskManager.addNewTask(new Task("1", "1"));
        taskManager.addNewTask(new Task("1", "2"));
        taskManager.addNewEpic(new Epic("1.0", "1.0"));
        taskManager.addNewEpic(new Epic("2.0", "2.0"));
        taskManager.addNewSubtask(new Subtask("1.1", "1.1", 3));
        taskManager.addNewSubtask(new Subtask("1.2", "1.2", 3));
        taskManager.addNewSubtask(new Subtask("2.2", "2.2", 4));

        assertEquals(taskManager.getTask(2).getDescription(), "2",
              "Ошибка! input и output не совпадают");

        assertEquals(taskManager.getEpic(4).getDescription(), "2.0",
                "Ошибка! input и output не совпадают");

        assertEquals(taskManager.getSubtask(5).getEpicId(), 3,
                "Ошибка! input и output не совпадают");

        assertEquals(taskManager.getSubtask(7).getEpicId(), 4,
                "Ошибка! input и output не совпадают");
    }

}