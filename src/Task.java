public class Task {

    private static int idCounter = 1;
    private int id;
    private String title;
    private String description;
    private Status status;

    public Task(String title, String description, Status status) {
        this.id = idCounter++;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    //Добавил на всякий
    public String getTitle() {
        return title;
    }

    //Аналогично))
    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}