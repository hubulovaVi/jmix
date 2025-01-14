package com.company.project_jmix.app;

import com.company.project_jmix.entity.Project;
import com.company.project_jmix.entity.ProjectStats;
import com.company.project_jmix.entity.Task;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component("Jm_ProjectStatsService")
public class ProjectStatsService {
    private final DataManager dataManager;
    public ProjectStatsService(DataManager dataManager) {
        this.dataManager = dataManager;
        }

    public List<ProjectStats> fetchProjectStatistics() {
        List<Project> projects = dataManager.load(Project.class).all().list();
        List<ProjectStats> projectStats = projects.stream()
                .map(project -> {
                    ProjectStats stats = dataManager.create(ProjectStats.class);
                    stats.setProjectName(project.getName());
                    List<Task> tasks = project.getTasks();
                    stats.setTasksCount(tasks.size());
                    Integer estimatedEfforts = tasks.stream()
                            .map(task -> task.getEstimatedEfforts() == null ? 0 : task.getEstimatedEfforts())
                            .reduce(0, Integer::sum);
                    stats.setPlannedEfforts(estimatedEfforts);
                    stats.setActualEfforts(stats.getActualEfforts(project.getId()));
                    return stats;
                }).toList();
        return projectStats;
    }

    private Integer getActualEfforts(UUID id) {
        return dataManager.loadValue("select sum(te.timeSpent) from tm_TimeEntry te" +
                      "where te.task.project.id = :projectId", Integer.class)
                .parameter("projectId", id)
                .one();

    }
}

