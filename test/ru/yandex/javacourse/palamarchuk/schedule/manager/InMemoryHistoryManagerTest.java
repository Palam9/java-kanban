import ru.yandex.javacourse.palamarchuk.schedule.manager.InMemoryHistoryManager;
import ru.yandex.javacourse.palamarchuk.schedule.manager.HistoryManager;  // Возможно, потребуется добавить импорт интерфейса
import ru.yandex.javacourse.palamarchuk.schedule.task.Task;
import ru.yandex.javacourse.palamarchuk.schedule.task.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private HistoryManager historyManager;  // Используем интерфейс HistoryManager
    private Task task1;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();  // Инициализируем менеджер через интерфейс
        task1 = new Task("Task 1", "Description", Status.NEW);
        task1.setId(1);
    }

    @Test
    void testAddAndRemoveTaskFromHistory() {
        historyManager.add(task1);
        assertEquals(1, historyManager.getHistory().size(), "История должна содержать одну задачу");

        historyManager.remove(1);
        assertTrue(historyManager.getHistory().isEmpty(), "История должна быть пуста");
    }

    @Test
    void testHistoryAfterMultipleAdditions() {
        Task task2 = new Task("Task 2", "Another task", Status.NEW);
        task2.setId(2);

        historyManager.add(task1);
        historyManager.add(task2);

        assertEquals(2, historyManager.getHistory().size(), "История должна содержать две задачи");
    }

    @Test
    void testRemoveTaskFromHistory() {
        historyManager.add(task1);
        historyManager.remove(1);

        assertTrue(historyManager.getHistory().isEmpty(), "История должна быть пуста после удаления задачи");
    }
}
