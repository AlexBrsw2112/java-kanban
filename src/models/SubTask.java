package models;

import java.util.Objects;

public class SubTask extends Task {

    private int epicId;

    public SubTask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
        this.type = TaskType.SUBTASK;
    }

    public SubTask(int epicId) {
        this.epicId = epicId;
        this.type = TaskType.SUBTASK;
    }

    public SubTask() {
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return epicId == subTask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    public String toString() {
        return "\n SubTask{" +
                "epicId=" + getEpicId() +
                ", id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
