import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.palamarchuk.schedule.task.*;
import java.time.Duration;
import java.time.LocalDateTime;

class TaskTest {

//    @Test
//    void shouldBeEqualWhenIdsAreSame() {
//        // Создаем две задачи с одинаковыми id
//        Task task1 = new Task(1, "Task 1", "Description", Status.NEW);
//        Task task2 = new Task(1, "Task 1", "Description", Status.NEW); // Тот же id и значения
//
//        // Проверяем, что они равны, даже если они имеют одинаковый id
//        assertEquals(task1, task2, "Задачи с одинаковыми id и одинаковыми параметрами должны считаться равными");
//    }

    @Test
    void testNotBeEqualWhenIdsAreDifferent() {
        Task task1 = new Task("Task 1", "Description", Status.NEW);
        Task task2 = new Task("Task 2", "Different Description", Status.IN_PROGRESS);

        task1.setId(1);
        task2.setId(2);

        assertNotEquals(task1, task2, "Задачи с разными id не должны быть равны");
    }

    @Test
    void testDetectTaskOverlap() {
        Task task1 = new Task(1, "Task 1", "Description", Status.NEW, Duration.ofHours(2), LocalDateTime.now());
        Task task2 = new Task(2, "Task 2", "Description", Status.NEW, Duration.ofHours(2), LocalDateTime.now().plusHours(1));
        assertTrue(task1.isOverlapping(task2), "Задачи должны пересекаться по времени");
    }

}
