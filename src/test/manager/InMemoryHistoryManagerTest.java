package manager;


import org.junit.jupiter.api.Test;
import ru.yandex.javacource.korolyov.taskManager.manager.InMemoryTaskManager;
import ru.yandex.javacource.korolyov.taskManager.tasks.Subtask;
import ru.yandex.javacource.korolyov.taskManager.tasks.Task;
import ru.yandex.javacource.korolyov.taskManager.tasks.Epic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class InMemoryHistoryManagerTest {

    InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @Test
    public void getHistoryShouldReturnListOf10Tasks() {

        for (int i = 0; i < 20; i++) {
            taskManager.addNewTask(new Task("Some name", "Some description"));
        }

        List<Task> tasks = taskManager.getTasks();
        for (Task task : tasks) {
            taskManager.getTask(task.getId());
        }

        List<Task> list = taskManager.getHistory();
        assertEquals(10, list.size(), "Неверное количество элементов в истории ");
    }

    @Test
    public void getHistoryShouldReturnOldTaskAfterUpdate() {
        Task task1 = new Task("12", "13");
        taskManager.addNewTask(task1);
        taskManager.getTask(task1.getId());
        taskManager.updateTask(new Task("Ye ye", "okey"), task1.getId());
        List<Task> tasks = taskManager.getHistory();
        Task oldTask = tasks.getFirst();
        assertEquals(task1.getName(), oldTask.getName(), "В истории не сохранилась старая версия задачи");
        assertEquals(task1.getDescription(), oldTask.getDescription(),
                "В истории не сохранилась старая версия задачи");

    }

    @Test
    public void getHistoryShouldReturnOldEpicAfterUpdate() {
        Epic epic1 = new Epic("12", "13");
        taskManager.addNewEpic(epic1);
        taskManager.getEpic(epic1.getId());
        taskManager.updateEpic(new Epic("Новое имя", "новое описание"), epic1.getId());
        List<Task> epics = taskManager.getHistory();
        Epic oldEpic = (Epic) epics.getFirst();
        assertEquals(epic1.getName(), oldEpic.getName(),
                "В истории не сохранилась старая версия эпика");
        assertEquals(epic1.getDescription(), oldEpic.getDescription(),
                "В истории не сохранилась старая версия эпика");
    }

    @Test
    public void getHistoryShouldReturnOldSubtaskAfterUpdate() {
        Epic epic1 = new Epic("12", "13");
        taskManager.addNewEpic(epic1);
        Subtask subtask1 = new Subtask("Новая субтаска", "Описание",
                epic1.getId());
        taskManager.addNewSubtask(subtask1);
        taskManager.getSubtask(subtask1.getId());
        taskManager.updateSubTask(new Subtask("Новое имя",
                "новое описание", epic1.getId()), subtask1.getId());
        List<Task> subtasks = taskManager.getHistory();
        Subtask oldSubtask = (Subtask) subtasks.getFirst();
        assertEquals(subtask1.getName(), oldSubtask.getName(),
                "В истории не сохранилась старая версия эпика");
        assertEquals(subtask1.getDescription(), oldSubtask.getDescription(),
                "В истории не сохранилась старая версия эпика");
    }
}