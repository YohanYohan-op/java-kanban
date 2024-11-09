package TaskManager;

public class EpicTask extends Task {

    private final int id;

    public EpicTask(String name, String annotation) {

        this.name = name;
        this.annotation = annotation;
        this.id = Task.x;
        Task.x++;
        this.status = Status.NEW;
    }

    public void changeStatus(Status status){
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void changeName(EpicTask epicTask) {
        this.name = epicTask.name;
        this.annotation = epicTask.annotation;
    }
}
