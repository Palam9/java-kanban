import ru.yandex.javacourse.palamarchuk.schedule.manager.InMemoryHistoryManager;
import ru.yandex.javacourse.palamarchuk.schedule.task.Task;
import ru.yandex.javacourse.palamarchuk.schedule.task.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private InMemoryHistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void testAddAndRemoveTask() {
        Task task1 = new Task("Task 1", "Description 1", Status.NEW);
        task1.setId(1);
        historyManager.add(task1);

        assertEquals(1, historyManager.getHistory().size(), "История должна содержать одну задачу");

        historyManager.remove(1);
        assertTrue(historyManager.getHistory().isEmpty(), "История должна быть пуста");
    }

    @Test
    void testAddMultipleTasks() {
        Task task1 = new Task("Task 1", "Description 1", Status.NEW);
        task1.setId(1);
        Task task2 = new Task("Task 2", "Description 2", Status.IN_PROGRESS);
        task2.setId(2);

        historyManager.add(task1);
        historyManager.add(task2);

        assertEquals(2, historyManager.getHistory().size(), "История должна содержать две задачи");
    }

    @Test
    void testRemoveTask() {
        Task task1 = new Task("Task 1", "Description 1", Status.NEW);
        task1.setId(1);
        historyManager.add(task1);
        historyManager.remove(1);

        assertTrue(historyManager.getHistory().isEmpty(), "История должна быть пуста");
    }

    @Test
    void testAddTaskAfterRemoval() {
        Task task1 = new Task("Task 1", "Description 1", Status.NEW);
        task1.setId(1);
        historyManager.add(task1);
        historyManager.remove(1);
        historyManager.add(task1);

        assertEquals(1, historyManager.getHistory().size(), "История должна содержать одну задачу");
    }
}
