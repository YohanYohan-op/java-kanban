package ru.yandex.javacource.korolyov.taskmanager.tasks;

import java.util.List;
import java.util.ArrayList;

public class Epic extends Task {

    private List<Integer> subtaskIds = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public void cleanSubtaskIds(){
        subtaskIds.clear();
    }

    public List<Integer> getSubtaskIds(){
        return new ArrayList<>(subtaskIds);
    }
    public void removeSubtaskIds(Integer id){
        subtaskIds.remove(id);
    }
    public void addSubtaskId(Integer id){
        subtaskIds.add(id);
    }
    public void setAllSubtaskId(List<Integer> subs){
        subtaskIds = subs;
    }


}
