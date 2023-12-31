package services;

import models.Epic;
import models.SubTask;
import models.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    List<Task> getHistory();

    void remove(int id);

    void saveTask(Task task);

    void saveEpic(Epic epic);

    void saveSubTask(SubTask subTask);

    Task getTaskById(int id);

    Epic getEpicById(int id);

    SubTask getSubTaskById(int id);

    ArrayList<SubTask> getSubTaskByEpicId(int id);

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<SubTask> getAllSubTasks();

    void updataTask(Task task);

    void updataEpic(Epic epic);

    void updateSubTask(SubTask subTask);

    void deleteTaskId(int id);

    void deleteEpicId(int id);

    void deleteSubTaskId(int id);

    void allDeleteTask();

    void allDeleteEpic();

    void allDeleteSubTask();
}
