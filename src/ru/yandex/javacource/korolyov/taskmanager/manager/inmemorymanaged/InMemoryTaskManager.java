package ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged;

import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.exceptions.IntersectionException;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.interfaces.HistoryManager;
import ru.yandex.javacource.korolyov.taskmanager.manager.Managers;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.interfaces.TaskManager;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Epic;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Status;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Subtask;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, Subtask> subtasks = new HashMap<>();
    protected final HistoryManager historyManager = Managers.getDefaultHistory();
    protected int generatorId = 0;
    private final static Comparator<Task> taskComparator = Comparator.comparing(Task::getStartTime);
    protected final Set<Task> prioritizedTasks = new TreeSet<>(taskComparator);


    @Override
    public Integer addNewEpic(Epic epic) {
        int id = ++generatorId;
        epic.setId(id);
        epics.put(id, epic);
        updateEpicStatus(epic.getId());
        return id;
    }

    @Override
    public Integer addNewSubtask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        }
        if (!checkIntersections(subtask)) {
            int id = ++generatorId;
            subtask.setId(id);
            subtasks.put(id, subtask);
            epic.addSubtaskId(subtask.getId());
            updateEpicStatus(epicId);
            changeEpicTiming(epic);
            addToPrioritizedTasks(subtask);
            return id;
        } else {
            throw new IntersectionException("Найдено пересечение во времени" + subtask.getStartTime().toString());
        }
    }

    @Override
    public Integer addNewTask(Task task) {
        if (!checkIntersections(task)) {
            int id = ++generatorId;
            task.setId(id);
            tasks.put(id, task);
            addToPrioritizedTasks(task);
            return id;
        } else {
            throw new IntersectionException("Найдено пересечение во времени" + task.getStartTime().toString());
        }
    }


    @Override
    public void deleteSubTask(int subtaskId) {
        for (Epic epic : epics.values()) {
            for (Integer id : epic.getSubtaskIds()) {
                if (id == subtaskId) {
                    epic.removeSubtaskIds(id);
                    prioritizedTasks.remove(subtasks.get(id));
                    subtasks.remove(id);
                    updateEpicStatus(epic.getId());
                    changeEpicTiming(epic);
                }
            }
        }
    }

    @Override
    public void deleteTask(int id) {
        prioritizedTasks.remove(tasks.get(id));
        tasks.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic == null) {
            return;
        }
        for (Integer subtaskId : epic.getSubtaskIds()) {
            prioritizedTasks.remove(subtasks.get(subtaskId));
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
        epic.setStartTime(savedEpic.getStartTime());

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
        subtask.setStartTime(savedSubtask.getStartTime());
        prioritizedTasks.removeIf(prioritizedTask -> prioritizedTask.equals(subtask));
        addToPrioritizedTasks(subtask);
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(epicId);
        changeEpicTiming(epics.get(subtask.getEpicId()));

    }

    @Override
    public void updateTask(Task task) {

        final Task savedTask = tasks.get(task.getId());
        if (savedTask == null) {
            return;
        }
        task.setId(savedTask.getId());
        task.setStatus(savedTask.getStatus());
        task.setStartTime(savedTask.getStartTime());
        prioritizedTasks.removeIf(prioritizedTask -> prioritizedTask.equals(task));
        addToPrioritizedTasks(task);
        tasks.put(task.getId(), task);
    }

    @Override
    public void deleteTasks() {
        for (Integer task : tasks.keySet()) {
            prioritizedTasks.remove(tasks.get(task));
        }
        tasks.clear();
    }

    @Override
    public void deleteEpics() {
        for (Integer subTask : subtasks.keySet()) {
            prioritizedTasks.remove(subtasks.get(subTask));
        }
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        for (Epic epic : epics.values()) {
            epic.cleanSubtaskIds();
            updateEpicStatus(epic.getId());
        }
        for (Integer subTask : subtasks.keySet()) {
            prioritizedTasks.remove(subtasks.get(subTask));
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
        if (epic.getSubtaskIds().size() < doneStatus || newStatus > 0) {
            epic.setStatus(Status.IN_PROGRESS);
        }
        if (epic.getSubtaskIds().size() == doneStatus) {
            epic.setStatus(Status.DONE);
        }
        if (epic.getSubtaskIds().size() == newStatus) {
            epic.setStatus(Status.NEW);
        }
        if (epic.getSubtaskIds().size() != doneStatus + newStatus) {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    protected void changeEpicTiming(Epic epic) {
        if (epic.getSubtaskIds().isEmpty()) {
            epic.setDuration(Duration.ofMinutes(0));
            epic.setStartTime(null);
            epic.setEndTime(null);
            return;
        }
        epic.setStartTime(subtasks.get(epic.getSubtaskIds().getFirst()).getStartTime());
        epic.setEndTime(subtasks.get(epic.getSubtaskIds().getFirst()).getEndTime());
        Duration epicDuration = Duration.ofMinutes(0);
        for (Integer id : epic.getSubtaskIds()) {
            if (epic.getStartTime().isAfter(subtasks.get(id).getStartTime())) {
                epic.setStartTime(subtasks.get(id).getStartTime());
            }
            if (epic.getEndTime().isBefore((subtasks.get(id).getEndTime()))) {
                epic.setEndTime(subtasks.get(id).getEndTime());
            }
            epicDuration = epicDuration.plus(subtasks.get(id).getDuration());
        }
        epic.setDuration(epicDuration);
    }

    protected void addToPrioritizedTasks(Task task) {
        prioritizedTasks.add(task);
    }

    protected boolean checkIntersections(Task task) {

        LocalDateTime startOfTask = task.getStartTime();
        LocalDateTime endOfTask = task.getEndTime();


        return prioritizedTasks.stream()
                .filter(prioritizedTask -> prioritizedTask.getStartTime() != null)
                .filter(prioritizedTask -> !prioritizedTask.equals(task))
                .anyMatch(prioritizedTask ->

                        (prioritizedTask.getStartTime().isEqual(startOfTask) || prioritizedTask.getStartTime().isBefore(startOfTask)) && prioritizedTask.getEndTime().isAfter(startOfTask)
                                || (startOfTask.isEqual(prioritizedTask.getStartTime()) || startOfTask.isBefore(prioritizedTask.getStartTime())) && endOfTask.isAfter(prioritizedTask.getStartTime())

                );

    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return prioritizedTasks.stream().toList();
    }

}

