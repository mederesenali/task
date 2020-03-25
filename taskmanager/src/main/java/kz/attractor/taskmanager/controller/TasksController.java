package kz.attractor.taskmanager.controller;

import kz.attractor.taskmanager.model.Task;
import kz.attractor.taskmanager.model.TaskRepository;
import kz.attractor.taskmanager.model.TaskType;
import org.apache.tomcat.jni.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class TasksController {

    private final TaskRepository repository;

    public TasksController(TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String rootHandler(Model model) {
        var map = StreamSupport.stream(repository.findAll().spliterator(), true)
                .collect(Collectors.groupingBy(Task::getDate, TreeMap::new, Collectors.toList()));
        model.addAttribute("days", map);
        model.addAttribute("today", LocalDate.now().getDayOfMonth());
        model.addAttribute("month", LocalDate.now().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, ruLocale));
        return "index";
    }

    //todo move to service
    static final Locale ruLocale = Locale.forLanguageTag("ru-RU");
    static final DateTimeFormatter longFormat = DateTimeFormatter.ofPattern("d MMMM uuuu", ruLocale);
    //todo issue: add support for days without tasks
    //

    @GetMapping("/tasks")
    public String tasksHandler(@RequestParam("date") String dateAsString, Model model) {
        var date = LocalDate.parse(dateAsString);
        var map = StreamSupport.stream(repository.findAll().spliterator(), true)
                .collect(Collectors.groupingBy(Task::getDate, TreeMap::new, Collectors.toList()));
        var tasks = map.getOrDefault(date, List.of());
        model.addAttribute("date", dateAsString);
        model.addAttribute("tasks", tasks);
        model.addAttribute("displayDateLongFormat", date.format(longFormat));
        return "tasks";
    }

    @GetMapping("/task/delete/{date}/{taskId}")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String deleteTaskHandler(@PathVariable("date") String dateAsString, @PathVariable String taskId) {
        repository.deleteById(taskId);
        return "redirect:/tasks?date=" + dateAsString;
    }

    @PostMapping("/task/add")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String addTaskPostHandler(@RequestParam Map<String, String> params) {
        // todo handle bad date or other params
        // todo map to task model (possible just need to understand how
        // todo @ModelSomething attribute
        var dateAsString = params.getOrDefault("date", "");
        repository.save(Task.makeFromParams(params));
        return "redirect:/tasks?date=" + dateAsString;
    }

    @GetMapping("/task/add")
    public String addTaskHandler(@RequestParam("date") String dateAsString, Model model) {
        model.addAttribute("formHeader", "Добавим задачу");
        model.addAttribute("submitText", "Создать!");
        model.addAttribute("formAction", "/task/add");
        model.addAttribute("date", dateAsString);
        model.addAttribute("task", new Task());
        model.addAttribute("taskTypes", TaskType.values());
        return "task-add-edit";
    }
}
