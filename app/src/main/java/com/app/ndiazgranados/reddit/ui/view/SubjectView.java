package com.app.ndiazgranados.reddit.ui.view;

import com.app.ndiazgranados.reddit.model.web.Child;

import java.util.List;

/**
 * @author n.diazgranados
 */
public interface SubjectView extends MvpView {
    void showRedditList(List<Child> categoryList);

    void showRedditListEmpty();

    void showLoader();

    void hideLoader();
}
