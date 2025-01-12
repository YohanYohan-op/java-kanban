package ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged;

import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.interfaces.HistoryManager;
import ru.yandex.javacource.korolyov.taskmanager.manager.Managers;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.interfaces.TaskManager;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Epic;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Status;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Subtask;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Task;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, Subtask> subtasks = new HashMap<>();
    protected final HistoryManager historyManager = Managers.getDefaultHistory();
    protected int generatorId = 0;


    @Override
    public Integer addNewEpic(Epic epic) {
        int id = ++generatorId;
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }

    @Override
    public Integer addNewSubtask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        }
        int id = ++generatorId;
        subtask.setId(id);
        subtasks.put(id, subtask);
        epic.addSubtaskId(subtask.getId());
        updateEpicStatus(epicId);
        return id;
    }

    @Override
    public Integer addNewTask(Task task) {
        int id = ++generatorId;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }


    @Override
    public void deleteSubTask(int subtaskId) {
        for (Epic epic : epics.values()) {
            for (Integer id : epic.getSubtaskIds()) {
                if (id == subtaskId) {
                    epic.removeSubtaskIds(id);
                    subtasks.remove(id);
                    updateEpicStatus(epic.getId());
                }
            }
        }
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic == null) {
            return;
        }
        for (Integer subtaskId : epic.getSubtaskIds()) {
            subtasks.remove(subtaskId);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        final Epic savedEpic = epics.get(epic.getId());
        if (savedEpic == null) {
            return;
        }
        epic.setAllSubtaskId(savedEpic.getSubtaskIds());
        epic.setStatus(savedEpic.getStatus());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateSubTask(Subtask subtask) {

        final Subtask savedSubtask = subtasks.get(subtask.getId());
        if (savedSubtask == null) {
            return;
        }
        int epicId = savedSubtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }
        subtask.setId(savedSubtask.getId());
        subtask.setStatus(savedSubtask.getStatus());
        subtask.setEpicId(savedSubtask.getEpicId());
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(epicId);

    }

    @Override
    public void updateTask(Task task) {

        final Task savedTask = tasks.get(task.getId());
        if (savedTask == null) {
            return;
        }
        task.setId(savedTask.getId());
        task.setStatus(savedTask.getStatus());
        tasks.put(task.getId(), task);
    }

    @Override
    public void deleteTasks() {
        tasks.clear();
    }

    @Override
    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        for (Epic epic : epics.values()) {
            epic.cleanSubtaskIds();
            updateEpicStatus(epic.getId());
        }
        subtasks.clear();
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Subtask> getEpicSubtasks(int epicId) {
        List<Subtask> getSubtasks = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        }
        for (int id : epic.getSubtaskIds()) {
            getSubtasks.add(subtasks.get(id));
        }
        return getSubtasks;
    }

    @Override
    public Task getTask(int id) {
        final Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        final Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        final Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyManager.getHistory());
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Subtask> statusChek = new ArrayList<>();
        int doneStatus = 0;
        int newStatus = 0;
        if (epic.getSubtaskIds().isEmpty()) {
            epic.setStatus(Status.NEW);
        } else {
            for (Integer id : new ArrayList<>(epic.getSubtaskIds())) {
                if (subtasks.containsKey(id)) {
                    statusChek.add(subtasks.get(id));
                    if (subtasks.get(id).getStatus().equals(Status.NEW)) {
                        newStatus++;
                    }
                    if (subtasks.get(id).getStatus().equals(Status.DONE)) {
                        doneStatus++;
                    }
                }
            }
        }
        if (epic.getSubtaskIds().size() < doneStatus && epic.getSubtaskIds().size() < newStatus) {
            epic.setStatus(Status.IN_PROGRESS);
        }
        if (epic.getSubtaskIds().size() == doneStatus) {
            epic.setStatus(Status.DONE);
        }
        if (epic.getSubtaskIds().size() == newStatus) {
            epic.setStatus(Status.NEW);
        }
    }

}

