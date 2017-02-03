package com.app.ndiazgranados.catalog.ui.interactor;

import com.app.ndiazgranados.catalog.model.web.Catalog;
import com.app.ndiazgranados.catalog.network.NetworkManager;
import com.app.ndiazgranados.catalog.settings.Settings;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

/**
 * @author n.diazgranados
 */
public class FetchCatalogInteractorImpl implements FetchCatalogInteractor {

    private NetworkManager networkManager;
    private Settings settings;
    private Bus eventBus;
    private int limitNumberOfTopApps;


    @Inject
    public FetchCatalogInteractorImpl(NetworkManager networkManager, Settings settings, Bus eventBus) {
        this.networkManager = networkManager;
        this.settings = settings;
        this.eventBus = eventBus;
        this.limitNumberOfTopApps = settings.getDefaultCathegoryAppsLimit();
    }

    @Override
    public void execute() {
        if (networkManager.isOnline()) {
            fetchCatalogEntries(limitNumberOfTopApps);
        } else {
            //TODO bring info from DB or File
        }
    }

    @Override
    public void setLimitNumberOftopApps(int limitNumberOfTopApps) {
        this.limitNumberOfTopApps = limitNumberOfTopApps;
    }

    @Override
    @Produce
    public Call<Catalog> fetchCatalogEntries(@Path("limitNumberOfTopApps") int limitNumberOfTopApps) {
        Call<Catalog> call = (networkManager.getDefaultRetrofit()).create(FetchCatalogInteractor.class).fetchCatalogEntries(limitNumberOfTopApps);
        // Fetch and print a list of the catalogs to the library.
        call.enqueue(new Callback<Catalog>() {
            @Override
            public void onResponse(Call<Catalog> call, Response<Catalog> response) {
                FetchCatalogEvent event = new FetchCatalogEvent();
                event.setResponse(call, response);
                eventBus.post(event);
            }

            @Override
            public void onFailure(Call<Catalog> call, Throwable t) {
                FetchCatalogEvent event = new FetchCatalogEvent();
                event.setBadResponse(call, t);
                eventBus.post(event);
            }
        });
        return call;
    }
}
