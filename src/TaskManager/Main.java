package TaskManager;


public class Main {

    public static void main(String[] args) {

        EpicTask epicTask1 = new EpicTask("go to shop", "Buy any foods");
        SubTask task1 = new SubTask("potato", "");
        SubTask task2 = new SubTask("tomato", "the red vegetable");


        EpicTask epicTask2 = new EpicTask("build airship", "null");
        SubTask task3 = new SubTask("put Engine", "null");




        TaskManager taskManager = new TaskManager();

        taskManager.setEpicTask(epicTask1);
        taskManager.setSubTask(epicTask1, task1);
        taskManager.setSubTask(epicTask1, task2);

        taskManager.setEpicTask(epicTask2);
        taskManager.setSubTask(epicTask2, task3);

        UsualTask usualTask1 = new UsualTask("wash car", "wash car BMW");
        taskManager.setUsualTask(usualTask1);

        UsualTask usualTask2 = new UsualTask("wash home", "wash first room");
        taskManager.setUsualTask(usualTask2);





        taskManager.updateUsualTask(usualTask1.getId(), new UsualTask("wash black car", "wash black BMW"));
        taskManager.updateEpicTask(epicTask1.getId(), new EpicTask("open holodilnik", "take food"));
        taskManager.updateSubTask(task1.getId(), new SubTask("cherry", "eat cherry"));




        taskManager.deleteEpicTask(epicTask1);
        taskManager.getPerID(1);

        //Привет, вроде все работает кроме этого метода именно с передачей epicTask1 (с epicTask2 все работает),
        // не могу понять в чем дело








    }

}