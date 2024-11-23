package ru.yandex.javacource.korolyov.taskManager.manager;

import ru.yandex.javacource.korolyov.taskManager.tasks.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private ArrayList<Task> history = new ArrayList<>();

    @Override
    public ArrayList<Task> getHistory() {
        if (history.size()>10){
            ArrayList<Task> returnHistory = new ArrayList<>();
            for (int i = history.size(); i > history.size()-10 ; i--) {
                returnHistory.add(history.get(i-1));
            }
            return returnHistory;
        }
        return history;
    }

    @Override
    public void addHistory(Task task) {
        history.add(task);
    }

}
