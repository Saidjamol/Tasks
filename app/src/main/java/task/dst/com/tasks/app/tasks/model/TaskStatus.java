package task.dst.com.tasks.app.tasks.model;

public enum TaskStatus {
    OPEN(1),
    CLOSED(0),
    EXPIRED(-1);

    TaskStatus(int status) {

    }
}
