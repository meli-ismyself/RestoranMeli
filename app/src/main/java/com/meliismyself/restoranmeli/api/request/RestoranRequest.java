package com.meliismyself.restoranmeli.api.request;

import com.meliismyself.restoranmeli.api.APIService;
import com.meliismyself.restoranmeli.api.ApiClient;
import com.meliismyself.restoranmeli.api.response.Restoran;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Meli Oktavia on 5/10/2016.
 */
public class RestoranRequest {
    private ApiClient apiClient;
    private Call<Restoran> request;
    private OnRestoranRequestListener listener;

    public RestoranRequest(OnRestoranRequestListener listener){
        apiClient = APIService.createService(ApiClient.class);
        this.listener = listener;
    }

    public void callApi(){
        request = apiClient.getRestoran();
        request.enqueue(new Callback<Restoran>() {
            @Override
            public void onResponse(Call<Restoran> call, Response<Restoran> response) {
                if (response != null && response.isSuccessful()){
                    Restoran mWeather = response.body();
                    listener.onRequestRestoranSuccess(mWeather);
                }else {
                    listener.onRequestRestoranFailure("Response Invalid");
                }
            }

            @Override
            public void onFailure(Call<Restoran> call, Throwable t) {
                String errorMessage = t.getMessage() != null?
                        t.getMessage() : "Unable to connect to server";
                listener.onRequestRestoranFailure(errorMessage);
            }
        });
    }

    public void cancelAPI(){
        if (request != null){
            request.cancel();
        }
    }
    public interface OnRestoranRequestListener{
        void onRequestRestoranSuccess(Restoran restoranResponse);
        void onRequestRestoranFailure(String message);
    }
}
