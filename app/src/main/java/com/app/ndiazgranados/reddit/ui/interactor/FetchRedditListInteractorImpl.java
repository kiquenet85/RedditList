package com.app.ndiazgranados.reddit.ui.interactor;

import com.app.ndiazgranados.reddit.model.web.RedditList;
import com.app.ndiazgranados.reddit.network.NetworkManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author n.diazgranados
 */
public class FetchRedditListInteractorImpl implements FetchRedditListInteractor {

    private NetworkManager networkManager;
    private Bus eventBus;


    @Inject
    public FetchRedditListInteractorImpl(NetworkManager networkManager, Bus eventBus) {
        this.networkManager = networkManager;
        this.eventBus = eventBus;
    }

    @Override
    public void execute() {
        if (networkManager.isOnline()) {
            fetchRedditList();
        } else {
            //TODO bring info from DB or File
        }
    }

    @Override
    @Produce
    public Call<RedditList> fetchRedditList() {
        Call<RedditList> call = (networkManager.getDefaultRetrofit()).create(FetchRedditListInteractor.class).fetchRedditList();
        // Fetch and print a list of the catalogs to the library.
        call.enqueue(new Callback<RedditList>() {
            @Override
            public void onResponse(Call<RedditList> call, Response<RedditList> response) {
                FetchRedditListEvent event = new FetchRedditListEvent();
                event.setResponse(call, response);
                eventBus.post(event);
            }

            @Override
            public void onFailure(Call<RedditList> call, Throwable t) {
                FetchRedditListEvent event = new FetchRedditListEvent();
                event.setBadResponse(call, t);
                eventBus.post(event);
            }
        });
        return call;
    }
}
