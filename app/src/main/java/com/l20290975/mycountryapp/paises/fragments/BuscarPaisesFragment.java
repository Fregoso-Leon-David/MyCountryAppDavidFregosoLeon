package com.l20290975.mycountryapp.paises.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.l20290975.mycountryapp.R;
import com.l20290975.mycountryapp.databinding.FragmentBuscarPaisesBinding;
import com.l20290975.mycountryapp.paises.adapter.PaisAdapter;
import com.l20290975.mycountryapp.services.restcountries.api.RestCountriesApi;
import com.l20290975.mycountryapp.services.restcountries.client.RestCountriesClient;
import com.l20290975.mycountryapp.services.restcountries.models.Country;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class BuscarPaisesFragment extends Fragment {

    private FragmentBuscarPaisesBinding viewBinding;

    private RestCountriesApi restCountriesApi;

    private CompositeDisposable compositeDisposable;

    private PaisAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewBinding = FragmentBuscarPaisesBinding.inflate(inflater, container, false);
        initComponents();

        return viewBinding.getRoot();
    }

    private void initComponents() {

        //Inicializando la api
        restCountriesApi = RestCountriesClient.get_instance().create(RestCountriesApi.class);
        //Inicializamos el bote de basura
        compositeDisposable = new CompositeDisposable();

        viewBinding.buscarPaisTilBuscar.setEndIconOnClickListener(this::onEndIconClick);
        viewBinding.buscarPaisTilBuscar.getEditText().setOnEditorActionListener(this::onTilBuscarEnter);

        //Configurando el Recycler View
        viewBinding.buscarPaisRvPaises.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Configurar el fab
        viewBinding.buscarPaisFabClear.setOnClickListener((view) -> {
            viewBinding.buscarPaisTilBuscar.getEditText().setText("");
            setRecyclerViewAdapter(new ArrayList<>());
        });
    }

    private boolean onTilBuscarEnter(TextView textView, int actionId, KeyEvent keyEvent) {
        if(actionId == EditorInfo.IME_ACTION_SEARCH) {
            onEndIconClick(textView);
            return true;
        }

        return false;
    }

    private void onEndIconClick(View view) {
        //Cerrar el teclado virtual de android
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        String input = viewBinding.buscarPaisTilBuscar.getEditText().getText().toString();
        //Toast.makeText(getContext(), "Input: " + input, Toast.LENGTH_SHORT).show();

        fetchCountriesData(input);
    }

    private void fetchCountriesData(String noombre) {
        compositeDisposable.add(
                restCountriesApi.searchCountriesByName(noombre)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setRecyclerViewAdapter, this::onSearchError)
        );

    }

    private void setRecyclerViewAdapter(List<Country> countries) {
        adapter = new PaisAdapter(getContext(), countries);
        viewBinding.buscarPaisRvPaises.setAdapter(adapter);
    }

    private void onSearchError(Throwable e) {
        Toast.makeText(getContext(), "No se encontraron coincidencias con ese termino", Toast.LENGTH_SHORT).show();
        setRecyclerViewAdapter(new ArrayList<>());
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}