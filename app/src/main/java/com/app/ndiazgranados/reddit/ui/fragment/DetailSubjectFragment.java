package com.app.ndiazgranados.reddit.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ndiazgranados.reddit.R;
import com.app.ndiazgranados.reddit.data.local.cache.DetailSubjectLocalCache;
import com.app.ndiazgranados.reddit.model.web.Child;
import com.app.ndiazgranados.reddit.ui.presenter.DetailSubjectPresenter;
import com.app.ndiazgranados.reddit.ui.view.DetailSubjectView;
import com.app.ndiazgranados.reddit.util.MyBounceInterpolator;
import com.app.ndiazgranados.reddit.util.StringUtil;
import com.squareup.picasso.Picasso;

/**
 * @author n.diazgranados
 */

public class DetailSubjectFragment extends BaseFragment implements DetailSubjectView {

    public static final String KEY_SELECTED_SUBJECT = "KEY_SELECTED_SUBJECT";

    private String nameSelectedApp;
    private ImageView appImage;
    private CardView imgContainer;
    private TextView description;
    private TextView name;
    private TextView numSubscribers;
    private TextView lang;
    private TextView subredditType;

    private DetailSubjectPresenter detailSubjectPresenter;

    public static DetailSubjectFragment newInstance(String selectedApp) {
        DetailSubjectFragment fragment = new DetailSubjectFragment();
        Bundle arguments = new Bundle();
        arguments.putString(KEY_SELECTED_SUBJECT, selectedApp);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailSubjectPresenter = appComponent.getDetailAppPresenter();

        if (savedInstanceState != null){
           nameSelectedApp = detailSubjectPresenter.searchInCache((long)savedInstanceState.get(DetailSubjectLocalCache.CACHE_DETAIL_APP_ITEM_KEY));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootFrag = inflater.inflate(R.layout.fragment_detail_subject, container, false);

        appImage = (ImageView) rootFrag.findViewById(R.id.fragment_detail_img);
        imgContainer = (CardView) rootFrag.findViewById(R.id.detail_app_image_container);
        description = (TextView) rootFrag.findViewById(R.id.fragment_detail_desc);
        name = (TextView) rootFrag.findViewById(R.id.fragment_detail_name);
        numSubscribers = (TextView) rootFrag.findViewById(R.id.fragment_detail_num_subscribers);
        lang = (TextView) rootFrag.findViewById(R.id.fragment_detail_lang);
        subredditType = (TextView) rootFrag.findViewById(R.id.fragment_detail_subreddit_type);

        final Bundle args = getArguments();

        if (args != null && nameSelectedApp == null) {
            nameSelectedApp = args.getString(KEY_SELECTED_SUBJECT);
        }
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        animation.setInterpolator(interpolator);
        imgContainer.startAnimation(animation);
        return rootFrag;
    }

    @Override
    public void onResume() {
        super.onResume();
        detailSubjectPresenter.attachView(this);
        detailSubjectPresenter.registerOnBus(eventBus);

        if (!getActivity().isFinishing()) {
            detailSubjectPresenter.loadSelectedSubject(nameSelectedApp); ;
        }
    }

    @Override
    public void onPause() {
        detailSubjectPresenter.unregisterOnBus(eventBus);
        detailSubjectPresenter.detachView();
        super.onPause();
        imgContainer.clearAnimation();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        detailSubjectPresenter.saveToCache(nameSelectedApp, outState);
    }

    @Override
    public void showDetailSubject(Child selectedSubject) {
        String imageURL = selectedSubject.getData().getIconImg();
        if (imageURL != null && !imageURL.isEmpty()) {
            Picasso.with(getContext()).load(imageURL).into(appImage);
        }
        Spanned text = Html.fromHtml(selectedSubject.getData().getDescription());
        description.setText(text);
        name.setText(StringUtil.getUnknownIfEmpty(selectedSubject.getData().getTitle()));
        numSubscribers.setText(StringUtil.getUnknownIfEmpty(Integer.toString(selectedSubject.getData().getSubscribers())));
        lang.setText(StringUtil.getUnknownIfEmpty(selectedSubject.getData().getLang()));
        subredditType.setText(StringUtil.getUnknownIfEmpty(selectedSubject.getData().getSubredditType()));
    }

    @Override
    public void showDetailSubjectEmpty() {

    }
}
