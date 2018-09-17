package com.sailesh.sparks.networkmodule;

import com.sailesh.sparks.networkmodule.util.NetworkConstants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final RetrofitClient mRetrofitClientInstance = new RetrofitClient();
    private APIClient mApiClient;
    private OkHttpClient mOkHttpClient;


    private RetrofitClient() {

        mOkHttpClient = new OkHttpClient
                .Builder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstants.NETWORK_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient)
                .build();
        mApiClient = retrofit.create(APIClient.class);
    }


    public static RetrofitClient getInstance() {
        return mRetrofitClientInstance;
    }


    public APIClient getNetworkClient() {
        return mApiClient;
    }

    public void cancelAllRequests() {
        mOkHttpClient.dispatcher().cancelAll();
    }

}
