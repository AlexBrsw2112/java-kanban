import models.Epic;
import models.Status;
import models.SubTask;
import models.Task;
import services.TaskManager;
import services.Managers;

public class Main {
    public static void main(String[] args) {

        TaskManager taskManager = Managers.getInMemoryTaskManager(Managers.getDefaultHistory());

        taskManager.saveTask(new Task("Задача 1","Описание задачи 1", Status.NEW));
        taskManager.saveTask(new Task("Задача 2","Описание задачи 2", Status.NEW));
        taskManager.saveEpic(new Epic("Эпик 1", "Описание эпик 1",Status.NEW));
        taskManager.saveEpic(new Epic("Эпик 2", "Описание эпик 2", Status.NEW));
        taskManager.saveSubTask(new SubTask("Подзадача 1 эпика 1", "123456", Status.NEW, 3));
        taskManager.saveSubTask(new SubTask("Подзадача 2 эпика 1", "123456", Status.NEW, 3));
        taskManager.saveSubTask(new SubTask("Подзадача 1 эпика 2", "223456", Status.NEW, 4));
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.getEpicById(3);
        taskManager.getEpicById(4);
        taskManager.getSubTaskById(5);
        taskManager.getSubTaskById(6);
        taskManager.getSubTaskById(7);
        taskManager.getTaskById(1);
        taskManager.getEpicById(3);
        taskManager.getEpicById(4);
        taskManager.getEpicById(4);

        System.out.println("История просмотра: ");
        System.out.println(taskManager.getHistory());
    }
}
