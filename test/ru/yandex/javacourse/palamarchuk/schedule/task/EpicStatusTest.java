package ru.yandex.javacourse.palamarchuk.schedule.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.palamarchuk.schedule.manager.InMemoryTaskManager;
import ru.yandex.javacourse.palamarchuk.schedule.manager.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EpicStatusTest {

    private TaskManager taskManager;
    private Epic epic;
    private Subtask subtask1;
    private Subtask subtask2;

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager();
        epic = new Epic("Epic 1", "Epic Description");
        taskManager.addEpic(epic);  // Добавляем эпик в менеджер

        subtask1 = new Subtask("Subtask 1", "Description 1", Status.NEW,
                Duration.ofMinutes(30), LocalDateTime.now(), epic.getId());
        subtask2 = new Subtask("Subtask 2", "Description 2", Status.NEW,
                Duration.ofMinutes(45), LocalDateTime.now().plusMinutes(30), epic.getId());
    }

    @Test
    void testEpicStatusAllNew() {
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        assertEquals(Status.NEW, epic.getStatus(), "Статус эпика должен быть NEW, если все подзадачи NEW");
    }

    @Test
    void testEpicStatusAllDone() {
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.updateSubtask(subtask1);  // Обновляем подзадачи, чтобы пересчитать статус эпика
        taskManager.updateSubtask(subtask2);
        assertEquals(Status.DONE, epic.getStatus(), "Статус эпика должен быть DONE, если все подзадачи DONE");
    }

    @Test
    void testEpicStatusNewAndDone() {
        subtask1.setStatus(Status.NEW);
        subtask2.setStatus(Status.DONE);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.updateSubtask(subtask1);  // Обновляем подзадачи, чтобы пересчитать статус эпика
        taskManager.updateSubtask(subtask2);
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус эпика должен быть IN_PROGRESS, если есть подзадачи NEW и DONE");
    }

    @Test
    void testEpicStatusInProgress() {
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.IN_PROGRESS);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.updateSubtask(subtask1);  // Обновляем подзадачи, чтобы пересчитать статус эпика
        taskManager.updateSubtask(subtask2);
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус эпика должен быть IN_PROGRESS, если хотя бы одна подзадача IN_PROGRESS");
    }
}