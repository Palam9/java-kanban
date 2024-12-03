package ru.yandex.javacourse.palamarchuk.schedule;

import ru.yandex.javacourse.palamarchuk.schedule.manager.Managers;
import ru.yandex.javacourse.palamarchuk.schedule.manager.TaskManager;
import ru.yandex.javacourse.palamarchuk.schedule.task.Epic;
import ru.yandex.javacourse.palamarchuk.schedule.task.Status;
import ru.yandex.javacourse.palamarchuk.schedule.task.Subtask;
import ru.yandex.javacourse.palamarchuk.schedule.task.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        // Создаём задачи
        Task task1 = new Task("Task 1", "Description 1", Status.NEW);
        Task task2 = new Task("Task 2", "Description 2", Status.NEW);

        int taskId1 = taskManager.addTask(task1);
        int taskId2 = taskManager.addTask(task2);

        Epic epic = new Epic("Epic 1", "Epic Description");
        int epicId = taskManager.addEpic(epic);

        Subtask subtask = new Subtask("Subtask 1", "Subtask Description", Status.NEW, epicId);
        taskManager.addSubtask(subtask);

        // Делаем запросы и печатаем историю
        taskManager.getTask(taskId1);
        taskManager.getTask(taskId2);
        taskManager.getEpic(epicId);

        printAllTasks(taskManager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
