package ru.yandex.javacourse.palamarchuk.schedule.task;

import java.time.Duration;
import java.time.LocalDateTime;


public class Subtask extends Task {
    private int epicId;
    private Epic epic;

    public Subtask(String title, String description, Status status, Duration duration, LocalDateTime startTime, int epicId) {
        super(title, description, status, duration, startTime);
        this.epicId = epicId;
        this.epic = epic;  // Инициализация через конструктор
        if (epic != null) {
            this.epic.addSubtask(this);  // Добавляем подзадачу в эпик
        }
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public void setStatus(Status status) {
        this.status = status;
        if (epic != null) {
            epic.recalculateStatus();  // Пересчитываем статус эпика при изменении статуса подзадачи
        }
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", epicId=" + epicId +
                '}';
    }
}