package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.palamarchuk.schedule.task.*;

class EpicTest {
    private Epic epic;

    @BeforeEach
    void setUp() {
        epic = new Epic("Epic 1", "Epic Description");
    }

    @Test
    void shouldAddAndRemoveSubtaskIds() {
        epic.addSubtaskId(1);
        epic.addSubtaskId(2);

        assertEquals(2, epic.getSubtaskIds().size(), "Должно быть 2 подзадачи");

        epic.removeSubtaskId(1);
        assertEquals(1, epic.getSubtaskIds().size(), "Одна подзадача должна быть удалена");
    }

    @Test
    void shouldClearSubtaskIds() {
        epic.addSubtaskId(1);
        epic.clearSubtaskIds();

        assertTrue(epic.getSubtaskIds().isEmpty(), "Все подзадачи должны быть удалены");
    }
}

