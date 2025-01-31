import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.palamarchuk.schedule.task.*;

class TaskTest {

    @Test
    void shouldBeEqualWhenIdsAreSame() {
        Task task1 = new Task(1, "Task 1", "Description", Status.NEW);
        Task task2 = new Task(1, "Task 2", "Different Description", Status.IN_PROGRESS);

        assertEquals(task1.getId(), task2.getId(), "Задачи с одинаковыми id должны считаться равными по id");
    }

    @Test
    void shouldNotBeEqualWhenIdsAreDifferent() {
        Task task1 = new Task("Task 1", "Description", Status.NEW);
        Task task2 = new Task("Task 2", "Different Description", Status.IN_PROGRESS);

        task1.setId(1);
        task2.setId(2);

        assertNotEquals(task1, task2, "Задачи с разными id не должны быть равны");
    }
}
