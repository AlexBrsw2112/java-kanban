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
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);

//        Task oneTask = new Task("Task1", "1111", Status.NEW);
//        fileBackedTaskManager.saveTask(oneTask);
//        Task twoTask = new Task("Task2", "2222", Status.DONE);
//        fileBackedTaskManager.saveTask(twoTask);
//        Epic oneEpic = new Epic("Epic1", "epic111", Status.NEW);
//        fileBackedTaskManager.saveEpic(oneEpic);
//        SubTask oneSubTask = new SubTask("subTask111", "subtask1111", Status.NEW, oneEpic.getId());
//        fileBackedTaskManager.saveSubTask(oneSubTask);
//
//        fileBackedTaskManager.getEpicById(oneEpic.getId());
//        fileBackedTaskManager.getTaskById(oneTask.getId());
//        System.out.println("\n");

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
    }
}
