package ru.yandex.javacourse.palamarchuk.schedule.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Epic extends Task {
    private List<Integer> subtaskIds = new ArrayList<>();
    private final List<Subtask> subtasks;
    private LocalDateTime endTime;

    public Epic(String title, String description) {
        super(title, description, Status.NEW, Duration.ZERO, null); // Устанавливаем статус NEW по умолчанию
        this.subtasks = new ArrayList<>();
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        recalculateTime();
    }

    public void recalculateTime() {
        // Использование Stream API для расчёта времени эпика
        duration = subtasks.stream().map(Subtask::getDuration).reduce(Duration.ZERO, Duration::plus);
        startTime = subtasks.stream().map(Subtask::getStartTime).filter(Objects::nonNull).min(LocalDateTime::compareTo).orElse(null);
        endTime = subtasks.stream().map(Subtask::getEndTime).filter(Objects::nonNull).max(LocalDateTime::compareTo).orElse(null);

        for (Subtask subtask : subtasks) {
            duration = duration.plus(subtask.duration);
            if (startTime == null || subtask.startTime.isBefore(startTime)) {
                startTime = subtask.startTime;
            }
            if (endTime == null || subtask.getEndTime().isAfter(endTime)) {
                endTime = subtask.getEndTime();
            }
        }
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
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
