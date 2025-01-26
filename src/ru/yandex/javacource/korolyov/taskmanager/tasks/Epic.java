package ru.yandex.javacource.korolyov.taskmanager.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Epic extends Task {

    private List<Integer> subtaskIds = new ArrayList<>();

    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(String name, String description, LocalDateTime startTime) {
        super(name, description, startTime);
    }

    public TaskTypes getType() {
        return TaskTypes.EPIC;
    }

    public Epic(String name, String description, Integer id, Status status, LocalDateTime startTime) {
        super(name, description, id, status, startTime);
    }

    public Epic(String name, String description, Integer id, Status status, Duration duration, LocalDateTime startTime) {
        super(name, description, id, status, duration, startTime);
    }

    public void cleanSubtaskIds() {
        subtaskIds.clear();
    }

    public List<Integer> getSubtaskIds() {
        return new ArrayList<>(subtaskIds);
    }

    public void removeSubtaskIds(Integer id) {
        subtaskIds.remove(id);
    }

    public void addSubtaskId(Integer id) {
        subtaskIds.add(id);
    }

    public void setAllSubtaskId(List<Integer> subs) {
        subtaskIds = subs;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }


}
