package ru.yandex.javacource.korolyov.taskmanager.manager;

import org.junit.jupiter.api.Test;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.FileBackedTaskManager;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.exceptions.IntersectionException;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.interfaces.TaskManager;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Epic;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Status;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Subtask;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {
    protected TaskManager taskManager = new FileBackedTaskManager(File.createTempFile("fba", ".txt"));

    TaskManagerTest() throws IOException {
    }

    @Test
    void shouldBeCreatedTask() {

        Task task = new Task("Test create", "Test createTask description", Status.NEW, Duration.ofMinutes(0), LocalDateTime.now());
        int taskId = taskManager.addNewTask(task);

        Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void shouldBeCreatedEpic() {

        Epic epic = new Epic("", "");
        int epicId = taskManager.addNewEpic(epic);

        Task savedEpic = taskManager.getEpic(epicId);

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");

        List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics, "Задачи не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(0), "Задачи не совпадают.");
    }

    @Test
    void shouldBeCreatedSubTask() {

        Epic epic = new Epic(" ", "  ");
        int epicId = taskManager.addNewEpic(epic);

        Subtask subTask = new Subtask("", "", epicId, Status.NEW, Duration.ofMinutes(0), LocalDateTime.now());
        int subTaskId = taskManager.addNewSubtask(subTask);

        Task savedSubTask = taskManager.getSubtask(subTaskId);

        assertNotNull(savedSubTask, "Задача не найдена.");
        assertEquals(subTask, savedSubTask, "Задачи не совпадают.");

        List<Subtask> subTasks = taskManager.getSubtasks();

        assertNotNull(subTasks, "Задачи не возвращаются.");
        assertEquals(1, subTasks.size(), "Неверное количество задач.");
        assertEquals(subTask, subTasks.get(0), "Задачи не совпадают.");
    }

    @Test
    public void shouldAddAngGetById() {
        Task task = new Task("Test create", "Test createTask description", Status.NEW, Duration.ofMinutes(0), LocalDateTime.now());
        int taskId = taskManager.addNewTask(task);
        Epic epic = new Epic("", "");
        int epicId = taskManager.addNewEpic(epic);
        Subtask subTask = new Subtask("", "", epicId, Status.NEW, Duration.ofMinutes(0), LocalDateTime.now().plusMinutes(1));
        int subTaskId = taskManager.addNewSubtask(subTask);

        assertEquals(task, taskManager.getTask(taskId), "Сохнаренные задачи не совпадают");
        assertEquals(epic, taskManager.getEpic(epicId), "Сохнаренные Эпики не совпадают");
        assertEquals(subTask, taskManager.getSubtask(subTaskId), "Сохнаренные СубТаски не совпадают");
    }

    @Test
    public void equalsTask() {
        Task task = new Task("Test create", "Test createTask description", Status.NEW, Duration.ofMinutes(0), LocalDateTime.now());
        int taskId = taskManager.addNewTask(task);

        Task savedTask = taskManager.getTask(taskId);

        assertEquals(task.getDescription(), savedTask.getDescription(), "По описанию.");
        assertEquals(task.getId(), savedTask.getId(), "По Id.");
        assertEquals(task.getName(), savedTask.getName(), "По названию.");
        assertEquals(task.getStatus(), savedTask.getStatus(), "По статусу.");
    }

    @Test
    public void equalsEpic() {
        Epic epic = new Epic("", "");
        int taskId = taskManager.addNewEpic(epic);
        Subtask subTask = new Subtask("", "", taskId, Status.NEW, Duration.ofMinutes(0), LocalDateTime.now());
        int subTaskId = taskManager.addNewSubtask(subTask);

        Epic savedEpic = taskManager.getEpic(taskId);

        assertEquals(epic.getDescription(), savedEpic.getDescription(), "По описанию.");
        assertEquals(epic.getId(), savedEpic.getId(), "По Id.");
        assertEquals(epic.getName(), savedEpic.getName(), "По названию.");
        assertEquals(epic.getStatus(), savedEpic.getStatus(), "По статусу.");
        assertEquals(epic.getSubtaskIds(), savedEpic.getSubtaskIds(), "По СубТаскам.");
    }

    @Test
    void EpicShouldChangeStatus() {
        int epic = taskManager.addNewEpic(new Epic("", ""));
        assertEquals(taskManager.getEpic(epic).getStatus(), Status.NEW, "Статус пустого эпикане NEW");
        taskManager.addNewSubtask(new Subtask("", "", epic, Status.NEW, Duration.ofMinutes(0), LocalDateTime.now()));
        taskManager.addNewSubtask(new Subtask("", "", epic, Status.NEW, Duration.ofMinutes(0), LocalDateTime.now().plusMinutes(1)));
        assertEquals(taskManager.getEpic(epic).getStatus(), Status.NEW, "статус эпика не NEW, с NEW субТасками");
        taskManager.deleteSubtasks();
        taskManager.addNewSubtask(new Subtask("", "", epic, Status.DONE, Duration.ofMinutes(0), LocalDateTime.now().plusMinutes(2)));
        taskManager.addNewSubtask(new Subtask("", "", epic, Status.DONE, Duration.ofMinutes(0), LocalDateTime.now().plusMinutes(3)));
        assertEquals(taskManager.getEpic(epic).getStatus(), Status.DONE, "статус эпика не DONE, с DONE субТасками");
        taskManager.deleteSubtasks();
        taskManager.addNewSubtask(new Subtask("", "", epic, Status.NEW, Duration.ofMinutes(0), LocalDateTime.now().plusMinutes(4)));
        taskManager.addNewSubtask(new Subtask("", "", epic, Status.DONE, Duration.ofMinutes(0), LocalDateTime.now().plusMinutes(5)));
        assertEquals(taskManager.getEpic(epic).getStatus(), Status.IN_PROGRESS, "статус эпика не IN_PROGRESS, с NEW и DONE субТасками");
        taskManager.deleteSubtasks();
        taskManager.addNewSubtask(new Subtask("", "", epic, Status.IN_PROGRESS, Duration.ofMinutes(0), LocalDateTime.now().plusMinutes(6)));
        taskManager.addNewSubtask(new Subtask("", "", epic, Status.IN_PROGRESS, Duration.ofMinutes(0), LocalDateTime.now().plusMinutes(7)));
        assertEquals(taskManager.getEpic(epic).getStatus(), Status.IN_PROGRESS, "статус эпика не IN_PROGRESS, с IN_PROGRESS субТасками");

    }

    @Test
    void shouldCheckPrioritization() {
        Task task = new Task("", "", Status.NEW, Duration.ofMinutes(10), LocalDateTime.of(2024, 7, 10, 15, 0));
        taskManager.addNewTask(task);
        Task task2 = new Task("", "", Status.NEW, Duration.ofMinutes(10), LocalDateTime.of(2024, 7, 10, 15, 5));
        assertThrows(IntersectionException.class, () -> taskManager.addNewTask(task2));
    }
}