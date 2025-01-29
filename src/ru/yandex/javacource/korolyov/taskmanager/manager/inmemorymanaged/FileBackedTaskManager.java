package ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged;

import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.exceptions.ManagerSaveException;
import ru.yandex.javacource.korolyov.taskmanager.tasks.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private static final String HEADER = "id,type,name,status,description,epic,duration,startTime";
    private File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
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

    @Override
    public void deleteSubTask(int subtaskId) {
        super.deleteSubTask(subtaskId);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubTask(Subtask subtask) {
        super.updateSubTask(subtask);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public void deleteSubtasks() {
        super.deleteSubtasks();
        save();
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        final FileBackedTaskManager taskManager = new FileBackedTaskManager(file);
        taskManager.generatorId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String line;
            while (br.ready()) {
                line = br.readLine();
                if (line.isBlank()) {
                    break;
                }
                Task task = fromString(line);
                taskManager.addAnyTask(task);
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Can't read form file: " + file.getName(), e);
        }
        for (Map.Entry<Integer, Subtask> e : taskManager.subtasks.entrySet()) {
            final Subtask subtask = e.getValue();
            final Epic epic = taskManager.epics.get(subtask.getEpicId());
            epic.addSubtaskId(subtask.getId());
        }

        return taskManager;
    }

    public static String toString(Task task) {
        return task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription()
                + "," + (task.getType().equals(TaskTypes.SUBTASK) ? ((Subtask) task).getEpicId() : "") + ","
                + (task.getDuration().isZero() ? "" : String.valueOf(task.getDuration().toMinutes()))
                + "," + task.getStartTime();
    }


    protected void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(HEADER);
            writer.newLine();


            for (Map.Entry<Integer, Task> entry : tasks.entrySet()) { //
                final Task task = entry.getValue();
                writer.write(toString(task));
                writer.newLine();
            }

            for (Map.Entry<Integer, Subtask> entry : subtasks.entrySet()) {
                final Task task = entry.getValue();
                writer.write(toString(task));
                writer.newLine();
            }

            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                final Task task = entry.getValue();
                writer.write(toString(task));
                writer.newLine();
            }

            writer.newLine();
        } catch (IOException e) {
            throw new ManagerSaveException("Can't save to file: " + file.getName(), e);
        }
    }

    protected void addAnyTask(Task task) {
        final int id = task.getId();
        switch (task.getType()) {
            case TASK:
                tasks.put(id, task);
                addToPrioritizedTasks(task);
                generatorId++;
                break;
            case SUBTASK:
                subtasks.put(id, (Subtask) task);
                addToPrioritizedTasks(task);
                generatorId++;
                break;
            case EPIC:
                epics.put(id, (Epic) task);
                generatorId++;
                break;
        }
    }

    private static Task fromString(String value) {
        final String[] values = value.split(",");
        final int id = Integer.parseInt(values[0]);
        final TaskTypes type = TaskTypes.valueOf(values[1]);
        final String name = values[2];
        final Status status = Status.valueOf(values[3]);
        final String description = values[4];
        if (type == TaskTypes.TASK) {
            if (values.length == 6) {
                return new Task(name, description, id, status, LocalDateTime.parse(values[5]));
            }
            return new Task(name, description, id, status, Duration.ofMinutes(Long.parseLong(values[5])), LocalDateTime.parse(values[6]));
        }
        if (type == TaskTypes.SUBTASK) {
            final int epicId = Integer.parseInt(values[5]);
            if (values.length == 7) {
                return new Subtask(name, description, id, epicId, status, LocalDateTime.parse(values[6]));
            }
            return new Subtask(name, description, id, epicId, status, Duration.ofMinutes(Long.parseLong(values[6])), LocalDateTime.parse(values[7]));
        }
        if (values.length == 6) {
            return new Epic(name, description, id, status, LocalDateTime.parse(values[5]));
        }
        return new Epic(name, description, id, status, Duration.ofMinutes(Long.parseLong(values[5])), LocalDateTime.parse(values[6]));

    }
}
