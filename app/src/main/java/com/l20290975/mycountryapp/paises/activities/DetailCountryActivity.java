package com.l20290975.mycountryapp.paises.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.l20290975.mycountryapp.databinding.ActivityDetailCountryBinding;
import com.l20290975.mycountryapp.services.restcountries.api.RestCountriesApi;
import com.l20290975.mycountryapp.services.restcountries.client.RestCountriesClient;
import com.l20290975.mycountryapp.services.restcountries.models.Country;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DetailCountryActivity extends AppCompatActivity {
    private ActivityDetailCountryBinding viewBinding;
    private RestCountriesApi restCountriesApi;

    private Country pais;
    private CompositeDisposable compositeDisposable = new CompositeDisposable(); // Inicializar el CompositeDisposable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityDetailCountryBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        initComponents();
    }

    private void initComponents() {
        //Obteniendo el codigo cca3
        String cca3 = getIntent().getStringExtra("cca3");
        Toast.makeText(this, "cca3: " + cca3, Toast.LENGTH_SHORT).show();

        //Iniciar la api
        restCountriesApi = RestCountriesClient.get_instance().create(RestCountriesApi.class);

        //Solicitar la información del país con el código cca3
        fetchCountryDataByCca3(cca3);


    }

    private void fetchCountryDataByCca3(String cca3) {
        compositeDisposable.add(
                restCountriesApi.getCountryByCca3(cca3)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::onCountryDataReceived, this::onError)
        );
    }

    private void onCountryDataReceived(Country country) {
        // Actualizar las vistas con la información del país
        pais = country;
        updateViews(country);
        setTitle(pais.getFlag() + " " + pais.getName().getCommon());
    }

    private void updateViews(Country country) {
        try {
            viewBinding.countryItemtvPaisNombre.setText(country.getName().getOfficial());
            viewBinding.countryItemTvCapital.setText(country.getCapital().get(0));
            viewBinding.countryItemTvCountryPopulation.setText(String.valueOf(country.getPopulation()));

            Glide.with(this)
                    .load(country.getFlags().getPNG()) // URL de la imagen de la bandera
                    .into(viewBinding.countryItemSivPaisImg);
        } catch (Exception e) {

        }

    }

    private void onError(Throwable throwable) {
        Toast.makeText(this, "Error al obtener los datos del país", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
