package kz.attractor.taskmanager.model;

public enum TaskType {
    ORDINARY, URGENT, SHOPPING, WORK;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
