import models.Epic;
import models.Status;
import models.SubTask;
import models.Task;
import services.FileBackedTaskManager;
import services.TaskManager;
import services.Managers;

import java.io.File;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {

        Path path = Path.of("data.csv");
        File file = new File(String.valueOf(path));
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(Managers.getDefaultHistory(), file);

        Task oneTask = new Task("Task1", "1111",Status.NEW);
        fileBackedTaskManager.saveTask(oneTask);
        Task twoTask = new Task("Task2", "2222",Status.DONE);
        fileBackedTaskManager.saveTask(twoTask);
        Epic oneEpic = new Epic("Epic1","epic111", Status.NEW);
        fileBackedTaskManager.saveEpic(oneEpic);
        SubTask oneSubTask = new SubTask("subTask111", "subtask1111",Status.NEW,oneEpic.getId());
        fileBackedTaskManager.saveSubTask(oneSubTask);

     //   System.out.println(fileBackedTaskManager.getAllTasks());
     //   System.out.println(fileBackedTaskManager.getAllEpics());
     //   System.out.println(fileBackedTaskManager.getAllSubTasks());

        fileBackedTaskManager.getEpicById(oneEpic.getId());
        fileBackedTaskManager.getTaskById(oneTask.getId());
      //  System.out.println(fileBackedTaskManager.getHistoryManager().getHistory());
        System.out.println("\n");

//        fileBackedTaskManager.allDeleteTask();
//        fileBackedTaskManager.allDeleteEpic();
//        fileBackedTaskManager.allDeleteSubTask();

        System.out.println("Читаем из файла:");

        fileBackedTaskManager.loadFromFile(file);
        System.out.println("Все задачи:");
        System.out.println(fileBackedTaskManager.getAllTasks());
        System.out.println("Все эпики:");
        System.out.println(fileBackedTaskManager.getAllEpics());
        System.out.println("Все подзадачи:");
        System.out.println(fileBackedTaskManager.getAllSubTasks());
        System.out.println("История просмотра:");
        System.out.println(fileBackedTaskManager.getHistoryManager().getHistory());


       // fileBackedTaskManager.allDeleteTask();

       // System.out.println(fileBackedTaskManager.getAllEpics());
       // System.out.println(fileBackedTaskManager.getAllSubTasks());






//        TaskManager taskManager = Managers.getInMemoryTaskManager(Managers.getDefaultHistory());
//
//        taskManager.saveTask(new Task("Задача 1","Описание задачи 1", Status.NEW));
//        taskManager.saveTask(new Task("Задача 2","Описание задачи 2", Status.NEW));
//        taskManager.saveEpic(new Epic("Эпик 1", "Описание эпик 1",Status.NEW));
//        taskManager.saveEpic(new Epic("Эпик 2", "Описание эпик 2", Status.NEW));
//        taskManager.saveSubTask(new SubTask("Подзадача 1 эпика 1", "123456", Status.NEW, 3));
//        taskManager.saveSubTask(new SubTask("Подзадача 2 эпика 1", "123456", Status.NEW, 3));
//        taskManager.saveSubTask(new SubTask("Подзадача 3 эпика 1", "223456", Status.NEW, 3));
//        taskManager.getTaskById(1);
//        taskManager.getTaskById(1);
//        taskManager.getTaskById(2);
//        taskManager.getTaskById(2);
//        taskManager.getEpicById(3);
//        taskManager.getEpicById(4);
//        taskManager.getSubTaskById(5);
//        taskManager.getSubTaskById(6);
//        taskManager.getSubTaskById(7);
//        taskManager.getTaskById(1);
//        taskManager.getEpicById(3);
//        taskManager.getEpicById(3);
//        taskManager.getEpicById(3);
//        taskManager.getEpicById(3);
//        taskManager.getEpicById(3);
//        taskManager.getTaskById(2);
//
//        System.out.println("История просмотра: ");
//        System.out.println(taskManager.getHistory());
//        System.out.println("Удаляем задачу 1: ");
//        taskManager.remove(1);
//        System.out.println(taskManager.getHistory());
//        System.out.println("Удаляем эпик и три его подзадачи: ");
//        taskManager.deleteEpicId(3);
//        System.out.println(taskManager.getHistory());
    }
}
