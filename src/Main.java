public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", Status.NEW);
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1", Status.NEW);
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2", Status.NEW);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", Status.NEW, epic1);
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", Status.NEW, epic2);

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);

        System.out.println("Задачи:");
        taskManager.getAllTasks().forEach(System.out::println);
        System.out.println("-".repeat(12));
        System.out.println("Эпики:");
        taskManager.getAllEpics().forEach(System.out::println);
        System.out.println("-".repeat(12));
        System.out.println("Подзадачи:");
        taskManager.getAllSubtasks().forEach(System.out::println);

        task1.setStatus(Status.DONE);
        task2.setStatus(Status.IN_PROGRESS);
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        subtask3.setStatus(Status.IN_PROGRESS);

        taskManager.updateEpic(epic1);
        taskManager.updateEpic(epic2);

        System.out.println("-".repeat(12));
        System.out.println("После изменения статусов:");
        taskManager.getAllTasks().forEach(System.out::println);
        taskManager.getAllEpics().forEach(System.out::println);
        taskManager.getAllSubtasks().forEach(System.out::println);

        taskManager.removeTask(task1.getId());
        taskManager.removeEpic(epic2.getId());

        System.out.println("-".repeat(12));
        System.out.println("После удаления:");
        taskManager.getAllTasks().forEach(System.out::println);
        taskManager.getAllEpics().forEach(System.out::println);
    }
}
