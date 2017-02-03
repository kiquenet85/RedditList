package com.app.ndiazgranados.catalog.ui.interactor;

import com.app.ndiazgranados.catalog.model.web.Catalog;
import com.app.ndiazgranados.catalog.network.ResponseEvent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author n.diazgranados
 */
public interface FetchCatalogInteractor {

    /**
     * The fetching event produced by the interactor
     */
    class FetchCatalogEvent extends ResponseEvent<Catalog> {
        private String rawResponse;

        public String getRawResponse() {
            return rawResponse;
        }

        public void setRawResponse(String rawResponse) {
            this.rawResponse = rawResponse;
        }
    }

    void setLimitNumberOftopApps (int limitNumberOfTopApps);
    @GET("/us/rss/topfreeapplications/limit={limitNumberOfTopApps}/json")
    Call<Catalog> fetchCatalogEntries(@Path("limitNumberOfTopApps") int limitNumberOfTopApps);

    /**
     * Method to execute interactor in a background thread
     * @return
     */
    void execute();
}
