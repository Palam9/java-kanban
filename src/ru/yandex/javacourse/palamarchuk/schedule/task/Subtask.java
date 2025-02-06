package ru.yandex.javacourse.palamarchuk.schedule.task;

import java.time.Duration;
import java.time.LocalDateTime;


public class Subtask extends Task {
    private int epicId;

    public Subtask(String title, String description, Status status, Duration duration, LocalDateTime startTime, int epicId) {
        super(title, description, status, duration, startTime);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
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