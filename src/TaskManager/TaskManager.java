package TaskManager;

import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {


    private HashMap<EpicTask, ArrayList<SubTask>> task = new HashMap<>();
    private ArrayList<UsualTask> usualTasks = new ArrayList<>();


    public void setEpicTask(EpicTask epicTask) {

        if (task.containsKey(epicTask)) {
            System.out.println("zadacha yzhe sozdana");
        } else {
            task.put(epicTask, new ArrayList<>());
        }

    }

    public void setSubTask(EpicTask epicTask, SubTask subTask) {


        if (task.containsKey(epicTask)) {
            ArrayList<SubTask> subTasks = task.get(epicTask);
            subTasks.add(subTask);
            task.put(epicTask, subTasks);
        } else {
            System.out.println("Чтобы добавить подзадачу - добавьте сначала задачу");
        }

    }

    public void setUsualTask(UsualTask usualTask) {
        if (!usualTasks.contains(usualTask)) {
            usualTasks.add(usualTask);
        } else {
            System.out.println("zadacha postavlena");
        }

    }


    public void deleteSubTask(SubTask subTask) {

        for (ArrayList<SubTask> search : task.values()){
                for (int i = 0; i < search.size(); i++) {
                    if (search.get(i).getId() == subTask.getId()) {

                        search.remove(subTask);
                    }
                }
        }

    }

    public void deleteUsualTask(UsualTask usualTask){
        for (int i = 0; i < usualTasks.size(); i++) {
            if (usualTasks.get(i).getId() == usualTask.getId()){

                usualTasks.remove(usualTask);
            }

        }
    }

    public void deleteEpicTask(EpicTask epicTask) {
        HashMap<EpicTask, ArrayList<SubTask>> copyTask = new HashMap<>();
        for(EpicTask search : task.keySet()){
            copyTask.put(search,task.get(search));
            if (search.getId() == epicTask.getId()) {
                copyTask.remove(search);
            }
        }
        task.clear();
        task.putAll(copyTask);
    }

    public void updateEpicTask(int id, EpicTask epicTask) {
        for (EpicTask change : task.keySet()) {
            if (id == change.getId()) {
                change.changeName(epicTask);
            }
        }
    }

    public void updateSubTask(int id, SubTask subTask) {
        for (ArrayList<SubTask> search : task.values()) {
            for (SubTask change : search) {
                if (change.getId() == id) {
                    change.changeName(subTask);
                }
            }
        }
    }

    public void updateUsualTask(int id, UsualTask usualTask) {
        for (UsualTask task : usualTasks) {
            if (id == task.getId()) {
                task.changeName(usualTask);
            }
        }
    }

    public void deleteAllUsualTask() {

        usualTasks.clear();
    }

    public void deleteAllEpicTasks() {

        task.clear();
    }

    public void deleteAllSubTasks() {
        for (ArrayList<SubTask> subTasks : task.values()) {
            subTasks.clear();
        }
    }

    public void getAllEpicTask() {
        for (EpicTask search : task.keySet()) {
            System.out.println(search);

        }
    }

    public void getAllUsualTask() {
        for (UsualTask task : usualTasks) {
            System.out.println(task);
        }
    }

    public void getAllSubTasks() {
        for (EpicTask search : task.keySet()) {
            ArrayList<SubTask> subTasks = task.get(search);
            System.out.println("Epic "+ search.name);
            for (SubTask print : subTasks) {
                System.out.println(print);
            }

        }
    }

    public void getPerID(int id) {
        for(EpicTask epicTask : task.keySet()){
            if(epicTask.getId()==id){
                System.out.println(epicTask);
                return;
            } else {
                ArrayList<SubTask> subTasks = task.get(epicTask);
                for(SubTask search : subTasks){
                    if(search.getId()==id){
                        System.out.println(search);
                        return;
                    } else {
                        for (UsualTask task1 : usualTasks){
                            if (task1.getId()==id){
                                System.out.println(task1);
                                return;
                            }
                        }
                    }
                }
            }
        }

    }


}
