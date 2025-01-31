package ru.yandex.javacourse.palamarchuk.schedule.task;

import java.util.Objects;

public class Task {
    private int id; // Идентификатор задачи
    private String title; // Заголовок задачи
    private String description; // Описание задачи
    private Status status; // Статус задачи

    public TaskType getType() {
        return TaskType.TASK;
    }

    //Конструктор id
    public Task(int id, String title, String description, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Task(String title, String description, Status status) {
        this(0, title, description, status);
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
                ", status=" + status +
                '}';
    }
}
