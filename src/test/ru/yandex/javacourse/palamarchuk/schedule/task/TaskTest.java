import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.palamarchuk.schedule.task.*;

class TaskTest {

    @Test
    void shouldBeEqualWhenIdsAreSame() {
        Task task1 = new Task("Task 1", "Description", Status.NEW);
        Task task2 = new Task("Task 2", "Different Description", Status.IN_PROGRESS);

        task1.setId(1);
        task2.setId(1);

        assertEquals(task1, task2, "Задачи с одинаковыми id должны быть равны");
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
