package ru.yandex.javacource.korolyov.taskmanager.manager;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.FileBackedTaskManager;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.exceptions.ManagerSaveException;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Epic;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Status;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Subtask;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class FileBackedTaskManagerTest {
    private File tempFile;
    private FileBackedTaskManager manager;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("file-backed-test", ".txt");
        manager = new FileBackedTaskManager(tempFile);
    }

    @AfterEach
    void tearDown() {
        tempFile.delete();
    }

    @Test
    void shouldLoadTasksFromFile() throws IOException {
        // Запишем задачи в файл вручную
        tempFile = File.createTempFile("fba", ".txt");
        writeToFile("1,TASK,Задача 1,NEW,Описание задачи 1,235,2007-12-03T10:15:30");
        writeToFile("2,EPIC,Эпик 1,NEW,Описание эпика 1,178,2009-12-03T10:15:30");
        writeToFile("3,SUBTASK,Подзадача 1,IN_PROGRESS,Описание подзадачи 1,2,222,2011-12-03T10:15:30");

        manager = FileBackedTaskManager.loadFromFile(tempFile);

        List<Task> tasks = manager.getTasks();
        List<Epic> epics = manager.getEpics();
        List<Subtask> subtasks = manager.getSubtasks();

        assertEquals(1, tasks.size());
        assertEquals(1, epics.size());
        assertEquals(1, subtasks.size());

        Task loadedTask = tasks.getFirst();
        assertEquals(1, loadedTask.getId());
        assertEquals("Задача 1", loadedTask.getName());
        assertEquals(Status.NEW, loadedTask.getStatus());
        assertEquals("Описание задачи 1", loadedTask.getDescription());
        assertEquals(235, loadedTask.getDuration().toMinutes());
        assertEquals(LocalDateTime.parse("2007-12-03T10:15:30"), loadedTask.getStartTime());

        Epic loadedEpic = epics.getFirst();
        assertEquals(2, loadedEpic.getId());
        assertEquals("Эпик 1", loadedEpic.getName());
        assertEquals(Status.NEW, loadedEpic.getStatus());
        assertEquals("Описание эпика 1", loadedEpic.getDescription());
        assertEquals(178, loadedEpic.getDuration().toMinutes());
        assertEquals(LocalDateTime.parse("2009-12-03T10:15:30"), loadedEpic.getStartTime());

        Subtask loadedSubtask = subtasks.getFirst();
        assertEquals(3, loadedSubtask.getId());
        assertEquals("Подзадача 1", loadedSubtask.getName());
        assertEquals(Status.IN_PROGRESS, loadedSubtask.getStatus());
        assertEquals("Описание подзадачи 1", loadedSubtask.getDescription());
        assertEquals(2, loadedSubtask.getEpicId());
        assertEquals(222, loadedSubtask.getDuration().toMinutes());
        assertEquals(LocalDateTime.parse("2011-12-03T10:15:30"), loadedSubtask.getStartTime());

    }

    private void writeToFile(String content) {
        try (FileWriter writer = new FileWriter(tempFile, StandardCharsets.UTF_8, true)) {
            writer.write(content + "\n");
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось сохранить в файл", e);
        }
    }
}
