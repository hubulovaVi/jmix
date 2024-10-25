package com.company.project_jmix.view.timeentity;

import com.company.project_jmix.entity.TimeEntity;
import com.company.project_jmix.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "timeEntities", layout = MainView.class)
@ViewController("Jm_TimeEntity.list")
@ViewDescriptor("time-entity-list-view.xml")
@LookupComponent("timeEntitiesDataGrid")
@DialogMode(width = "64em")
public class TimeEntityListView extends StandardListView<TimeEntity> {
}