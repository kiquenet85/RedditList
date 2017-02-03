package com.app.ndiazgranados.catalog.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ndiazgranados.catalog.R;
import com.app.ndiazgranados.catalog.data.local.cache.CategoryLocalCache;
import com.app.ndiazgranados.catalog.model.web.Category;
import com.app.ndiazgranados.catalog.network.NetworkManager;
import com.app.ndiazgranados.catalog.ui.adapter.CategoryAdapter;
import com.app.ndiazgranados.catalog.ui.presenter.CategoryPresenter;
import com.app.ndiazgranados.catalog.ui.view.CatalogView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author n.diazgranados
 */

public class CategoryFragment extends BaseFragment implements CatalogView, CategoryAdapter.OnCategoryClickListener {

    private RecyclerView categoryList;
    private ProgressBar loader;
    private TextView customMessage;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private CategoryLocalCache categoryLocalCache;
    private CategoryPresenter categoryPresenter;
    private NetworkManager networkManager;

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryLocalCache = appComponent.getCategoryLocalCache();
        categoryPresenter = appComponent.getCategoryPresenter();
        networkManager = appComponent.getNetworkManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootFrag = inflater.inflate(R.layout.fragment_cathegory, container, false);

        categoryList = (RecyclerView) rootFrag.findViewById(R.id.fragment_cathegory_cathegory_list);
        customMessage = (TextView) rootFrag.findViewById(R.id.fragment_cathegory_message);
        loader = (ProgressBar) rootFrag.findViewById(R.id.fragment_cathegory_loader);

        categoryList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        categoryList.setLayoutManager(layoutManager);
        adapter = new CategoryAdapter(new ArrayList<Category>(), this);
        categoryList.setAdapter(adapter);

        return rootFrag;
    }

    @Override
    public void onResume() {
        super.onResume();
        categoryPresenter.attachView(this);
        categoryPresenter.registerOnBus(eventBus);

        if (!networkManager.isOnline()) {
            showCatalogEmpty();
        } else if (!getActivity().isFinishing()) {
            showLoader();
            categoryPresenter.loadCategories(200);
        }
    }

    @Override
    public void onPause() {
        categoryPresenter.unregisterOnBus(eventBus);
        categoryPresenter.detachView();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //As not detail is shown we can save null value.
        categoryLocalCache.saveToCache(null, outState);
    }

    @Override
    public void showLoader() {
        customMessage.setVisibility(View.GONE);
        this.categoryList.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        loader.setVisibility(View.GONE);
    }

    @Override
    public void showCatalog(List<Category> categoryList) {
        hideLoader();
        customMessage.setVisibility(View.GONE);
        this.categoryList.setVisibility(View.VISIBLE);
        adapter = new CategoryAdapter(categoryList, this);
        this.categoryList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showCatalogEmpty() {
        hideLoader();
        adapter = new CategoryAdapter(new ArrayList<Category>(), this);
        categoryList.setVisibility(View.GONE);
        customMessage.setText(getString(R.string.category_fragment_endpoint_error));
        customMessage.setVisibility(View.VISIBLE);
    }

    public void updateConnectionStatus(boolean isOnline) {
        if (isOnline) {
            showLoader();
            categoryPresenter.loadCategories(40);
        }
    }

    @Override
    public void onCategoryClicked(View view) {
        int itemPosition = categoryList.getChildLayoutPosition(view);
        Category item = ((CategoryAdapter)adapter).getDataSet().get(itemPosition);
        Toast.makeText(getContext(), item.getAttributes().getLabel(), Toast.LENGTH_LONG).show();
    }
}
