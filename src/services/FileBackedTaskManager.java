package services;

import models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private final File file;

    public FileBackedTaskManager(File file) {
        super(Managers.getDefaultHistory());
        this.file = file;
    }

    public void save() {
        try {
            Files.deleteIfExists(file.toPath());
            Files.createFile(file.toPath());
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка! Удаление и создание файла.");
        }

        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            writer.write("id,type,name,status,description,epic\n");

            for (Task task : getAllTasks()) {
                writer.write(toString(task) + "\n");
            }

            for (Epic epic : getAllEpics()) {
                writer.write(toString(epic) + "\n");
            }

            for (SubTask subTask : getAllSubTasks()) {
                writer.write(toString(subTask) + "\n");
            }
            writer.write("\n");
            writer.write(historyToString(getHistoryManager()));
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка! Записи в файл.", e);
        }
    }

    private TaskType getType(Task task) {

        if (task.getType().equals(TaskType.EPIC)) {
            return TaskType.EPIC;
        } else if (task.getType().equals(TaskType.SUBTASK)) {
            return TaskType.SUBTASK;
        }
        return TaskType.TASK;
    }

    private String getSubTaskByEpicIds(Task task) {
        if (task.getType().equals(TaskType.SUBTASK)) {
            return Integer.toString(((SubTask) task).getEpicId());
        }
        return "";
    }

    private String toString(Task task) {

        return new StringBuilder().append(Integer.toString(task.getId())).append(",").
                append(getType(task).toString()).append(",").
                append(task.getName()).append(",").
                append(task.getStatus().toString()).append(",").
                append(task.getDescription()).append(",").
                append(getSubTaskByEpicIds(task)).toString();
    }

    private static String historyToString(HistoryManager historyManager) {

        List<Task> historys = historyManager.getHistory();
        StringBuilder line = new StringBuilder();

        if (historys.isEmpty()) {
            return "";
        }

        for (Task task : historys) {
            line.append(task.getId()).append(",");
        }

        if (line.length() != 0) {
            line.deleteCharAt(line.length() - 1);
        }

        return line.toString();
    }

    @Override
    public void saveTask(Task task) {
        super.saveTask(task);
        save();
    }

    @Override
    public void saveEpic(Epic epic) {
        super.saveEpic(epic);
        save();
    }

    @Override
    public void saveSubTask(SubTask subTask) {
        super.saveSubTask(subTask);
        save();
    }

    @Override
    public void deleteTaskId(int id) {
        super.deleteTaskId(id);
        save();
    }

    @Override
    public void deleteEpicId(int id) {
        super.deleteEpicId(id);
        save();
    }

    @Override
    public void deleteSubTaskId(int id) {
        super.deleteSubTaskId(id);
        save();
    }

    @Override
    public void allDeleteTask() {
        super.allDeleteTask();
        save();
    }

    @Override
    public void allDeleteEpic() {
        super.allDeleteEpic();
        save();
    }

    @Override
    public void allDeleteSubTask() {
        super.allDeleteSubTask();
        save();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subTask = super.getSubTaskById(id);
        save();
        return subTask;
    }

    @Override
    public void updataTask(Task task) {
        super.updataTask(task);
        save();
    }

    @Override
    public void updataEpic(Epic epic) {
        super.updataEpic(epic);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    public void loadFromFile(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String line = bufferedReader.readLine();
            while (bufferedReader.ready()) {
                line = bufferedReader.readLine();
                if (line.equals("")) {
                    break;
                }
                Task task = fromString(line);
                if (task.getType().equals(TaskType.EPIC)) {
                    loadEpic((Epic) task);
                } else if (task.getType().equals(TaskType.SUBTASK)) {
                    loadSubTask((SubTask) task);
                } else {
                    loadTask(task);
                }
            }

            String loadLineHistory = bufferedReader.readLine();
            for (int id : historyFromString(loadLineHistory)) {
                loadHistory(id);
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка! Чтение из файла.");
        }
    }

    private Task fromString(String str) {

        String[] argument = str.split(",");
        if (argument[1].equals("EPIC")) {
            Epic epic = new Epic(argument[2], argument[4], Status.valueOf(argument[3]));
            epic.setId(Integer.parseInt(argument[0]));
            epic.setStatus(Status.valueOf(argument[3]));
            return epic;
        } else if (argument[1].equals("SUBTASK")) {
            SubTask subTask = new SubTask(argument[2], argument[4], Status.valueOf(argument[3]),
                    Integer.parseInt(argument[5]));
            subTask.setId(Integer.parseInt(argument[0]));
            return subTask;
        } else {
            Task task = new Task(argument[2], argument[4], Status.valueOf(argument[3]));
            task.setId(Integer.parseInt(argument[0]));
            return task;
        }
    }

    public void loadTask(Task task) {

        super.saveTask(task);
    }

    public void loadEpic(Epic epic) {

        super.saveEpic(epic);
    }

    public void loadSubTask(SubTask subTask) {

        super.saveSubTask(subTask);
    }

    private static List<Integer> historyFromString(String str) {
        List<Integer> back = new ArrayList<>();
        if (str != null) {
            String[] id = str.split(",");
            for (String s : id) {
                back.add(Integer.parseInt(s));
            }
            return back;
        }
        return back;
    }
}
