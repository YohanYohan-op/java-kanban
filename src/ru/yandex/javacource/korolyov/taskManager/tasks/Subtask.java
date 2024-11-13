package ru.yandex.javacource.korolyov.taskManager.tasks;

public class Subtask extends Task {

    private Integer epicId; //Финал я использовал потому, что id с задачей останется навсегда и не вижу ситуаций при которой потребуется замена, но поправил:)

    public Subtask(String name, String description, Integer epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Integer getEpicId(){
        return epicId;
    }


}
