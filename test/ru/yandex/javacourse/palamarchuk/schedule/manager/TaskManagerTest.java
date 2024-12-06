package ru.yandex.javacourse.palamarchuk.schedule.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.palamarchuk.schedule.task.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    void testTaskEqualityById() {
        Task task1 = new Task("Task 1", "Description 1", Status.NEW);
        Task task2 = new Task("Task 1", "Description 1", Status.NEW);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        assertNotEquals(task1.getId(), task2.getId(), "У каждой задачи должен быть уникальный ID");
    }

    @Test
    void testEpicCannotAddItselfAsSubtask() {
        Epic epic = new Epic("Epic 1", "Epic Description");
        int epicId = taskManager.addEpic(epic);

        assertThrows(IllegalArgumentException.class,
                () -> taskManager.addSubtask(new Subtask("Subtask", "Description", Status.NEW, epicId)),
                "Эпик не может быть добавлен как подзадача самому себе");
    }

    @Test
    void testAddTasksAndRetrieveById() {
        Task task = new Task("Task 1", "Description 1", Status.NEW);
        Epic epic = new Epic("Epic 1", "Epic Description");

        int taskId = taskManager.addTask(task);
        int epicId = taskManager.addEpic(epic);

        assertNotNull(taskManager.getTask(taskId), "Задача не найдена по id");
        assertNotNull(taskManager.getEpic(epicId), "Эпик не найден по id");
    }

    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        final int taskId = taskManager.addTask(task);

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks(); // Используем getAllTasks

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }


    @Test
    void testHistoryManagerPreservesTaskHistory() {
        Task task = new Task("Task 1", "Description 1", Status.NEW);
        int taskId = taskManager.addTask(task);

        taskManager.getTask(taskId);

        List<Task> history = taskManager.getHistory();

        assertEquals(1, history.size(), "История должна содержать одну задачу");
        assertEquals(task, history.get(0), "Задача в истории не совпадает с добавленной задачей");
    }
}
