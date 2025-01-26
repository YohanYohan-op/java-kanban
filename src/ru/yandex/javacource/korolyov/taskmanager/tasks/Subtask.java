package ru.yandex.javacource.korolyov.taskmanager.tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {

    private Integer epicId;

    public Subtask(String name, String description, Integer epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, Integer epicId, LocalDateTime startTime) {
        super(name, description, startTime);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, Integer id, Integer epicId, Status status, LocalDateTime startTime) {
        super(name, description, id, status, startTime);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, Integer epicId, Status status, Duration duration, LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, Integer id, Integer epicId, Status status, Duration duration, LocalDateTime startTime) {
        super(name, description, id, status, duration, startTime);
        this.epicId = epicId;
    }

    public TaskTypes getType() {
        return TaskTypes.SUBTASK;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

}
