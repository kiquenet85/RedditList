package com.app.ndiazgranados.catalog.ui.view;

import com.app.ndiazgranados.catalog.model.web.Entry;

import java.util.List;

/**
 * @author n.diazgranados
 */
public interface TopAppsView extends MvpView {
    void showTopApps(List<Entry> topAppList);

    void showTopAppsEmpty();
}
