package ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged;

import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.exceptions.ManagerSaveException;
import ru.yandex.javacource.korolyov.taskmanager.tasks.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private static final String HEAD_STRING = "id,type,name,status,description,epic\n";
    private File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
        try {
            if (Files.exists(file.toPath())) {
                Files.delete(file.toPath());
            }
            Files.createFile(file.toPath());
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось найти файл для записи данных");
        }
        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            writer.write(HEAD_STRING);
        } catch (IOException e) {
            System.out.println("Что-то пошло не так при попытке добавить мейн строку в файл с тасками");
        }
    }

    @Override
    public Integer addNewEpic(Epic epic) {
        Integer id = super.addNewEpic(epic);
        save();
        return id;
    }

    @Override
    public Integer addNewSubtask(Subtask subtask) {
        Integer id = super.addNewSubtask(subtask);
        save();
        return id;
    }

    @Override
    public Integer addNewTask(Task task) {
        Integer id = super.addNewTask(task);
        save();
        return id;
    }

    private void save() {
        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8, true)) {
            for (Task task : getTasks()) {
                writer.write(toString(task) + "\n");
            }
            for (Epic epic : getEpics()) {
                writer.write(toString(epic) + "\n");
            }
            for (Subtask subtask : getSubtasks()) {
                writer.write(toString(subtask) + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось сохранить в файл");
        }

    }

    public void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String line;
            br.readLine(); //предположим, что в передаваемом файле первая строка всегда = HEAD_STRING
            while (br.ready()) {
                line = br.readLine();
                if (line.isBlank()) {
                    break;
                }
                Task task = fromString(line);

                if (task instanceof Epic) {
                    super.addNewEpic((Epic) task);
                } else if (task instanceof Subtask) {
                    super.addNewSubtask((Subtask) task);
                } else {
                    super.addNewTask(task);
                }

            }

        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось загрузить задачи");
        }
    }

    public String toString(Task task) {
        if (task instanceof Subtask) {
            return String.format("%s,%s,%s,%s,%s,%s", Integer.toString(task.getId()), TaskTypes.SUBTASK.toString(),
                    task.getName(), task.getStatus().toString(), task.getDescription(), ((Subtask) task).getEpicId());
        }
        if (task instanceof Epic) {
            return String.format("%s,%s,%s,%s,%s", task.getId(), TaskTypes.EPIC.toString(),
                    task.getName(), task.getStatus(), task.getDescription());
        }
        return String.format("%s,%s,%s,%s,%s", task.getId(), TaskTypes.TASK.toString(),
                task.getName(), task.getStatus(), task.getDescription());
    }

    public Task fromString(String task) {
        String[] tasks = task.split(",");
        switch (tasks[1]) {
            case "EPIC":
                switch (tasks[3]) {
                    case "NEW":
                        return new Epic(tasks[2], tasks[4], Integer.parseInt(tasks[0]), Status.NEW);
                    case "IN_PROGRESS":
                        return new Epic(tasks[2], tasks[4], Integer.parseInt(tasks[0]), Status.IN_PROGRESS);
                    case "DONE":
                        return new Epic(tasks[2], tasks[4], Integer.parseInt(tasks[0]), Status.DONE);
                }
            case "TASK":
                switch (tasks[3]) {
                    case "NEW":
                        return new Task(tasks[2], tasks[4], Integer.parseInt(tasks[0]), Status.NEW);
                    case "IN_PROGRESS":
                        return new Task(tasks[2], tasks[4], Integer.parseInt(tasks[0]), Status.IN_PROGRESS);
                    case "DONE":
                        return new Task(tasks[2], tasks[4], Integer.parseInt(tasks[0]), Status.DONE);
                }
            case "SUBTASK":
                switch (tasks[3]) {
                    case "NEW":
                        return new Subtask(tasks[2], tasks[4], Integer.parseInt(tasks[0]), Integer.parseInt(tasks[5]), Status.NEW);
                    case "IN_PROGRESS":
                        return new Subtask(tasks[2], tasks[4], Integer.parseInt(tasks[0]), Integer.parseInt(tasks[5]), Status.IN_PROGRESS);
                    case "DONE":
                        return new Subtask(tasks[2], tasks[4], Integer.parseInt(tasks[0]), Integer.parseInt(tasks[5]), Status.DONE);
                }
            default:
                return null;
        }
    }
}
