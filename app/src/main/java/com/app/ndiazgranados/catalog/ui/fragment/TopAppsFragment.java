package com.app.ndiazgranados.catalog.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ndiazgranados.catalog.R;
import com.app.ndiazgranados.catalog.data.local.cache.CategoryLocalCache;
import com.app.ndiazgranados.catalog.model.web.Entry;
import com.app.ndiazgranados.catalog.network.NetworkManager;
import com.app.ndiazgranados.catalog.ui.adapter.TopAppsAdapter;
import com.app.ndiazgranados.catalog.ui.presenter.TopAppsPresenter;
import com.app.ndiazgranados.catalog.ui.view.TopAppsView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author n.diazgranados
 */

public class TopAppsFragment extends BaseFragment implements TopAppsView, TopAppsAdapter.OnTopAppClickListener {

    public interface TopAppsFragmentInteractionListener {
        void showSelectedApp(String nameSelectedApp);
    }

    public static final int GRID_COLUMN_NUMBER = 3;
    public static final String KEY_SELECTED_CATEGORY = "KEY_SELECTED_CATEGORY";

    private TopAppsFragmentInteractionListener interactionListener;
    private RecyclerView appsGrid;
    private String nameSelectedCategory;
    private TextView customMessage;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager gridManager;

    private CategoryLocalCache categoryLocalCache;
    private TopAppsPresenter topAppsPresenter;
    private NetworkManager networkManager;

    public static TopAppsFragment newInstance(String selectedCategory) {
        TopAppsFragment fragment = new TopAppsFragment();
        Bundle arguments = new Bundle();
        arguments.putString(KEY_SELECTED_CATEGORY, selectedCategory);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryLocalCache = appComponent.getCategoryLocalCache();
        topAppsPresenter = appComponent.getTopAppsPresenter();
        networkManager = appComponent.getNetworkManager();

        if (savedInstanceState != null){
            nameSelectedCategory = topAppsPresenter.searchInCache((long)savedInstanceState.get(CategoryLocalCache.CACHE_SELECTED_CATEGORY_ITEM_KEY));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            interactionListener = (TopAppsFragmentInteractionListener) getActivity();
        } catch (ClassCastException ex) {
            throw new ClassCastException("Activity must implement TopAppsFragmentInteractionListener interface");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootFrag = inflater.inflate(R.layout.fragment_top_apps, container, false);

        appsGrid = (RecyclerView) rootFrag.findViewById(R.id.fragment_top_apps_list);
        customMessage = (TextView) rootFrag.findViewById(R.id.fragment_top_apps_message);

        appsGrid.setHasFixedSize(true);
        gridManager = new GridLayoutManager(getContext(), GRID_COLUMN_NUMBER);
        appsGrid.setLayoutManager(gridManager);
        adapter = new TopAppsAdapter(new ArrayList<Entry>(), this);
        appsGrid.setAdapter(adapter);
        final Bundle args = getArguments();

        if (args != null && nameSelectedCategory == null) {
            nameSelectedCategory = args.getString(KEY_SELECTED_CATEGORY);
        }

        return rootFrag;
    }

    @Override
    public void onResume() {
        super.onResume();
        topAppsPresenter.attachView(this);
        topAppsPresenter.registerOnBus(eventBus);

        if (!getActivity().isFinishing()) {
            topAppsPresenter.loadTopApps(nameSelectedCategory); ;
        }
    }

    @Override
    public void onPause() {
        topAppsPresenter.unregisterOnBus(eventBus);
        topAppsPresenter.detachView();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //As not detail is shown we can save null value.
        topAppsPresenter.saveToCache(nameSelectedCategory, outState);
    }

    @Override
    public void showTopApps(List<Entry> topAppList) {
        customMessage.setVisibility(View.GONE);
        this.appsGrid.setVisibility(View.VISIBLE);
        adapter = new TopAppsAdapter(topAppList, this);
        this.appsGrid.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void showTopAppsEmpty() {
        customMessage.setVisibility(View.GONE);
        adapter = new TopAppsAdapter(new ArrayList<Entry>(), this);
        appsGrid.setVisibility(View.GONE);
        customMessage.setText(getString(R.string.top_apps_fragment_endpoint_error));
        customMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTopAppClicked(View view) {
        int itemPosition = appsGrid.getChildLayoutPosition(view);
        Entry item = ((TopAppsAdapter) adapter).getDataSet().get(itemPosition);
        Toast.makeText(getContext(), item.getImName().getLabel(), Toast.LENGTH_LONG).show();
        interactionListener.showSelectedApp(item.getImName().getLabel());
    }
}
