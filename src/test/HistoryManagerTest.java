package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.palamarchuk.schedule.task.*;
import ru.yandex.javacourse.palamarchuk.schedule.manager.*;

import java.util.List;

class HistoryManagerTest {
    private HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    void shouldAddTaskToHistory() {
        Task task = new Task("Task 1", "Description", Status.NEW);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "История должна содержать одну задачу");
    }

    @Test
    void shouldLimitHistoryTo10Tasks() {
        for (int i = 1; i <= 15; i++) {
            historyManager.add(new Task("Task " + i, "Description", Status.NEW));
        }

        List<Task> history = historyManager.getHistory();
        assertEquals(10, history.size(), "История должна содержать максимум 10 задач");
    }
}

