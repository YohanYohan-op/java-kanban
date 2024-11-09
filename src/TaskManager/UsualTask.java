package TaskManager;

public class UsualTask extends Task{
    private final int id;

    public UsualTask(String name, String annotation) {

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

    public void changeName(UsualTask usualTask) {
        this.name = usualTask.name;
        this.annotation = usualTask.annotation;
    }
}
