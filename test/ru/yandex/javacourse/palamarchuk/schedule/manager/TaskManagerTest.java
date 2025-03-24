package ru.yandex.javacourse.palamarchuk.schedule.manager;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.palamarchuk.schedule.task.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;
import java.time.LocalDateTime;

class TaskManagerTest {

    private TaskManager taskManager;
    private Task task1;
    private Task task2;
    private Epic epic;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
        task1 = new Task("Task 1", "Description 1", Status.NEW);
        task1.setId(1);
        task2 = new Task("Task 2", "Description 2", Status.NEW);
        task2.setId(2);
        epic = new Epic("Epic 1", "Epic Description");
    }

    @Test
    void testAddTask() {
        int taskId = taskManager.addTask(task1);
        assertNotNull(taskManager.getTask(taskId), "Задача не была добавлена.");
    }


    @Test
    void testRemoveTask() {
        int taskId = taskManager.addTask(task1);
        taskManager.removeTask(taskId);

        assertNull(taskManager.getTask(taskId), "Задача не была удалена.");
    }

    @Test
    void testTaskUpdate() {
        int taskId = taskManager.addTask(task1);
        Task updatedTask = new Task("Updated Task", "Updated Description", Status.IN_PROGRESS);
        updatedTask.setId(taskId);

        taskManager.updateTask(updatedTask);
        Task taskFromManager = taskManager.getTask(taskId);

        assertEquals(updatedTask, taskFromManager, "Задача не была обновлена корректно.");
    }


    @Test
    void testRemoveTaskFromHistoryOnDeletion() {
        int taskId = taskManager.addTask(task1);

        // Проверяем, что задача добавлена в историю
        taskManager.getHistory().forEach(task -> {
            assertTrue(task.getId() == taskId, "История должна содержать удаленную задачу.");
        });

        taskManager.removeTask(taskId);

        // Проверяем, что история пустая после удаления задачи
        assertTrue(taskManager.getHistory().isEmpty(), "История должна быть пустой после удаления задачи");
    }

    @Test
    void testSubtaskConsistency() {
        int epicId = taskManager.addEpic(epic);
        Subtask subtask = new Subtask(
                "Subtask 1",
                "Subtask Description",
                Status.NEW,
                Duration.ofMinutes(30),  // Указываем длительность
                LocalDateTime.now(),     // Указываем время начала
                epicId
        );
        int subtaskId = taskManager.addSubtask(subtask);

        assertTrue(epic.getSubtaskIds().contains(subtaskId), "ID подзадачи не был добавлен в эпик.");

        taskManager.removeSubtask(subtaskId);

        assertFalse(epic.getSubtaskIds().contains(subtaskId), "ID подзадачи не был удален из эпика.");
    }

    @Test
    void testTaskDataIntegrity() {
        Task task1 = new Task("Задача 1", "Описание задачи", Status.NEW);
        int taskId = taskManager.addTask(task1);

        // Изменим данные задачи через сеттеры
        task1.setTitle("Updated Title");
        taskManager.updateTask(task1);

        // Проверим, что изменения в задаче отразились в менеджере
        Task updatedTask = taskManager.getTask(taskId);
        assertEquals("Updated Title", updatedTask.getTitle(), "Заголовок задачи не был обновлен.");
    }


    @Test
    void testSubtaskDataIntegrity() {
        int epicId = taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Subtask 1", "Description 1", Status.NEW,
                Duration.ofMinutes(30), LocalDateTime.now(), 1);
        int subtaskId = taskManager.addSubtask(subtask);

        // Изменим подзадачу через сеттеры
        subtask.setTitle("Updated Subtask");
        taskManager.updateSubtask(subtask);

        // Проверим, что изменения отразились корректно
        Subtask updatedSubtask = taskManager.getSubtask(subtaskId);
        assertEquals("Updated Subtask", updatedSubtask.getTitle(), "Название подзадачи не было обновлено.");
    }

    @Test
    void testRemoveSubtaskDataIntegrity() {
        int epicId = taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Subtask 1", "Description 1", Status.NEW,
                Duration.ofMinutes(30), LocalDateTime.now(), 1);
        int subtaskId = taskManager.addSubtask(subtask);

        taskManager.removeSubtask(subtaskId);

        // Проверим, что подзадача не существует в менеджере после удаления
        assertNull(taskManager.getSubtask(subtaskId), "Подзадача не была удалена.");
    }

    @Test
    void testAddTaskSuccessfully() {
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Task 1", "Description", Status.NEW);
        int id = taskManager.addTask(task);
        assertNotNull(taskManager.getTask(id), "Задача должна быть добавлена в менеджер");
    }

}


