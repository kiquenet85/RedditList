package com.app.ndiazgranados.reddit.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.app.ndiazgranados.reddit.R;
import com.app.ndiazgranados.reddit.network.WifiReceiver;
import com.app.ndiazgranados.reddit.ui.fragment.DetailSubjectFragment;
import com.app.ndiazgranados.reddit.ui.fragment.SubjectFragment;
import com.google.gson.Gson;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

/**
 * @author n.diazgranados
 */
public class RedditActivity extends BaseActivity implements SubjectFragment.CategoryFragmentInteractionListener {

    public static String ACTIVITY_SUBJECT_LIST = "ACTIVITY_SUBJECT_LIST";
    private Gson gson;

    private CollapsingToolbarLayout collapsingToolbar;

    public static Intent createIntent(Context context, String redditSubjectList) {
        Intent intent = new Intent(context, RedditActivity.class);
        Bundle args = new Bundle();

        args.putString(ACTIVITY_SUBJECT_LIST, redditSubjectList);
        intent.putExtras(args);
        return intent;
    }

    //region lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolbar();
        gson = applicationComponent.getDefaultGson();

        if (savedInstanceState == null) {
            Bundle arguments = arguments = getIntent().getExtras();

            SubjectFragment subjectFragment = SubjectFragment.newInstance();
            if (arguments != null) {
                subjectFragment.setArguments(arguments);
            }
            addFragment(subjectFragment);
        }
    }

    private void setupToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(getString(R.string.toolbar_header_title));

        ImageView header = (ImageView) findViewById(R.id.header);
        Picasso.with(currentContext).load(R.drawable.reddit_splash).into(header);

        collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbar.setStatusBarScrimColor(getResources().getColor(R.color.colorAccent));
    }
    //endregion

    //region broadcast-events
    @Subscribe
    public void updateConnectionStatus(WifiReceiver.NetworkConnectionChange networkConnectionChange) {
        updateActiveConnectionIcon(networkConnectionChange.isOnline());
        SubjectFragment subjectFragment = (SubjectFragment) getFragmentFromActivity(SubjectFragment.class.getName());
        if (subjectFragment != null) {
            subjectFragment.updateConnectionStatus(networkConnectionChange.isOnline());
        }
    }

    @Override
    public void showSelectedSubject(String nameSelectedCategory) {
        pushFragment(R.id.fragment_container, DetailSubjectFragment.newInstance(nameSelectedCategory), DetailSubjectFragment.class.getName(),
                true, new CustomAnimations(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left,
                        R.anim.slide_in_from_left,
                        R.anim.slide_out_to_right));
    }
    //endregion
}
