package ru.yandex.javacource.korolyov.taskManager.tasks;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subtaskIds = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public void cleanSubtaskIds(){
        subtaskIds.clear();
    }

    public ArrayList<Integer> getSubtaskIds(){
        return new ArrayList<>(subtaskIds);
    }
    public void removeSubtaskIds(Integer id){
        subtaskIds.remove(id);
    }
    public void addSubtaskId(Integer id){
        subtaskIds.add(id);
    }


}
