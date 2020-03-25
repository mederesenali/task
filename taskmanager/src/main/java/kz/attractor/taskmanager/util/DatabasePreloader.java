package kz.attractor.taskmanager.util;

import kz.attractor.taskmanager.model.Task;
import kz.attractor.taskmanager.model.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabasePreloader {

    @Bean
    CommandLineRunner generateGibberish(TaskRepository repository) {
        return args -> {
            repository.deleteAll();
            var tasks = Task.makeCurrentMonthTasks();
            repository.saveAll(tasks);
        };
    }
}
