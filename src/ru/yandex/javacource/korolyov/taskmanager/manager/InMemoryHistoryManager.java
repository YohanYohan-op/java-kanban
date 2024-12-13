package ru.yandex.javacource.korolyov.taskmanager.manager;

import ru.yandex.javacource.korolyov.taskmanager.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final DoublyLinkedList<Task> historyNode = new DoublyLinkedList<>();
    private final Map<Integer, Node<Task>> history = new HashMap<>();

    @Override
    public List<Task> getHistory() {
        return historyNode.getHistor();
    }

    @Override
    public void addHistory(Task task) {
        if (task == null) {
            return;
        }
        for (Node<Task> search : historyNode.list()){
            if (search.data == task){
                historyNode.removeNode(search);
                historyNode.add(search);
                history.put(task.getId(), search);
            }
        }
    }

    @Override
    public void remove(int id) {
        historyNode.removeNode(history.get(id));
        history.remove(id);
    }
}
