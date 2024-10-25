package com.company.project_jmix.app;

import com.company.project_jmix.entity.Project;
import com.company.project_jmix.entity.Task;
import io.jmix.core.DataManager;
import io.jmix.core.EntitySet;
import io.jmix.core.SaveContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("Jm_TaskImportService")
public class TaskImportService {
    private static final Logger log = LoggerFactory.getLogger(TaskImportService.class);
    private final DataManager dataManager;

    public TaskImportService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public int importTasks() {

        List<String> names = getTaskNames();
        Project defaultProject = loadDefaltProject();
        EntitySet entitySet = new EntitySet();

        if (defaultProject != null) {
            List<Task> tasks = names.stream()
                    .map(name -> {
                        Task task = dataManager.create(Task.class);
                        task.setName(name);
                        task.setProject(defaultProject);
                        return task;
                    })
                    .toList();
            entitySet = dataManager.save(new SaveContext().saving(tasks));
        }

        log.info("tasks imported " + entitySet.size());
        return entitySet.size();
    }

    private List<String> getTaskNames() {
        return Stream.iterate(0, i -> i).limit(5)
                .map(i -> "Task " + RandomStringUtils.randomAlphabetic(5))
                .collect(Collectors.toList());
    }

    @Nullable
    public Project loadDefaltProject() {
        Optional<Project> project = dataManager.load(Project.class)
                .query("select p from Jm_Project p where p.defaultProject = :isDefault")
                .parameter("isDefault", true)
                .optional();

        return project.orElse(null);
    }
}