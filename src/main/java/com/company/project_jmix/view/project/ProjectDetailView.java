package com.company.project_jmix.view.project;

import com.company.project_jmix.app.ProjectsService;
import com.company.project_jmix.datatype.ProjectLabels;
import com.company.project_jmix.entity.Project;
import com.company.project_jmix.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.core.validation.group.UiComponentChecks;
import io.jmix.core.validation.group.UiCrossFieldChecks;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@Route(value = "projects/:id", layout = MainView.class)
@ViewController("Jm_Project.detail")
@ViewDescriptor("project-detail-view.xml")
@EditedEntityContainer("projectDc")
public class ProjectDetailView extends StandardDetailView<Project> {

    @ViewComponent
    private TypedTextField<ProjectLabels> projectLabelsField;
    @Autowired
    private ProjectsService projectsService;
    @Autowired
    private Notifications notifications;
    @Autowired
    private Validator validator;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Project> event) {
        projectLabelsField.setReadOnly(false);

        event.getEntity().setProjectLabels(new ProjectLabels(List.of("bug", "enhancement", "task")));
    }

    @Subscribe(id = "commitWithBeanValidation", subject = "clickListener")
    public void onCommitWithBeanValidationClick(final ClickEvent<JmixButton> event) {
        try {
            projectsService.save(getEditedEntity());
            close(StandardOutcome.CLOSE);
        } catch (ConstraintViolationException e) {
            StringBuilder sb = new StringBuilder();

            for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
                sb.append(constraintViolation.getMessage()).append("\n");
            }
                notifications.create(sb.toString())
                        .withPosition(Notification.Position.TOP_END)
                        .withType(Notifications.Type.WARNING)
                        .show();
        }
    }

    @Subscribe(id = "performBeanValidationBtn", subject = "clickListener")
    public void onPerformBeanValidationBtnClick(final ClickEvent<JmixButton> event) {
        Set<ConstraintViolation<Project>> violations = validator.validate(getEditedEntity(),
                Default.class, UiComponentChecks.class, UiCrossFieldChecks.class);

        showBeanValidationExceptions(violations);
    }

    private void showBeanValidationExceptions(Set<ConstraintViolation<Project>> constraintViolations) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                sb.append(constraintViolation.getMessage()).append("\n");
            }
            notifications.create(sb.toString())
                    .withPosition(Notification.Position.TOP_START)
                    .show();
        }
        
    }

