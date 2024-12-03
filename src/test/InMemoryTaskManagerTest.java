package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.palamarchuk.schedule.task.*;

class InMemoryTaskManagerTest {
    private InMemoryTaskManager manager;

    @BeforeEach
    void setUp() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void shouldAddAndRetrieveTask() {
        Task task = new Task("Task 1", "Description 1", Status.NEW);
        int taskId = manager.addTask(task);
        Task retrievedTask = manager.getTask(taskId);

        assertNotNull(retrievedTask, "Задача должна быть добавлена");
        assertEquals(task, retrievedTask, "Добавленная и полученная задача должны совпадать");
    }

    @Test
    void shouldRemoveTask() {
        Task task = new Task("Task 1", "Description 1", Status.NEW);
        int taskId = manager.addTask(task);

        manager.removeTask(taskId);
        assertNull(manager.getTask(taskId), "Задача должна быть удалена");
    }

    @Test
    void shouldReturnAllTasks() {
        Task task1 = new Task("Task 1", "Description 1", Status.NEW);
        Task task2 = new Task("Task 2", "Description 2", Status.IN_PROGRESS);

        manager.addTask(task1);
        manager.addTask(task2);

        assertEquals(2, manager.getTasks().size(), "Должно быть возвращено 2 задачи");
    }
}

