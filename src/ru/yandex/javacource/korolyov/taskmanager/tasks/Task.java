package ru.yandex.javacource.korolyov.taskmanager.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {


    private String name;
    private String description;
    private Status status;
    private Integer id;

    private LocalDateTime startTime;

    private Duration duration;

    public Task(String name, String description) {

        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }

    public Task(String name, String description, Integer id, Status status, LocalDateTime startTime) {

        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id;
        this.startTime = startTime;
    }

    public Task(String name, String description, Status status, Duration duration, LocalDateTime startTime) {

        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(String name, String description, Integer id, Status status, Duration duration, LocalDateTime startTime) {

        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id;
        this.startTime = startTime;
        this.duration = duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Duration getDuration() {
        if (duration != null) {
            return duration;
        } else {
            return Duration.ofMinutes(0);
        }
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public TaskTypes getType() {
        return TaskTypes.TASK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(status, task.status) &&
                Objects.equals(id, task.id);

    }

    @Override
    public int hashCode() {
        int hash = 17;
        if (name != null) {
            hash = hash + name.hashCode();
        }
        hash = hash * 31;
        if (description != null) {
            hash = hash + description.hashCode();
        }
        if (status != null) {
            hash = hash + status.hashCode();
        }
        hash = hash * 7;
        if (id != null) {
            hash = hash + id.hashCode();
        }
        hash = hash * 11;

        return hash;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id =" + id +
                ", name ='" + name + '\'' +
                ", description ='" + description + '\'' +
                ", status =" + status + '\'' +
                ", startTime =" + startTime + '\'' +
                ", Duration=" + duration.toMinutes() + '\'' +
                '}';
    }
}
