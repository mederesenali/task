package kz.attractor.taskmanager.model;

import kz.attractor.taskmanager.util.Generator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Document
public class Task {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final Random r = new Random();

    @Id
    private String id = generateId();
    private String name;
    private String description;
    private TaskType taskType;
    private LocalDate date;

    private static String generateId() {
        return LocalDateTime.now().format(dtf) + r.nextInt();
    }

    private static String generateId(LocalDate date) {
        return date.atTime(LocalTime.now()).format(dtf) + "-" + r.nextInt(1_000_000);
    }

    private static List<Task> makeRandom(LocalDate date, int min, int max) {
        int limit = r.nextInt(max - min) + min;
        return Stream.generate(() -> Task.make(date))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public static Task make(LocalDate date) {
        Task t = new Task();
        t.setDate(date);
        t.setId(generateId(date));
        t.setName(Generator.makeName());
        t.setDescription(Generator.makeDescription());
        t.setTaskType(TaskType.values()[r.nextInt(4)]);
        return t;
    }

    public static List<Task> makeCurrentMonthTasks() {
        LocalDate monthStart = LocalDate.now().withDayOfMonth(1);
        return IntStream
                .rangeClosed(1, monthStart.lengthOfMonth())
                .mapToObj(monthStart::withDayOfMonth)
                .map(date -> Task.makeRandom(date, 2,7))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public static Task makeFromParams(Map<String, String> params) {
        var type = TaskType.valueOf(params.getOrDefault("taskType", "ordinary").toUpperCase());
        var date = LocalDate.parse(params.getOrDefault("date", ""));
        Task t = new Task();
        t.setDate(date);
        t.setId(params.getOrDefault("taskId", generateId()));
        t.setName(params.getOrDefault("name", ""));
        t.setDescription(params.getOrDefault("description", ""));
        t.setTaskType(type);
        return t;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
