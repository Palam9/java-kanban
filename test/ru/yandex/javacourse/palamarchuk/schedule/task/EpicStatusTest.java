package ru.yandex.javacourse.palamarchuk.schedule.task;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EpicStatusTest {

    private Epic epic;
    private Subtask subtask1;
    private Subtask subtask2;

    @BeforeEach
    void setUp() {
        epic = new Epic("Epic 1", "Epic Description");
        subtask1 = new Subtask("Subtask 1", "Description 1", Status.NEW,
                Duration.ofMinutes(30), LocalDateTime.now(), 1);
        subtask2 = new Subtask("Subtask 2", "Description 2", Status.NEW,
                Duration.ofMinutes(45), LocalDateTime.now().plusMinutes(30), 1);
    }

    @Test
    void testEpicStatusAllNew() {
        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);
        assertEquals(Status.NEW, epic.getStatus(), "Статус эпика должен быть NEW, если все подзадачи NEW");
    }

    @Test
    void testEpicStatusAllDone() {
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);
        assertEquals(Status.DONE, epic.getStatus(), "Статус эпика должен быть DONE, если все подзадачи DONE");
    }

    @Test
    void testEpicStatusNewAndDone() {
        subtask1.setStatus(Status.NEW);
        subtask2.setStatus(Status.DONE);
        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус эпика должен быть IN_PROGRESS, если есть подзадачи NEW и DONE");
    }

    @Test
    void testEpicStatusInProgress() {
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.IN_PROGRESS);
        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус эпика должен быть IN_PROGRESS, если хотя бы одна подзадача IN_PROGRESS");
    }
}
