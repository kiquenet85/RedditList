package com.app.ndiazgranados.catalog.ui.view;

import com.app.ndiazgranados.catalog.model.web.Entry;

/**
 * @author n.diazgranados
 */
public interface DetailAppView extends MvpView {
    void showDetailApp(Entry selectedApp);

    void showDetailAppEmpty();
}

