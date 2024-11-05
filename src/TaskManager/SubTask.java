package TaskManager;

public class SubTask extends TaskManager {
    /*String name;
    String annotation;
    int track = 0;
    Status status;
    static int id;*/


    public SubTask(String name, String annotation) {
        this.name = name;
        this.annotation = annotation;
        ++track;
        this.status = Status.NEW;
    }

    //переопределить икуалс, хэшкод
}
