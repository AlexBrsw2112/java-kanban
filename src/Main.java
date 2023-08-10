import models.Epic;
import models.SubTask;
import models.Task;
import services.KanbanManager;
public class Main {
    public static void main(String[] args) {

        KanbanManager kanbanManager = new KanbanManager();

        //TASK
        System.out.println("Создаем 2 задачи и выводим на экран:");
        kanbanManager.saveTask(new Task("Задача 1","Описание задачи 1", "NEW"));
        kanbanManager.saveTask(new Task("Задача 2","Описание задачи 2","NEW"));
        System.out.println(kanbanManager.getAllTasks());
        System.out.println("Меняем статус задачи 1 на IN_PROGRESS: ");
        Task task = kanbanManager.getTaskById(1);
        task.setStatus("IN_PROGRESS");
        kanbanManager.updataTask(task);
        System.out.println(task);
        System.out.println("Выводим на экран 2 задачу:");
        System.out.println(kanbanManager.getTaskById(2));

        //EPIC
        System.out.println("Создаем 2 эпика и выводим на экран:");
        kanbanManager.saveEpic(new Epic("Эпик 1", "Описание эпик 1","NEW"));
        kanbanManager.saveEpic(new Epic("Эпик 2", "Описание эпик 2", "NEW"));
        System.out.println(kanbanManager.getAllEpics());
        System.out.println("Меняем статус эпика 2 на IN_PROGRESS: ");
        Epic epic = kanbanManager.getEpicById(4);
        epic.setStatus("IN_PROGRESS");
        kanbanManager.updataEpic(epic);
        System.out.println(epic);
        System.out.println("Выводим на экран 1 эпик:");
        System.out.println(kanbanManager.getEpicById(3));

        //SUBTASK
        System.out.println("Создаем 3 подзадачи и выводим их на экран:");
        kanbanManager.saveSubTask(new SubTask("Подзадача 1 эпика 1", "123456", "NEW", 3));
        kanbanManager.saveSubTask(new SubTask("Подзадача 2 эпика 1", "123456", "NEW", 3));
        kanbanManager.saveSubTask(new SubTask("Подзадача 1 эпика 2", "223456", "NEW", 4));
        System.out.println(kanbanManager.getAllSubTasks());
        System.out.println("Выводим на экран подзадачи 1 эпика: ");
        System.out.println(kanbanManager.getSubTaskByEpicId(3));
        System.out.println("Вывод на кран 2 подзадачу 1 эпика(id)");
        SubTask subTask = kanbanManager.getSubTaskById(6);
        System.out.println(subTask);
        System.out.println("Меняем статус подзадачи на IN_PROGRESS: ");
        subTask.setStatus("IN_PROGRESS");
        kanbanManager.updateSubTask(subTask);
        System.out.println(subTask);

//      System.out.println("Проверяем изменился ли статус у эпика");
//      System.out.println(kanbanManager.getAllEpics());

//      DELETE SUBTASK
        System.out.println("Удаляем подзадачу по id: ");
        kanbanManager.deleteSubTaskId(6);
        System.out.println(kanbanManager.getAllSubTasks());
        System.out.println("Удаляем все подзадачи: ");
        kanbanManager.allDeleteSubTask();
        System.out.println(kanbanManager.getAllSubTasks());

//      DELETE EPIC
        System.out.println("Удаляем эпик по id: ");
        kanbanManager.deleteEpicId(3);
        System.out.println(kanbanManager.getAllEpics());
        System.out.println("Удаляем все эпики: ");
        kanbanManager.allDeleteEpic();
        System.out.println(kanbanManager.getAllEpics());

//      DELETE TASK
        System.out.println("Удаляем задачу по id: ");
        kanbanManager.deleteTaskId(1);
        System.out.println(kanbanManager.getAllTasks());
        System.out.println("Удаляем все задачи: ");
        kanbanManager.allDeleteTask();
        System.out.println(kanbanManager.getAllTasks());
    }
}
