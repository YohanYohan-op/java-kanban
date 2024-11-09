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

        idSearch.add(usualTask);
        usualTasks.add(usualTask);

    }


    public void deleteSubTask(EpicTask epicTask, SubTask subTask) {

        for (EpicTask search : task.keySet()){
            if (search.getId() == epicTask.getId()) {
                ArrayList<SubTask> subTasks = task.get(search);
                for (SubTask task1 : subTasks) {
                    if (task1.getId()==subTask.getId()) {
                        subTasks.remove(subTask);
                        Task.x--;
                        task.put(epicTask, subTasks);
                    }
                }
            }
        }

    }

    public void deleteUsualTask(UsualTask usualTask){
        for (UsualTask task : usualTasks) {
            if (task.getId() == usualTask.getId()){
                for (Task deleteID : idSearch) {
                    if (deleteID.getId()== task.getId()) {
                        idSearch.remove(deleteID);
                    }
                }
                usualTasks.remove(task);
                Task.x--;
            }

        }
    }

    public void deleteEpicTask(EpicTask epicTask) {

        for(EpicTask search : task.keySet()){
            if (search.getId()==epicTask.getId()) {
                for (Task deleteID : idSearch) {
                    if (deleteID.getId() == epicTask.getId()) {
                        idSearch.remove(deleteID);
                    }
                }
                ArrayList<SubTask> subTasks = task.get(epicTask);
                for (SubTask delete : subTasks) {
                    subTasks.remove(delete);
                    Task.x--;
                }
                task.remove(epicTask);
                Task.x--;
            }
        }



    }

    public void updateEpicTask(int id, EpicTask epicTask) {
        for (EpicTask change : task.keySet()) {
            if (id == change.getId()) {
                change.changeName(epicTask);
            }
        }
        Task.x--;
    }

    public void updateSubTask(int id, SubTask subTask) {
        for (EpicTask epicTask : task.keySet()) {
            ArrayList<SubTask> subTasks = task.get(epicTask);
            for (SubTask change : subTasks) {
                if (id == change.getId()) {
                    change.changeName(subTask);

                }
            }
            task.put(epicTask, subTasks);

        }
        Task.x--;
    }

    public void updateUsualTask(int id, UsualTask usualTask) {
        for (UsualTask task : usualTasks) {
            if (id == task.getId()) {
                task.changeName(usualTask);
                //найти и заменить
            }
        }
        Task.x--;
    }

    public void deleteAllUsualTask() {
        for (UsualTask task : usualTasks) {
            for (Task deleteID : idSearch) {
                if (deleteID.getId()== task.getId()) {
                    idSearch.remove(deleteID);
                }
            }
            usualTasks.remove(task);
            Task.x--;
        }
    }

    public void deleteAllEpicTasks() {
        for (EpicTask epicTask : task.keySet()) {
            for (Task deleteID : idSearch) {
                if (deleteID.getId()==epicTask.getId()) {
                    idSearch.remove(deleteID);
                }
            }
            ArrayList<SubTask> subTasks = task.get(epicTask);
            for (SubTask delete : subTasks) {
                subTasks.remove(delete);
                Task.x--;
            }
            task.remove(epicTask);
            Task.x--;

        }
    }

    public void deleteAllSubTasks() {
        for (EpicTask epicTask : task.keySet()) {
            ArrayList<SubTask> subTasks = task.get(epicTask);
            for (SubTask delete : subTasks) {
                for (Task deleteID : idSearch) {
                    if (deleteID.getId()==delete.getId()) {
                        idSearch.remove(deleteID);
                    }
                }

                subTasks.remove(delete);
                Task.x--;
            }
            task.put(epicTask, subTasks);

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
