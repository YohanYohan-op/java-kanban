package ru.yandex.javacource.korolyov.taskmanager.tasks;

public class Subtask extends Task {

    private Integer epicId;

    public Subtask(String name, String description, Integer epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Integer getEpicId(){
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

}
