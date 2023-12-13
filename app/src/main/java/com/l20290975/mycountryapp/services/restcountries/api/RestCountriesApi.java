package com.l20290975.mycountryapp.services.restcountries.api;

import com.l20290975.mycountryapp.services.restcountries.models.Country;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestCountriesApi {
    @GET("name/{name}?fields=name,cca3,flag,flags,capital,population")
    Observable< List<Country> > searchCountriesByName(@Path("name") String nombre);

    @GET("alpha/{code}?fields=name,cca3,flag,flags,capital,population")
    Observable<Country> getCountryByCca3(@Path("code") String cca3);

    @GET("region/{region}")
    Observable<List<Country> > getCountriesByRegion(
            @Path("region") String continente,
            @Query("fields") String campos
    );


}
