package ru.yandex.javacourse.palamarchuk.schedule.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.palamarchuk.schedule.task.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TaskManagerTest {

    private TaskManager taskManager;
    private Task task1;
    private Task task2;
    private Epic epic;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
        task1 = new Task("Task 1", "Description 1", Status.NEW);
        task2 = new Task("Task 2", "Description 2", Status.NEW);
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
    void testEpicSubtaskConsistency() {
        int epicId = taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Subtask 1", "Subtask Description", Status.NEW, epicId);
        int subtaskId = taskManager.addSubtask(subtask);

        // Проверим, что подзадача была добавлена в эпик
        assertTrue(epic.getSubtaskIds().contains(subtaskId), "ID подзадачи не был добавлен в эпик.");

        taskManager.removeSubtask(subtaskId);

        // Проверим, что подзадача была удалена из эпика
        assertFalse(epic.getSubtaskIds().contains(subtaskId), "ID подзадачи не был удален из эпика.");
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

/*    @Test
    void testRemoveTaskFromHistoryOnDeletion() {
        int taskId = taskManager.addTask(task1);
        taskManager.getTask(taskId);  // Добавляем задачу в историю

        taskManager.removeTask(taskId);  // Удаляем задачу

        List<Task> history = taskManager.getHistory();
        assertTrue(history.isEmpty(), "История должна быть пуста после удаления задачи.");
    }*/


}


