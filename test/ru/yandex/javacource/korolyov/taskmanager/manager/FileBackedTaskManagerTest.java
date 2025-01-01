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
import java.util.ArrayList;
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
    void shouldCreateEmptyFileOnInitialization() throws IOException {
        assertTrue(tempFile.exists());
        for (String line : readFileContents()) {
            if (line.equals("id,type,name,status,description,epic")) {
                assertEquals("id,type,name,status,description,epic", line);
            }
        }
    }

    @Test
    void shouldAddAndSaveTaskToFile() throws IOException {
        Task task = new Task("Задача 1", "Описание задачи 1");
        manager.addNewTask(task);

        String expectedContent = "1,TASK,Задача 1,NEW,Описание задачи 1";

        for (String line : readFileContents()) {
            if (line.equals(expectedContent)) {
                assertEquals(expectedContent, line);
            }
        }
    }

    @Test
    void shouldAddAndSaveEpicToFile() throws IOException {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        manager.addNewEpic(epic);

        String expectedContent = "1,EPIC,Эпик 1,NEW,Описание эпика 1";

        for (String line : readFileContents()) {
            if (line.equals(expectedContent)) {
                assertEquals(expectedContent, line);
            }
        }
    }

    @Test
    void shouldAddAndSaveSubtaskToFile() throws IOException {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        manager.addNewEpic(epic);

        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", 1);
        subtask.setStatus(Status.IN_PROGRESS);
        manager.addNewSubtask(subtask);

        String expectedContent = "2,SUBTASK,Подзадача 1,IN_PROGRESS,Описание подзадачи 1,1";
        for (String line : readFileContents()) {
            if (line.equals(expectedContent)) {
                assertEquals(expectedContent, line);
            }
        }
    }

    @Test
    void shouldLoadTasksFromFile() throws IOException {
        // Запишем задачи в файл вручную
        writeToFile("1,TASK,Задача 1,NEW,Описание задачи 1");
        writeToFile("2,EPIC,Эпик 1,NEW,Описание эпика 1");
        writeToFile("3,SUBTASK,Подзадача 1,IN_PROGRESS,Описание подзадачи 1,2");

        manager.loadFromFile();

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

        Epic loadedEpic = epics.getFirst();
        assertEquals(2, loadedEpic.getId());
        assertEquals("Эпик 1", loadedEpic.getName());
        assertEquals(Status.NEW, loadedEpic.getStatus());
        assertEquals("Описание эпика 1", loadedEpic.getDescription());

        Subtask loadedSubtask = subtasks.getFirst();
        assertEquals(3, loadedSubtask.getId());
        assertEquals("Подзадача 1", loadedSubtask.getName());
        assertEquals(Status.IN_PROGRESS, loadedSubtask.getStatus());
        assertEquals("Описание подзадачи 1", loadedSubtask.getDescription());
        assertEquals(2, loadedSubtask.getEpicId());
    }

    private List<String> readFileContents() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(tempFile));
        List<String> stringsFromFile = new ArrayList<>();

        while (br.ready()) {
            stringsFromFile.add(br.readLine());
        }

        return stringsFromFile;
    }

    private void writeToFile(String content) throws IOException {
        try (FileWriter writer = new FileWriter(tempFile, StandardCharsets.UTF_8, true)) {
            writer.write(content + "\n");
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось сохранить в файл");
        }
    }
}
