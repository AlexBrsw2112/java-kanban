package services;

import models.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private static final int MAX_SIZE_LIST_HISTORY = 10;

    private final List<Task> historyTask = new LinkedList<>();

    @Override
    public void add(Task task) {
        if (task != null) {
            if (historyTask.size() >= MAX_SIZE_LIST_HISTORY) {
                historyTask.remove(0);
                historyTask.add(task);
            } else {
                historyTask.add(task);
            }
        } else {
            System.out.println("Задача не найдена.");
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyTask;
    }
}
