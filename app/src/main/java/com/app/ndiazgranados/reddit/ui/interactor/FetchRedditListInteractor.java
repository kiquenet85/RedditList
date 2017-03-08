package com.app.ndiazgranados.reddit.ui.interactor;

import com.app.ndiazgranados.reddit.model.web.RedditList;
import com.app.ndiazgranados.reddit.network.ResponseEvent;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author n.diazgranados
 */
public interface FetchRedditListInteractor {

    /**
     * The fetching event produced by the interactor
     */
    class FetchRedditListEvent extends ResponseEvent<RedditList> {
        private String rawResponse;

        public String getRawResponse() {
            return rawResponse;
        }

        public void setRawResponse(String rawResponse) {
            this.rawResponse = rawResponse;
        }
    }

    @GET("/reddits.json")
    Call<RedditList> fetchRedditList();

    /**
     * Method to execute interactor in a background thread
     * @return
     */
    void execute();
}
