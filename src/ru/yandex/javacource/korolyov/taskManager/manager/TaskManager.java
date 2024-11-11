package ru.yandex.javacource.korolyov.taskManager.manager;

import ru.yandex.javacource.korolyov.taskManager.tasks.Epic;
import ru.yandex.javacource.korolyov.taskManager.tasks.Subtask;
import ru.yandex.javacource.korolyov.taskManager.tasks.Task;

import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private int generatorId = 0;


    public Integer addNewEpic(Epic epic) {
        int id = ++generatorId;
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }

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

    public int addNewTask(Task task) {
        int id = ++generatorId;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }


     public void deleteSubTask(int subtaskId) {
        for (Epic epic : epics.values()){
            for (Integer id : epic.getSubtaskIds()){
                if (id==subtaskId){
                    epic.removeSubtaskIds(id);
                    subtasks.remove(id);
                    updateEpicStatus(epic.getId());
                }
            }
        }
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public void deleteEpicTask(int epicId) {
        for (Integer id : epics.keySet()) {
            if (id == epicId) {
                Epic epic = epics.get(id);
                epic.cleanSubtaskIds();
                epics.remove(epicId);
            }
        }
    }

    public void updateEpicTask(Epic epic) {
        int id = epic.getId();
        Epic savedEpic = epics.get(id);
        if (savedEpic == null) {
            return;
        }
        tasks.put(id, epic);
    }

    public void updateSubTask(Subtask subTask) {
        int id = subTask.getId();
        Subtask savedSubTask = subtasks.get(id);
        if (savedSubTask == null) {
            return;
        }
        for (Epic epic : epics.values()){
            for (Integer value : epic.getSubtaskIds()){
                if (id==value){
                    subtasks.put(id, subTask);
                    epic.addSubtaskId(id);
                }
            }
        }

    }

    public void updateTask(Task task) {
        int id = task.getId();
        Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        tasks.put(id, task);
    }

    public void deleteTasks() {
        tasks.clear();
    }

    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void deleteSubtasks() {
        for (Epic epic : epics.values()) {
            epic.cleanSubtaskIds();
            updateEpicStatus(epic.getId());
        }
        subtasks.clear();
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        ArrayList<Subtask> getSubtasks = new ArrayList<>();
        for (Epic epic : epics.values()){
            if (epicId == epic.getId()){
                for (Integer id : epic.getSubtaskIds()) {
                    if(subtasks.containsKey(id)){
                        getSubtasks.add(subtasks.get(id));
                    }
                }
            }
        }
        return getSubtasks;
    }
    public Task getTask(int id){
        if(!tasks.containsKey(id)){
            return null;
        }
        return tasks.get(id);
    }
    public Epic getEpic(int id){
        if(!epics.containsKey(id)){
            return null;
        }
        return epics.get(id);
    }
    public Subtask getSubtask(int id){
        if(!subtasks.containsKey(id)){
            return null;
        }
        return subtasks.get(id);
    }
    public Epic getEpicForSubtask(int id){
        if(!subtasks.containsKey(id)){
            return null;
        }
        return epics.get(subtasks.get(id).getEpicId());
    }
    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Subtask> statusChek = new ArrayList<>();
        int doneStatus = 0;
        int newStatus = 0;
        if (epic.getSubtaskIds().isEmpty()){
            epic.setStatus(Status.NEW);
        } else {
            for (Integer id : new ArrayList<>(epic.getSubtaskIds())) {
                if (subtasks.containsKey(id)) {
                    statusChek.add(subtasks.get(id));
                    if (subtasks.get(id).getStatus().equals(Status.NEW)){
                        newStatus++;
                    }
                    if (subtasks.get(id).getStatus().equals(Status.DONE)){
                        doneStatus++;
                    }
                }
            }
        }
        if (epic.getSubtaskIds().size() < doneStatus && epic.getSubtaskIds().size() < newStatus){
            epic.setStatus(Status.IN_PROGRESS);
        }
        if (epic.getSubtaskIds().size() == doneStatus){
            epic.setStatus(Status.DONE);
        }
        if (epic.getSubtaskIds().size() == newStatus){
            epic.setStatus(Status.NEW);
        }
    }

}
