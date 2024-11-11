package ru.yandex.javacource.korolyov.taskManager.tasks;

public class Subtask extends Task {

    private final Integer epicId;

    public Subtask(String name, String description, Integer epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Integer getEpicId(){
        return epicId;
    }


}
