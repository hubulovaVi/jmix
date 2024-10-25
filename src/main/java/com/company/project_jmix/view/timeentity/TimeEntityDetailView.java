package com.company.project_jmix.view.timeentity;

import com.company.project_jmix.entity.TimeEntity;
import com.company.project_jmix.entity.User;
import com.company.project_jmix.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Route(value = "timeEntities/:id", layout = MainView.class)
@ViewController("Jm_TimeEntity.detail")
@ViewDescriptor("time-entity-detail-view.xml")
@EditedEntityContainer("timeEntityDc")
public class TimeEntityDetailView extends StandardDetailView<TimeEntity> {

    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Subscribe
    public void onInitEntity(final StandardDetailView.InitEntityEvent<TimeEntity> event) {
        final User user = (User) currentAuthentication.getUser();
        TimeEntity timeEntity = event.getEntity();

        timeEntity.setUser(user);

        timeEntity.setEntryDate(LocalDateTime.now().withHour(12).withMinute(0));
    }
}