package TaskManager;

public class EpicTask extends TaskManager {
    /*String name;
    String annotation;
    int track = 0;
    Status status;
    static int id;*/

    public EpicTask(String name, String annotation) {
        this.name = name;
        this.annotation = annotation;
        ++track;
        this.status = Status.NEW;
    }
    //переопределил икуалс, хэшкод
}
