package ru.yandex.javacource.korolyov.taskManager.tasks;

import java.util.Objects;

public class Task {

    private String name;
    private String description;
    private Status status;
    private Integer id;

    public Task(String name, String description) {

        this.name = name;
        this.description = description;
        this.status = Status.NEW;
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
        if (name != null){
            hash = hash + name.hashCode();
        }
        hash = hash * 31;
        if(description != null){
            hash = hash + description.hashCode();
        }
        if(status != null) {
            hash = hash + status.hashCode();
        }
        hash = hash * 7;
        if (id != null){
            hash = hash + id.hashCode();
        }
        hash = hash * 11;

        return hash;
    }

    @Override
    public String toString() {
        return "Задача {" +
                "name='" + name + '\'' +
                ", annotation='" + description + '\'' +
                ", status='"+status +'\''+
                ", id=" + id + '}';
    }
}
