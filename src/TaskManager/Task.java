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
                Objects.equals(status, task.status);

    }

    @Override // не забываем об аннотации
    public int hashCode() {
        int hash = 17; // объявляем и инициализируем переменную hash
        if (name != null) { // проверяем значение первого поля
            hash = name.hashCode(); // вычисляем хеш первого поля
        }
        hash = hash * 31;
        if (annotation != null) { // проверяем значение второго поля
            hash = hash + annotation.hashCode(); // вычисляем хеш второго поля и общий хеш
        }
        hash = hash * 31;
        if (status != null) { // проверяем значение второго поля
            hash = hash + status.hashCode(); // вычисляем хеш второго поля и общий хеш
        }


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
