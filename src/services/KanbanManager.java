package services;

import models.Epic;
import models.SubTask;
import models.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class KanbanManager {

    private int generatedId = 0;

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();

    public Task saveTask(Task task) {

        int id = generatedId();

        task.setId(id);
        tasks.put(id, task);
        return task;
    }
    public Epic saveEpic(Epic epic) {

        int id = generatedId();

        epic.setId(id);
        epic.setStatus("NEW");
        epics.put(id, epic);
        return epic;
    }
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
    public Task getTaskById(int id) {

        return tasks.get(id);
    }
    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return subTasks.get(id);
    }
    public ArrayList<SubTask> getSubTaskByEpicId(int id) {

        if (epics.containsKey(id)) {
            ArrayList<SubTask> newSubtask = new ArrayList<>();
            Epic epic = epics.get(id);
            for (int i = 0; i < epic.getSubTaskIds().size(); i++) {
                newSubtask.add(subTasks.get(epic.getSubTaskIds().get(i)));
            }
            return newSubtask;
        } else {
            return  null;
        }
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }
    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public void updataTask(Task task) {

        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Задача " + task.getId() + "не найдена");
        }
    }

    public void updataEpic(Epic epic) {

        if (epic != null && epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            epicStatus(epic);
        } else {
            System.out.println("Эпик " + epic.getId() + " не найден.");
        }
    }

    public void updateSubTask(SubTask subTask) {

        if (subTask != null && subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getEpicId());
            epicStatus(epic);
        } else  {
            System.out.println("Подзадача эпика не найдена.");
        }
    }
    public void deleteTaskId(int id) {

        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else  {
            System.out.println("Задача по идентификатору " + id + " не найдена.");
        }
    }
    public void deleteEpicId(int id) {

        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            epics.remove(id);
            for (Integer subtaskId : epic.getSubTaskIds()) {
                subTasks.remove(subtaskId);
            }
            epic.addSubTaskIds(id);
        } else {
            System.out.println("Эпик по идентификатору " + id + " не найден.");
        }
    }
    public void deleteSubTaskId(int id) {

        SubTask subTask = subTasks.get(id);
        if (subTask != null) {
            Epic epic = epics.get(subTask.getEpicId());
            epic.getSubTaskIds().remove((Integer) subTask.getId());
            epicStatus(epic);
            subTasks.remove(id);
        } else {
            System.out.println("Подзадача " + id + " не найдена.");
        }
    }
    public void allDeleteTask() {

        tasks.clear();
    }
    public void allDeleteEpic() {

        subTasks.clear();
        epics.clear();
    }
    public void allDeleteSubTask() {

        for (Epic epic : epics.values()) {
            for (int id : epic.getSubTaskIds()) {
                SubTask subTask = subTasks.get(id);
                subTasks.remove(id);
            }
            epic.getSubTaskIds().clear();
        }
    }
    private int generatedId() {

        return ++generatedId;
    }
    private void epicStatus(Epic epic) {

        if (epics.containsKey(epic.getId())) {
            if (epic.getSubTaskIds().size() == 0) {
                epic.setStatus("NEW");
            } else {
                ArrayList<SubTask> newSubtask = new ArrayList<>();
                int news = 0;
                int dones = 0;

                for (int i = 0; i < epic.getSubTaskIds().size(); i++) {
                    newSubtask.add(subTasks.get(epic.getSubTaskIds().get(i)));
                }
                for (SubTask subTask : newSubtask) {
                    if (subTask.getStatus().equals("DONE")) {
                        dones++;
                    }
                    if (subTask.getStatus().equals("NEW")) {
                        news++;
                    }
                    if (subTask.getStatus().equals("IN_PROGRESS")) {
                        epic.setStatus("IN_PROGRESS");
                        return;
                    }
                }
                if (dones == epic.getSubTaskIds().size()) {
                    epic.setStatus("DONE");
                } else if (news == epic.getSubTaskIds().size()) {
                    epic.setStatus("IN_PROGRESS");
                }
            }
        } else {
            System.out.println("Эпик не найден");
        }
    }
}
