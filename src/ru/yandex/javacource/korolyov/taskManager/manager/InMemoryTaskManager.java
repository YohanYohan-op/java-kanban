package ru.yandex.javacource.korolyov.taskManager.manager;

import ru.yandex.javacource.korolyov.taskManager.tasks.Epic;
import ru.yandex.javacource.korolyov.taskManager.tasks.Status;
import ru.yandex.javacource.korolyov.taskManager.tasks.Subtask;
import ru.yandex.javacource.korolyov.taskManager.tasks.Task;

import java.util.HashMap;
import java.util.ArrayList;

public class InMemoryTaskManager implements TaskManagerInt {

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    private int generatorId = 0;


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
    public int addNewTask(Task task) {
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
    public void updateEpic(Epic epic, int id) {
        Epic savedEpic = epics.get(id);
        if (savedEpic == null) {
            return;
        }
        savedEpic.setName(epic.getName());
        savedEpic.setDescription(epic.getDescription());
    }

    @Override
    public void updateSubTask(Subtask subtask, int id) {
        //int id = subtask.getId();
        Subtask savedSubtask = subtasks.get(id);
        if (savedSubtask == null) {
            return;
        }
        int epicId = savedSubtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }

        savedSubtask.setName(subtask.getName());
        savedSubtask.setDescription(subtask.getDescription());
        updateEpicStatus(epicId);
    }

    @Override
    public void updateTask(Task task, int id) {
        //int id = task.getId();
        Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        savedTask.setName(task.getName());
        savedTask.setDescription(task.getDescription());
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
    public ArrayList<Epic> getEpics() {
        //history.addAll(new ArrayList<>(epics.values()));
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        ArrayList<Subtask> getSubtasks = new ArrayList<>();
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
        historyManager.addHistory(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.addHistory(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        historyManager.addHistory(subtasks.get(id));
        return subtasks.get(id);
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

    public ArrayList<Task> getHistory() {
        return new ArrayList<>(historyManager.getHistory());
    }


}

