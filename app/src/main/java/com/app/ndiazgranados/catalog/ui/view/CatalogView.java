package com.app.ndiazgranados.catalog.ui.view;

import com.app.ndiazgranados.catalog.model.web.Category;

import java.util.List;

/**
 * @author n.diazgranados
 */
public interface CatalogView extends MvpView {
    void showCatalog(List<Category> categoryList);

    void showCatalogEmpty();

    void showLoader();

    void hideLoader();
}
