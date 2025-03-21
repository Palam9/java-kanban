package ru.yandex.javacourse.palamarchuk.schedule.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private int id; // Идентификатор задачи
    private String title; // Заголовок задачи
    private String description; // Описание задачи
    protected Status status; // Статус задачи
    protected Duration duration;
    protected LocalDateTime startTime;

    public TaskType getType() {
        return TaskType.TASK;
    }

    //Конструктор id
    public Task(int id, String title, String description, Status status, Duration duration, LocalDateTime startTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String title, String description, Status status, Duration duration, LocalDateTime startTime) {
        this(0, title, description, status, duration, startTime);
    }

    public Task(String title, String description, Status status) {
        this(0, title, description, status, Duration.ZERO, null); // Значения по умолчанию
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return (startTime != null) ? startTime.plus(duration) : null; // Добавлена защита от NullPointerException
    }

    public String getDurationAsString() {
        return duration != null ? String.valueOf(duration.toMillis()) : null;
    }

    public void setDurationFromString(String durationString) {
        if (durationString != null) {
            this.duration = Duration.ofMillis(Long.parseLong(durationString));
        }
    }

    // Проверка пересечения задач
    public boolean isOverlapping(Task other) {
        return this.startTime != null && other.startTime != null &&
                this.getEndTime().isAfter(other.startTime) && other.getEndTime().isAfter(this.startTime);
    }

    // Метод equals для сравнения только по id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                status == task.status;
    }

    // Метод hashCode для генерации хэша только на основе id
    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status);
    }

    // Переопределение toString для удобного отображения задачи
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status + '\'' +
                "duration=" + duration.toMinutes() + '\'' +
                "startTime=" + startTime +
                '}';
    }
}


