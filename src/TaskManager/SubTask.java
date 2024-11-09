package TaskManager;

public class SubTask extends Task {

    private final int id;
    public SubTask(String name, String annotation) {

        this.name = name;
        this.annotation = annotation;
        this.status = Status.NEW;
        this.id = Task.x;
        Task.x++;
    }

    public void changeStatus(Status status){
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void changeName(SubTask subTask) {
        this.name = subTask.name;
        this.annotation = subTask.annotation;
    }


}
