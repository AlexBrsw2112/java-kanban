package services;

import models.Epic;
import models.SubTask;
import models.Task;
import models.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private int generatedId = 0;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();

    private final HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    @Override
    public Task saveTask(Task task) {

        int id = generatedId();

        task.setId(id);
        tasks.put(id, task);
        return task;
    }

    @Override
    public Epic saveEpic(Epic epic) {

        int id = generatedId();

        epic.setId(id);
        epic.setStatus(Status.NEW);
        epics.put(id, epic);
        return epic;
    }

    @Override
    public SubTask saveSubTask(SubTask subTask) {

        if (subTask == null) return null;
        int id = generatedId();
        subTask.setId(id);
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            subTasks.put(id, subTask);
            epic.addSubTaskIds(id);
            epicStatus(epic);
        }
        return subTask;
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subTask = subTasks.get(id);
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public ArrayList<SubTask> getSubTaskByEpicId(int id) {

        if (epics.containsKey(id)) {
            ArrayList<SubTask> newSubtask = new ArrayList<>();
            Epic epic = epics.get(id);
            for(int subtaskId : epic.getSubTaskIds()) {
                newSubtask.add(subTasks.get(subtaskId));
            }
            return newSubtask;
        } else {
            return  null;
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void updataTask(Task task) {

        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Задача " + task.getId() + "не найдена");
        }
    }

    @Override
    public void updataEpic(Epic epic) {

        if (epic != null && epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            epicStatus(epic);
        } else {
            System.out.println("Эпик " + epic.getId() + " не найден.");
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {

        if (subTask != null && subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getEpicId());
            epicStatus(epic);
        } else  {
            System.out.println("Подзадача эпика не найдена.");
        }
    }

    @Override
    public void deleteTaskId(int id) {

        if (tasks.containsKey(id)) {
            tasks.remove(id);
            historyManager.remove(id);
        } else  {
            System.out.println("Задача по идентификатору " + id + " не найдена.");
        }
    }

    @Override
    public void deleteEpicId(int id) {
        Epic epic = epics.remove(id);
        if (epic !=null) {
            for (Integer subtaskId : epic.getSubTaskIds()) {
                subTasks.remove(subtaskId);
                historyManager.remove(subtaskId);
            }
            epics.remove(id);
            historyManager.remove(id);
        } else {
            System.out.println("Эпик по идентификатору " + id + " не найден.");
        }
    }

    @Override
    public void deleteSubTaskId(int id) {

        SubTask subTask = subTasks.get(id);
        if (subTask != null) {
            Epic epic = epics.get(subTask.getEpicId());
            epic.getSubTaskIds().remove((Integer) subTask.getId());
            epicStatus(epic);
            subTasks.remove(id);
            historyManager.remove(id);
        } else {
            System.out.println("Подзадача " + id + " не найдена.");
        }
    }

    @Override
    public void allDeleteTask() {

        tasks.clear();
    }

    @Override
    public void allDeleteEpic() {

        subTasks.clear();
        epics.clear();
    }

    @Override
    public void allDeleteSubTask() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubTaskIds().clear();
            epicStatus(epic);
        }
    }

    private int generatedId() {
        return ++generatedId;
    }

    private void epicStatus(Epic epic) {

        if (epics.containsKey(epic.getId())) {
            if (!epic.getSubTaskIds().isEmpty()) {
                epic.setStatus(Status.NEW);
            } else {
                int news = 0;
                int dones = 0;

                  for (int subTaskId : epic.getSubTaskIds()) {
                    if (subTasks.get(subTaskId).getStatus() == Status.DONE) {
                        dones++;
                    } else if (subTasks.get(subTaskId).getStatus() == Status.NEW) {
                        news++;
                    } else {
                        epic.setStatus(Status.IN_PROGRESS);
                        return;
                    }
                }  
                if (dones == epic.getSubTaskIds().size()) {
                    epic.setStatus(Status.DONE);
                } else if (news == epic.getSubTaskIds().size()) {
                    epic.setStatus(Status.NEW);
                } else {
                    epic.setStatus(Status.IN_PROGRESS);
                }
            }
        } else {
            System.out.println("Эпик не найден");
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void remove(int id) {
        historyManager.remove(id);
    }
}

