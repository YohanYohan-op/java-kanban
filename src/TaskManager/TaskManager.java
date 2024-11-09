package TaskManager;

import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {


    public HashMap<EpicTask, ArrayList<SubTask>> task = new HashMap<>();
    public ArrayList<Task> idSearch = new ArrayList<>();
    public ArrayList<UsualTask> usualTasks = new ArrayList<>();


    public void setEpicTask(EpicTask epicTask) {


        if (task.containsKey(epicTask)) {

            System.out.println("zadacha yzhe sozdana");
        } else {
            task.put(epicTask, new ArrayList<>());
            idSearch.add(epicTask);

        }

    }

    public void setSubTask(EpicTask epicTask, SubTask subTask) {


        if (task.containsKey(epicTask)) {
            ArrayList<SubTask> subTasks = task.get(epicTask);
            subTasks.add(subTask);
            task.put(epicTask, subTasks);
            idSearch.add(subTask);


        } else {
            System.out.println("Чтобы добавить подзадачу - добавьте сначала задачу");
        }

    }

    public void setUsualTask(UsualTask usualTask) {
        if (!usualTasks.contains(usualTask)) {
            idSearch.add(usualTask);
            usualTasks.add(usualTask);
        } else {
            System.out.println("zadacha postavlena");
        }

    }


    public void deleteSubTask(SubTask subTask) {

        for (ArrayList<SubTask> search : task.values()){
                for (int i = 0; i < search.size(); i++) {
                    if (search.get(i).getId() == subTask.getId()) {
                        for (int j = 0; j < idSearch.size(); j++) {
                            if(idSearch.get(j).getId() == subTask.getId()){
                                idSearch.remove(subTask);
                            }
                        }
                        search.remove(subTask);
                    }
                }
        }

    }

    public void deleteUsualTask(UsualTask usualTask){
        for (int i = 0; i < usualTasks.size(); i++) {
            if (usualTasks.get(i).getId() == usualTask.getId()){
                for (int j = 0; j < idSearch.size(); j++) {
                    if(idSearch.get(j).getId() == usualTask.getId()){
                        idSearch.remove(usualTask);
                    }
                }
                usualTasks.remove(usualTask);
            }

        }
    }

    public void deleteEpicTask(EpicTask epicTask) {

        for(EpicTask search : task.keySet()){
            if (search.getId() == epicTask.getId()) {

                for (int j = 0; j < idSearch.size(); j++) {
                    if(idSearch.get(j).getId() == epicTask.getId()){
                        idSearch.remove(epicTask);
                    }
                }

                task.remove(search);
            }
        }
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
        for (UsualTask task : usualTasks) {
            for (int j = 0; j < idSearch.size(); j++) {
                if(idSearch.get(j).getId() == task.getId()){
                    idSearch.remove(task);
                }
            }
        }
        usualTasks.clear();
    }

    public void deleteAllEpicTasks() {
        for (EpicTask epicTask : task.keySet()){
            for (int j = 0; j < idSearch.size(); j++) {
                if(idSearch.get(j).getId() == epicTask.getId()){
                    idSearch.remove(epicTask);
                }
            }
        }
        task.clear();
    }

    public void deleteAllSubTasks() {
        for (ArrayList<SubTask> subTasks : task.values()) {
            for (SubTask subTask : subTasks){
                for (int j = 0; j < idSearch.size(); j++) {
                    if(idSearch.get(j).getId() == subTask.getId()){
                        idSearch.remove(subTask);
                    }
                }
            }
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
        for (Task search : idSearch) {
            if (id == search.getId()) {
                System.out.println(search);
            }
        }
    }


}
