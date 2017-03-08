package com.app.ndiazgranados.reddit.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.ndiazgranados.reddit.R;
import com.app.ndiazgranados.reddit.data.local.cache.SubjectLocalCache;
import com.app.ndiazgranados.reddit.model.web.Child;
import com.app.ndiazgranados.reddit.ui.adapter.SubjectAdapter;
import com.app.ndiazgranados.reddit.ui.presenter.SubjectPresenter;
import com.app.ndiazgranados.reddit.ui.view.SubjectView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author n.diazgranados
 */

public class SubjectFragment extends BaseFragment implements SubjectView, SubjectAdapter.OnSubjectClickListener {

    public static final int LIMIT_NUMBER_OF_TOP_APPS = 200;

    public interface CategoryFragmentInteractionListener {
        void showSelectedSubject(String nameSelectedCategory);
    }

    private CategoryFragmentInteractionListener interactionListener;
    private RecyclerView subjectList;
    private ProgressBar loader;
    private TextView customMessage;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private SubjectLocalCache subjectLocalCache;
    private SubjectPresenter subjectPresenter;
    private Child selectedSubject;
    private Handler handler;

    public static SubjectFragment newInstance() {
        SubjectFragment fragment = new SubjectFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subjectLocalCache = appComponent.getSubjectLocalCache();
        subjectPresenter = appComponent.getCategoryPresenter();
        handler = new Handler();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootFrag = inflater.inflate(R.layout.fragment_subject, container, false);

        subjectList = (RecyclerView) rootFrag.findViewById(R.id.fragment_category_list);
        customMessage = (TextView) rootFrag.findViewById(R.id.fragment_category_message);
        loader = (ProgressBar) rootFrag.findViewById(R.id.fragment_category_loader);

        subjectList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        subjectList.setLayoutManager(layoutManager);
        adapter = new SubjectAdapter(new ArrayList<Child>(), this);
        subjectList.setAdapter(adapter);

        return rootFrag;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            interactionListener = (CategoryFragmentInteractionListener) getActivity();
        } catch (ClassCastException ex) {
            throw new ClassCastException("Activity must implement CategoryFragmentInteractionListener interface");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        subjectPresenter.attachView(this);
        subjectPresenter.registerOnBus(eventBus);

        if (!getActivity().isFinishing()) {
            showLoader();
            subjectPresenter.loadSubjects(LIMIT_NUMBER_OF_TOP_APPS);
        }
    }

    @Override
    public void onPause() {
        subjectPresenter.unregisterOnBus(eventBus);
        subjectPresenter.detachView();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //As not detail is shown we can save null value.
        subjectLocalCache.saveToCache(null, outState);
    }

    @Override
    public void showLoader() {
        customMessage.setVisibility(View.GONE);
        this.subjectList.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        loader.setVisibility(View.GONE);
    }

    @Override
    public void showRedditList(List<Child> categoryList) {
        hideLoader();
        customMessage.setVisibility(View.GONE);
        this.subjectList.setVisibility(View.VISIBLE);
        adapter = new SubjectAdapter(categoryList, this);
        this.subjectList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showRedditListEmpty() {
        hideLoader();
        adapter = new SubjectAdapter(new ArrayList<Child>(), this);
        subjectList.setVisibility(View.GONE);
        customMessage.setText(getString(R.string.category_fragment_endpoint_error));
        customMessage.setVisibility(View.VISIBLE);
    }

    public void updateConnectionStatus(boolean isOnline) {
        if (isOnline) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    showLoader();
                    subjectPresenter.loadSubjects(LIMIT_NUMBER_OF_TOP_APPS);
                }
            });
        }
    }

    @Override
    public void onSubjectClicked(View view) {
        int itemPosition = subjectList.getChildLayoutPosition(view);
        selectedSubject = ((SubjectAdapter) adapter).getDataSet().get(itemPosition);
        interactionListener.showSelectedSubject(selectedSubject.getData().getTitle());
    }
}
