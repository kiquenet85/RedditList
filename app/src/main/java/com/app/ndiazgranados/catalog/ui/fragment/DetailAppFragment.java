package com.app.ndiazgranados.catalog.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.ndiazgranados.catalog.R;
import com.app.ndiazgranados.catalog.model.web.Entry;
import com.app.ndiazgranados.catalog.ui.presenter.DetailAppPresenter;
import com.app.ndiazgranados.catalog.ui.view.DetailAppView;

import static com.app.ndiazgranados.catalog.ui.fragment.TopAppsFragment.KEY_SELECTED_CATEGORY;

/**
 * @author n.diazgranados
 */

public class DetailAppFragment extends BaseFragment implements DetailAppView {

    public static final String KEY_SELECTED_APP = "KEY_SELECTED_APP";

    private String nameSelectedApp;
    private TextView customMessage;

    private DetailAppPresenter detailAppPresenter;

    public static DetailAppFragment newInstance(String selectedApp) {
        DetailAppFragment fragment = new DetailAppFragment();
        Bundle arguments = new Bundle();
        arguments.putString(KEY_SELECTED_CATEGORY, selectedApp);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailAppPresenter = appComponent.getDetailAppPresenter();

        if (savedInstanceState != null){
            nameSelectedApp = detailAppPresenter.searchInCache((long)savedInstanceState.get("LOCAL_CACHE_DETAIL_APP"));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootFrag = inflater.inflate(R.layout.fragment_detail_app, container, false);

        customMessage = (TextView) rootFrag.findViewById(R.id.fragment_detail_app_message);
        final Bundle args = getArguments();

        if (args != null && nameSelectedApp == null) {
            nameSelectedApp = args.getString(KEY_SELECTED_APP);
        }

        return rootFrag;
    }

    @Override
    public void onResume() {
        super.onResume();
        detailAppPresenter.attachView(this);
        detailAppPresenter.registerOnBus(eventBus);

        if (!getActivity().isFinishing()) {
            detailAppPresenter.loadSelectedApp(nameSelectedApp); ;
        }
    }

    @Override
    public void onPause() {
        detailAppPresenter.unregisterOnBus(eventBus);
        detailAppPresenter.detachView();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //As not detail is shown we can save null value.
        detailAppPresenter.saveToCache(nameSelectedApp, outState);
    }

    @Override
    public void showTopApps(Entry selectedApp) {
        customMessage.setVisibility(View.GONE);
    }


    @Override
    public void showTopAppsEmpty() {
        customMessage.setVisibility(View.GONE);
        customMessage.setText(getString(R.string.top_apps_fragment_endpoint_error));
        customMessage.setVisibility(View.VISIBLE);
    }
}
