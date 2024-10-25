package com.company.project_jmix.view.task;

import com.company.project_jmix.entity.Project;
import com.company.project_jmix.entity.Task;
import com.company.project_jmix.view.main.MainView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.dataview.ComboBoxListDataView;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.combobox.JmixComboBox;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;

import java.util.ArrayList;

@Route(value = "tasks/:id", layout = MainView.class)
@ViewController("Jm_Task.detail")
@ViewDescriptor("task-detail-view.xml")
@EditedEntityContainer("taskDc")
public class TaskDetailView extends StandardDetailView<Task> {
    @ViewComponent
    private JmixComboBox<String> labelField;
    private ComboBoxListDataView<String> labelsDataView;

    @Subscribe(id = "taskDc", target = Target.DATA_CONTAINER)
    public void onTaskDcItemPropertyChange(final InstanceContainer.ItemPropertyChangeEvent<Task> event) {
        if ("project".equals(event.getProperty())) {
            Project otherProject = (Project) event.getValue();

            if (otherProject != null) {
                event.getItem().setPriority(otherProject.getDefaultTaskPriority());
            }
        }
    }

    @Subscribe("projectField")
    public void onProjectFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityPicker<Project>, Project> event) {
        if (event.isFromClient()) {
            Project project = (Project) event.getValue();

            if (project != null) {
                getEditedEntity().setPriority(project.getDefaultTaskPriority());
            }
        }
    }

    @Subscribe
    public void onInit(final InitEvent event) {
        labelsDataView = labelField.setItems(new ListDataProvider<>(new ArrayList<>()));
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event){
        Project project = getEditedEntity().getProject();
        if (project != null && project.getProjectLabels() != null){
            labelsDataView.addItems(project.getProjectLabels().getLabels());
        }
    }
}