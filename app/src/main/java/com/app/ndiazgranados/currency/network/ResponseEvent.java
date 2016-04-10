package com.app.ndiazgranados.currency.network;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by user on 08/04/2016.
 */
public abstract class ResponseEvent<T> {
    private Call<T> call;
    private Response<T> response;
    private Throwable throwable;

    public void setResponse(Call<T> call, Response<T> response) {
        this.call = call;
        this.response = response;
    }

    public void setBadResponse(Call<T> call, Throwable t) {
        this.call = call;
        this.throwable = throwable;
    }

    public Call<T> getCall() {
        return call;
    }
    public Response<T> getResponse() {
        return response;
    }
    public Throwable getThrowable() {
        return throwable;
    }
}
