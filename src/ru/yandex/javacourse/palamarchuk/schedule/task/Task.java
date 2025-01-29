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

    // Конструктор
    public Task(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
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
        if (this == o) return true; // Объекты равны, если это один и тот же экземпляр
        if (o == null || getClass() != o.getClass()) return false; // Классы должны совпадать
        Task task = (Task) o;
        return id == task.id; // Сравнение только по id
    }

    // Метод hashCode для генерации хэша только на основе id
    @Override
    public int hashCode() {
        return Objects.hash(id);
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
