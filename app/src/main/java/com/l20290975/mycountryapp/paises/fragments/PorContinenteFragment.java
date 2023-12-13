package com.l20290975.mycountryapp.paises.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.l20290975.mycountryapp.R;
import com.l20290975.mycountryapp.databinding.FragmentPorContinenteBinding;
import com.l20290975.mycountryapp.paises.adapter.PaisAdapter;
import com.l20290975.mycountryapp.services.restcountries.api.RestCountriesApi;
import com.l20290975.mycountryapp.services.restcountries.client.RestCountriesClient;
import com.l20290975.mycountryapp.services.restcountries.models.Country;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PorContinenteFragment extends Fragment {

    private FragmentPorContinenteBinding viewBinding;

    private RestCountriesApi restCountriesApi;

    private CompositeDisposable compositeDisposable;

    private PaisAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewBinding = FragmentPorContinenteBinding.inflate(inflater, container, false);

        initComponents();

        return viewBinding.getRoot();
    }

    private void initComponents() {
        //Construir nuestro objeto de conexión a la API
        restCountriesApi = RestCountriesClient.get_instance().create(RestCountriesApi.class);
        //Preparar nuestro bote de basura
        compositeDisposable = new CompositeDisposable();

        //Llenar de información el spinner
        //Crear un adaptador
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.continentes,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewBinding.porContinenteSpiContinentes.setAdapter(adapter);

        //Configurar para obtener el valor seleccionado del spinner
        viewBinding.porContinenteSpiContinentes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PorContinenteFragment.this.onItemSelected(parent, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "Seleccione un elemento", Toast.LENGTH_SHORT).show();
            }
        });

        //Configurar el Recycler View
        viewBinding.porContinenteRVPaises.setLayoutManager( new LinearLayoutManager(getActivity()));

        //Configurar comportamiento del FabClear
        viewBinding.porContinenteFabClear.setOnClickListener((view) -> {
            viewBinding.porContinenteSpiContinentes.setSelection(0);
        });
    }

    private void onItemSelected(AdapterView<?> parent, int position) {
        if(position == 0) {
            setRecyclerViewAdapter(new ArrayList<>());
            return;
        }

        String continente = (String) parent.getAdapter().getItem(position);

        Toast.makeText(getContext(), "Elemento seleccionado: " + continente, Toast.LENGTH_SHORT).show();

        fetchCountriesByRegion(continente);
    }

    private void fetchCountriesByRegion(String continente) {
        compositeDisposable.add(
                restCountriesApi.getCountriesByRegion(continente, RestCountriesClient.FIELDS_VALUE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( this::setRecyclerViewAdapter, this::onRequestError )
        );
    }

    private void setRecyclerViewAdapter(List<Country> countries) {
        //Creando el adapter
        adapter = new PaisAdapter( getContext(), countries );

        //Enviando la información al Recycler View
        viewBinding.porContinenteRVPaises.setAdapter(adapter);
    }

    private void onRequestError(Throwable e) {
        setRecyclerViewAdapter( new ArrayList<>());
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}