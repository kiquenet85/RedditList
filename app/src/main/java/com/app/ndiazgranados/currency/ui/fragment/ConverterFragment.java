package com.app.ndiazgranados.currency.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.ndiazgranados.currency.R;
import com.app.ndiazgranados.currency.data.local.cache.ConverterLocalCache;
import com.app.ndiazgranados.currency.model.CurrencyItem;
import com.app.ndiazgranados.currency.model.presenter.ConverterPresenter;
import com.app.ndiazgranados.currency.model.web.Rate;
import com.app.ndiazgranados.currency.network.NetworkManager;
import com.app.ndiazgranados.currency.ui.adapter.ConverterAdapter;
import com.app.ndiazgranados.currency.ui.view.ConverterView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * A placeholder ui.fragment containing a simple view.
 */
public class ConverterFragment extends BaseFragment implements ConverterView, AdapterView.OnItemSelectedListener {

    private RecyclerView currencyList;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button btnCurrencyRates;
    private EditText editInputCurrency;
    private Spinner selectedCurrencyKey;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private String selectedCurrency = Rate.USD_CURRENCY;

    private NetworkManager networkManager;

    @Inject
    ConverterPresenter converterPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_converter, container, false);
        networkManager = appComponent.getNetworkManager();
        converterPresenter = appComponent.getConverterPresenter();
        converterPresenter.attachView(this);

        currencyList = (RecyclerView) view.findViewById(R.id.fragment_converter_currency_list);
        btnCurrencyRates = (Button) view.findViewById(R.id.fragment_converter_btn_currency);
        editInputCurrency = (EditText) view.findViewById(R.id.fragment_converter_currency_input);
        selectedCurrencyKey = (Spinner) view.findViewById(R.id.fragment_converter_currency_key);

        spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.currency_key_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectedCurrencyKey.setAdapter(spinnerAdapter);
        int defaultSelection = spinnerAdapter.getPosition(selectedCurrency);
        selectedCurrencyKey.setSelection(defaultSelection);
        selectedCurrencyKey.setOnItemSelectedListener(this);

        btnCurrencyRates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!networkManager.isOnline()){
                    Toast.makeText(getActivity(), "Please check your internet connection!", Toast.LENGTH_LONG).show();
                }
                else if (validateCorrectDataInput()) {
                    Integer usdValue = Integer.valueOf(editInputCurrency.getText().toString());
                    if (savedInstanceState != null) {
                        converterPresenter.loadCurrencies(usdValue, (Long) savedInstanceState.getLong(ConverterLocalCache.CACHE_CURRENCY_ITEMS_KEY), selectedCurrency);
                    } else {
                        converterPresenter.loadCurrencies(usdValue, null, selectedCurrency);
                    }
                } else {
                    Toast.makeText(getActivity(), "Please input an Integer up to 9 digits", Toast.LENGTH_LONG).show();
                }
            }
        });

        currencyList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        currencyList.setLayoutManager(layoutManager);
        adapter = new ConverterAdapter(new ArrayList<CurrencyItem>(), 0);
        currencyList.setAdapter(adapter);

        if (savedInstanceState != null) {
            converterPresenter.checkCache((Long) savedInstanceState.getLong(ConverterLocalCache.CACHE_CURRENCY_ITEMS_KEY));
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        converterPresenter.saveCache(((ConverterAdapter) adapter).getDataSet(), outState);
    }

    public void onResume() {
        super.onResume();
        converterPresenter.registerOnBus(eventBus);
    }

    public void onPause() {
        super.onPause();
        converterPresenter.unregisterOnBus(eventBus);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        converterPresenter.detachView();
    }

    @Override
    public void showAllCurrencies(List<CurrencyItem> currencyItems, int usdValue, String selectedCurrency) {
        this.selectedCurrency = selectedCurrency;
        int defaultSelection = spinnerAdapter.getPosition(selectedCurrency);
        selectedCurrencyKey.setSelection(defaultSelection);

        editInputCurrency.setText(usdValue + "");
        adapter = new ConverterAdapter(currencyItems, usdValue);
        currencyList.setAdapter(adapter);
    }

    /**
     *  Validate input data
     */
    public boolean validateCorrectDataInput(){
       return  selectedCurrency != null //Some currency selected
               && !editInputCurrency.getText().toString().isEmpty() //Not empty value to find rates
               && editInputCurrency.getText().toString().matches("[0-9]+") //Validate Integer Number
               && editInputCurrency.length() <= 9; //Validate Integer capacity
    }

    @Override
    public void showCurrenciesEmpty(List<CurrencyItem> currency) {
        currencyList.removeAllViews();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCurrency = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedCurrency = null;
    }
}
