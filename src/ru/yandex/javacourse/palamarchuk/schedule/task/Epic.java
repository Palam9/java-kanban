package ru.yandex.javacourse.palamarchuk.schedule.task;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subtaskIds = new ArrayList<>();

    public Epic(String title, String description) {
        super(title, description, Status.NEW); // Устанавливаем статус NEW по умолчанию
    }

    public List<Integer> getSubtaskIds() {
        return new ArrayList<>(subtaskIds);
    }

    public void setSubtaskIds(List<Integer> subtaskIds) {
        this.subtaskIds = new ArrayList<>(subtaskIds);
    }

    public void addSubtaskId(int subtaskId) {
        subtaskIds.add(subtaskId);
    }

    public void removeSubtaskId(int subtaskId) {
        subtaskIds.remove((Integer) subtaskId); // Удаление по значению
    }

    public void clearSubtaskIds() {
        subtaskIds.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtaskIds=" + subtaskIds +
                '}';
    }
}
