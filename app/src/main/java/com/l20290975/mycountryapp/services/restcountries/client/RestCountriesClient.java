package com.l20290975.mycountryapp.services.restcountries.client;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestCountriesClient {
    private static final String BASE_URL = "https://restcountries.com/v3.1/";

    public static final String FIELDS_VALUE = "name,cca3,flag,flags,capital,population";

    private static Retrofit _instance;

    public static Retrofit get_instance() {
        if ( _instance == null ) {
            _instance = new Retrofit.Builder()
                    .baseUrl(RestCountriesClient.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return _instance;
    }

}
