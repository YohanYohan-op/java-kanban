package ru.yandex.javacource.korolyov.taskmanager.tasks;

public class Subtask extends Task {

    private Integer epicId;

    public Subtask(String name, String description, Integer epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, Integer id, Integer epicId, Status status) {
        super(name, description, id, status);
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
