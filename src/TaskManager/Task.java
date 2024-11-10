package TaskManager;

import java.util.Objects;

public class Task {

    String name;
    String annotation;
    Status status;
    static Integer x = 1;
    int id;

    public int getId() {
        return id;
    }


    @Override
    public boolean equals(Object o) { // метод equals переопределён
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) &&
                Objects.equals(annotation, task.annotation) &&
                Objects.equals(status, task.status) &&
                Objects.equals(id, task.id);

    }

    @Override // не забываем об аннотации
    public int hashCode() {
        int hash = 17;
        if (name != null){
            hash = hash + name.hashCode();
        }
        hash = hash * 31;
        if(annotation != null){
            hash = hash + annotation.hashCode();
        }
        hash = hash * id;

        return hash; // возвращаем хеш
    }

    @Override
    public String toString() {
        return "Задача {" +
                "name='" + name + '\'' +
                ", annotation='" + annotation + '\'' +
                ", status='"+status +'}';
    }
}
