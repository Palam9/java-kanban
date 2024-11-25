package ru.yandex.javacourse.Palamarcuk.schedule;

import ru.yandex.javacourse.Palamarcuk.schedule.manager.TaskManager;
import ru.yandex.javacourse.Palamarcuk.schedule.task.*;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Task 1", "Description 1", Status.NEW);
        Task task2 = new Task("Task 2", "Description 2", Status.NEW);
        Epic epic1 = new Epic("Epic 1", "Epic Description 1", Status.NEW);
        Epic epic2 = new Epic("Epic 2", "Epic Description 2", Status.NEW);

        int epic1Id = taskManager.addEpic(epic1);
        int epic2Id = taskManager.addEpic(epic2);

        Subtask subtask1 = new Subtask("Subtask 1", "Subtask Description 1", Status.NEW, epic1Id);
        Subtask subtask2 = new Subtask("Subtask 2", "Subtask Description 2", Status.NEW, epic1Id);
        Subtask subtask3 = new Subtask("Subtask 3", "Subtask Description 3", Status.NEW, epic2Id);

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);

        System.out.println("All Tasks:");
        taskManager.getAllTasks().forEach(System.out::println);

        System.out.println("\nAll Epics:");
        taskManager.getAllEpics().forEach(System.out::println);

        System.out.println("\nAll Subtasks:");
        taskManager.getAllSubtasks().forEach(System.out::println);
    }
}