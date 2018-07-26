package task.dst.com.tasks.app.tasks.model;

public enum TaskStatus {
    OPEN(1),
    CLOSED(0),
    EXPIRED(-1),
    PENDING(2);

    private int colorStatus;

    TaskStatus(int status) {

    }


    public int getColorStatus() {
        return colorStatus;
    }

}
