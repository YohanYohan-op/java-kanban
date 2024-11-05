package TaskManager;

import java.util.HashMap;
import java.util.ArrayList;

public class Task {
    private HashMap<EpicTask, ArrayList<SubTask>> task = new HashMap<>();



    public void setEpicTask(EpicTask epicTask) {
        for (EpicTask search : task.keySet()) {
            if (epicTask.equals(search)){
                System.out.println("zadacha yzhe sozdana");
            } else {
                task.put(epicTask, new ArrayList<>());
            }
        }
    }

    public void setSubTask(EpicTask epicTask, SubTask subTask) {

        for (EpicTask search : task.keySet()) {
            if (epicTask.equals(search)){
                ArrayList<SubTask> subTasks = task.get(epicTask);
                subTasks.add(subTask);
                task.put(epicTask,subTasks);
            } else {
                System.out.println("Чтобы добавить подзадачу - добавьте сначала задачу");
            }
        }
    }



    public void clearSubTasks(EpicTask epicTask, SubTask subTask){
        for (EpicTask search : task.keySet()) {
            if (epicTask.equals(search)){
                ArrayList<SubTask> subTasks = task.get(epicTask);
                for (SubTask task1 : subTasks) {
                    if(task1.equals(subTask)){
                        subTasks.remove(subTask);
                        task.put(epicTask, subTasks);
                    } else {
                        System.out.println("Subtaska ne naedena");
                    }
                }
            } else {
                System.out.println("EpicTask ne naeden");
            }
        }
        //метод удаления субтасков в епике
        //
    }

    public void deleteEpicTask (EpicTask epicTask){
        for (EpicTask search : task.keySet()) {
            if (epicTask.equals(search)){
                task.remove(epicTask);
            } else {
                System.out.println("EpicTaski ne naedeno");
            }
        }
        //метод удаления Епика
        //Перебор ключей, находим нужный и удаляем
    }

    public void updateEpicTask(EpicTask epicTask){
        //найти и заменить, добавить логику проверку сабтасков на статус
    }

    public void updateSubTask(SubTask subTask){
        //найти и заменить
    }

    // возможно на каждый статус добавить по методу на изменение статуса задач

    // метод удаления всех эпиков

    //метод удаления всех саб тасков

    //метод удаления

}
