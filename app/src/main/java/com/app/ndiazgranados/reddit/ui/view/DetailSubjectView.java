package com.app.ndiazgranados.reddit.ui.view;

import com.app.ndiazgranados.reddit.model.web.Child;

/**
 * @author n.diazgranados
 */
public interface DetailSubjectView extends MvpView {
    void showDetailSubject(Child selectedSubject);

    void showDetailSubjectEmpty();
}

