package com.company.project_jmix.view.projectstats;


import com.company.project_jmix.app.ProjectStatsService;
import com.company.project_jmix.entity.ProjectStats;
import com.company.project_jmix.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.LoadContext;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "project-stats-view", layout = MainView.class)
@ViewController("Jm_ProjectStatsView")
@ViewDescriptor("project-stats-view.xml")
public class ProjectStatsView extends StandardView {
    @Autowired
    private ProjectStatsService projectStatsService;

    @Install(to = "projectStatsDl", target = Target.DATA_LOADER)
    private List<ProjectStats> projectStatsDlLoadDelegate(final LoadContext<ProjectStats> loadContext) {
        return projectStatsService.fetchProjectStatistics();
    }
}