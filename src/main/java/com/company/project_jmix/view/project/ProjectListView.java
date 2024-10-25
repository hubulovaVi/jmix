package com.company.project_jmix.view.project;

import com.company.project_jmix.entity.Project;
import com.company.project_jmix.view.main.MainView;
import com.company.project_jmix.view.projecttasksbrowser.ProjectTasksBrowser;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "projects", layout = MainView.class)
@ViewController("Jm_Project.list")
@ViewDescriptor("project-list-view.xml")
@LookupComponent("projectsDataGrid")
@DialogMode(width = "64em")
public class ProjectListView extends StandardListView<Project> {

    @Autowired
    private DialogWindows dialogWindows;
    @ViewComponent
    private DataGrid<Project> projectsDataGrid;

    @Subscribe("projectsDataGrid.showTasksDialog")
    public void onProjectsDataGridShowTasksDialog(final ActionPerformedEvent event) {
        DialogWindow<ProjectTasksBrowser> window = dialogWindows.view(this, ProjectTasksBrowser.class)
                .build();

        window.getView().setProject(projectsDataGrid.getSingleSelectedItem());
        window.setResizable(true);
        window.open();
    }
}