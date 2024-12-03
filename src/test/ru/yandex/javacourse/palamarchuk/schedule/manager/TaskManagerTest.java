package ru.yandex.javacourse.palamarchuk.schedule.manager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.palamarchuk.schedule.manager.InMemoryTaskManager;
import ru.yandex.javacourse.palamarchuk.schedule.manager.TaskManager;
import ru.yandex.javacourse.palamarchuk.schedule.manager.Managers;
import ru.yandex.javacourse.palamarchuk.schedule.task.*;

import java.util.List;

class TaskManagerTest {
    private TaskManager taskManager;

    @BeforeEachimport static org.junit.jupiter.api.Assertions.*;
import BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.palamarchuk.schedule.manager.InMemoryTaskManager;
import ru.yandex.javacourse.palamarchuk.schedule.manager.TaskManager;
import ru.yandex.javacourse.palamarchuk.schedule.manager.Managers;
import ru.yandex.javacourse.palamarchuk.schedule.task.*;

import java.util.List;

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
            int id1 = taskManager.addTask(task1);
            int id2 = taskManager.addTask(task2);

            assertEquals(id1, id2, "Задачи с одинаковыми id должны быть равны");
        }

        @Test
        void testEpicCannotAddItselfAsSubtask() {
            Epic epic = new Epic("Epic 1", "Epic Description", Status.NEW);
            int epicId = taskManager.addEpic(epic);

            assertThrows(IllegalArgumentException.class, () -> taskManager.addSubtask(new Subtask("Subtask", "Description", Status.NEW, epicId)),
                    "Эпик не может быть добавлен как подзадача самому себе");
        }

        @Test
        void testAddTasksAndRetrieveById() {
            Task task = new Task("Task 1", "Description 1", Status.NEW);
            Epic epic = new Epic("Epic 1", "Epic Description", Status.NEW);
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

            final List<Task> tasks = taskManager.getTasks();

            assertNotNull(tasks, "Задачи не возвращаются.");
            assertEquals(1, tasks.size(), "Неверное количество задач.");
            assertEquals(task, tasks.get(0), "Задачи не совпадают.");
        }
    }

    void setUp() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    void testTaskEqualityById() {
        Task task1 = new Task("Task 1", "Description 1", Status.NEW);
        Task task2 = new Task("Task 1", "Description 1", Status.NEW);
        int id1 = taskManager.addTask(task1);
        int id2 = taskManager.addTask(task2);

        Task retrievedTask1 = taskManager.getTask(id1);
        Task retrievedTask2 = taskManager.getTask(id2);

        assertEquals(retrievedTask1, retrievedTask2, "Задачи с одинаковыми id должны быть равны");
    }

    @Test
    void testTaskInheritanceEqualityById() {
        Task task = new Task("Task 1", "Description 1", Status.NEW);
        Epic epic = new Epic("Epic 1", "Epic Description", Status.NEW);
        int taskId = taskManager.addTask(task);
        int epicId = taskManager.addEpic(epic);

        Task retrievedTask = taskManager.getTask(taskId);
        Task retrievedEpic = taskManager.getEpic(epicId);

        assertEquals(retrievedTask, retrievedEpic, "Задачи с одинаковыми id должны быть равны, независимо от типа");
    }

    @Test
    void testEpicCannotAddItselfAsSubtask() {
        Epic epic = new Epic("Epic 1", "Epic Description", Status.NEW);
        int epicId = taskManager.addEpic(epic);

        // Попытка добавить эпик в качестве подзадачи самому себе
        assertThrows(IllegalArgumentException.class, () -> taskManager.addSubtask(new Subtask("Subtask", "Description", Status.NEW, epicId)),
                "Эпик не может быть добавлен как подзадача самому себе");
    }

    @Test
    void testSubtaskCannotBeItsOwnEpic() {
        Subtask subtask = new Subtask("Subtask 1", "Subtask Description", Status.NEW, 0);
        int subtaskId = taskManager.addSubtask(subtask);

        // Попытка сделать подзадачу её же эпиком
        assertThrows(IllegalArgumentException.class, () -> taskManager.addEpic(new Epic("Epic 1", "Epic Description", Status.NEW)),
                "Подзадача не может быть своим эпиком");
    }

    @Test
    void testManagersReturnInitializedManagers() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "Менеджер задач должен быть проинициализирован");
    }

    @Test
    void testAddTasksAndRetrieveById() {
        Task task = new Task("Task 1", "Description 1", Status.NEW);
        Epic epic = new Epic("Epic 1", "Epic Description", Status.NEW);
        int taskId = taskManager.addTask(task);
        int epicId = taskManager.addEpic(epic);

        assertNotNull(taskManager.getTask(taskId), "Задача не найдена по id");
        assertNotNull(taskManager.getEpic(epicId), "Эпик не найден по id");
    }

    @Test
    void testGeneratedAndGivenIdsDoNotConflict() {
        Task task1 = new Task("Task 1", "Description 1", Status.NEW);
        Task task2 = new Task("Task 2", "Description 2", Status.NEW);

        int taskId1 = taskManager.addTask(task1); // Сгенерированный id
        int taskId2 = taskManager.addTask(task2); // Сгенерированный id

        task1.setId(1); // Ручное задание id
        taskManager.addTask(task1);

        assertNotEquals(taskId1, taskId2, "Задачи с разными id не должны конфликтовать");
    }

    @Test
    void testTaskImmutabilityOnAdd() {
        Task task = new Task("Task 1", "Description 1", Status.NEW);
        int taskId = taskManager.addTask(task);

        Task retrievedTask = taskManager.getTask(taskId);

        assertEquals(task.getTitle(), retrievedTask.getTitle(), "Название задачи не совпадает");
        assertEquals(task.getDescription(), retrievedTask.getDescription(), "Описание задачи не совпадает");
        assertEquals(task.getStatus(), retrievedTask.getStatus(), "Статус задачи не совпадает");
    }

    @Test
    void testHistoryManagerPreservesTaskHistory() {
        Task task = new Task("Task 1", "Description 1", Status.NEW);
        int taskId = taskManager.addTask(task);

        // Добавляем задачу в историю
        taskManager.getTask(taskId);

        List<Task> history = taskManager.getHistory();

        assertEquals(1, history.size(), "История должна содержать одну задачу");
        assertEquals(task, history.get(0), "Задача в истории не совпадает с добавленной задачей");
    }

    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        final int taskId = taskManager.addTask(task);

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void add() {
        Task task = new Task("Test add", "Test add description", Status.NEW);
        taskManager.addTask(task);
        final List<Task> history = taskManager.getHistory();

        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }
}
