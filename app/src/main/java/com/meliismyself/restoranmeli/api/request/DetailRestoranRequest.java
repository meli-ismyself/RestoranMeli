package com.meliismyself.restoranmeli.api.request;

import com.meliismyself.restoranmeli.api.APIService;
import com.meliismyself.restoranmeli.api.ApiClient;
import com.meliismyself.restoranmeli.api.response.DetailRestoran;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Meli Oktavia on 5/10/2016.
 */


public class DetailRestoranRequest {
    private ApiClient apiClient;
    private Call<DetailRestoran> request;
    private OnDetailRestoranRequestListener listener;

    public DetailRestoranRequest(OnDetailRestoranRequestListener listener){
        apiClient = APIService.createService(ApiClient.class);
        this.listener = listener;
    }

    public void callApi(String id){
        request = apiClient.getDetailRestoran(id);
        request.enqueue(new Callback<DetailRestoran>() {
            @Override
            public void onResponse(Call<DetailRestoran> call, Response<DetailRestoran> response) {
                if (response != null && response.isSuccessful()){
                    DetailRestoran mWeather = response.body();
                    listener.onDetailRequestRestoranSuccess(mWeather);
                }else {
                    listener.onDetailRequestRestoranFailure("Response Invalid");
                }
            }

            @Override
            public void onFailure(Call<DetailRestoran> call, Throwable t) {
                String errorMessage = t.getMessage() != null?
                        t.getMessage() : "Unable to connect to server";
                listener.onDetailRequestRestoranFailure(errorMessage);
            }
        });
    }

    public void cancelAPI(){
        if (request != null){
            request.cancel();
        }
    }
    public interface OnDetailRestoranRequestListener{
        void onDetailRequestRestoranSuccess(DetailRestoran detailRestoranResponse);
        void onDetailRequestRestoranFailure(String message);
    }
}
