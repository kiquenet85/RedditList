package com.app.ndiazgranados.catalog.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ndiazgranados.catalog.R;
import com.app.ndiazgranados.catalog.data.local.cache.DetailAppLocalCache;
import com.app.ndiazgranados.catalog.model.web.Entry;
import com.app.ndiazgranados.catalog.ui.presenter.DetailAppPresenter;
import com.app.ndiazgranados.catalog.ui.view.DetailAppView;
import com.squareup.picasso.Picasso;

/**
 * @author n.diazgranados
 */

public class DetailAppFragment extends BaseFragment implements DetailAppView {

    public static final String KEY_SELECTED_APP = "KEY_SELECTED_APP";

    private String nameSelectedApp;
    private ImageView appImage;
    private TextView description;
    private TextView name;
    private TextView price;
    private TextView artist;
    private TextView releaseDate;

    private DetailAppPresenter detailAppPresenter;

    public static DetailAppFragment newInstance(String selectedApp) {
        DetailAppFragment fragment = new DetailAppFragment();
        Bundle arguments = new Bundle();
        arguments.putString(KEY_SELECTED_APP, selectedApp);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailAppPresenter = appComponent.getDetailAppPresenter();

        if (savedInstanceState != null){
           nameSelectedApp = detailAppPresenter.searchInCache((long)savedInstanceState.get(DetailAppLocalCache.CACHE_DETAIL_APP_ITEM_KEY));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootFrag = inflater.inflate(R.layout.fragment_detail_app, container, false);

        appImage = (ImageView) rootFrag.findViewById(R.id.fragment_detail_img);
        description = (TextView) rootFrag.findViewById(R.id.fragment_detail_desc);
        name = (TextView) rootFrag.findViewById(R.id.fragment_detail_name);
        price = (TextView) rootFrag.findViewById(R.id.fragment_detail_price);
        artist = (TextView) rootFrag.findViewById(R.id.fragment_detail_artist);
        releaseDate = (TextView) rootFrag.findViewById(R.id.fragment_detail_release_date);

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
        detailAppPresenter.saveToCache(nameSelectedApp, outState);
    }

    @Override
    public void showDetailApp(Entry selectedApp) {
        String imageURL = selectedApp.getImImage().get(0).getLabel().replaceAll(getContext().getString(R.string.size_regex_top_app),
                getContext().getString(R.string.frag_top_apps_size_web_image));
        Picasso.with(getContext()).load(imageURL).into(appImage);
        description.setText(selectedApp.getSummary().getLabel());
        name.setText(selectedApp.getImName().getLabel());
        price.setText(selectedApp.getImPrice().getAttributes().getAmount());
        artist.setText(selectedApp.getImArtist().getLabel());
        releaseDate.setText(selectedApp.getImReleaseDate().getLabel());
    }

    @Override
    public void showDetailAppEmpty() {

    }
}
