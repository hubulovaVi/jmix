package com.company.project_jmix.view.workload;


import com.company.project_jmix.view.main.MainView;
import com.company.project_jmix.view.workloadinfo.WorkloadInfoView;
import com.vaadin.flow.router.Route;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.component.ListDataComponent;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "workload-view", layout = MainView.class)
@ViewController("Jm_WorkloadView")
@ViewDescriptor("workload-view.xml")
public class WorkloadView extends StandardView {

    @ViewComponent
    private DataGrid<KeyValueEntity> workloadDataGrid;

    @Autowired
    private DialogWindows dialogWindows;

    @Subscribe("workloadDataGrid.workloadInfo")
    public void onWorkloadDataGridWorkloadInfo(final ActionPerformedEvent event) {
        KeyValueEntity workloadItem = workloadDataGrid.getSingleSelectedItem();
        if (workloadItem == null) {
            return;
        }

        dialogWindows.view(this, WorkloadInfoView.class)
                    .withViewConfigurer(workloadInfoView -> workloadInfoView.withItem(workloadItem))
                    .open();
    }
}