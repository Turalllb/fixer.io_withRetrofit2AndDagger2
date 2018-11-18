package mobiledimension.exchangerates.ui.MainMenu;

import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import mobiledimension.exchangerates.AdapterModelData;
import mobiledimension.exchangerates.MyApplication;
import mobiledimension.exchangerates.R;
import mobiledimension.exchangerates.Utils.NetworkChangeReceiver;
import mobiledimension.exchangerates.di.ActivityComponent;
import mobiledimension.exchangerates.di.DaggerActivityComponent;
import mobiledimension.exchangerates.presenter.MainMenu.MainPresenter;
import mobiledimension.exchangerates.ui.DataPickerFragment.DatePickerFragment;

import static mobiledimension.exchangerates.data.db.model.ModelData.COMPARATOR_NAME;


public class MainMenu extends AppCompatActivity implements MainView, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    @Inject
    NetworkChangeReceiver networkChangeReceiver;
    @Inject
    MainPresenter<MainView> mainPresenter;
    private TextView currentDateTextView;
    private ArrayAdapter<String> spinnerAdapter;
    private AdapterModelData adapterModelData;
    private Spinner spinnerOfCurrencies;
    private RadioGroup sortRadioGroup;
    private static ActivityComponent activityComponent;
    private DatePickerFragment datePickerFragment = new DatePickerFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(((MyApplication) getApplication()).getAppComponent())
                .build();
        activityComponent.inject(this);
        setContentView(R.layout.main_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //постоянно портретная ориентация
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);  //приложение на полный экран


        //region findViewById
        currentDateTextView = (TextView) findViewById(R.id.currentDateTextView);
        spinnerOfCurrencies = (Spinner) findViewById(R.id.spinnerCurrency);
        ListView listView = (ListView) findViewById(R.id.ListView);
        sortRadioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        //endregion

        setDate(mainPresenter.getCurrentDate());   //Предварительная установка текущей даты.

        adapterModelData = new AdapterModelData(this, R.layout.rates, mainPresenter.getRates()); //Адаптер списка с курсом валют
        listView.setAdapter(adapterModelData);

        spinnerAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, mainPresenter.getCurrencies()); //Адаптер для спиннера
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOfCurrencies.setAdapter(spinnerAdapter);
        spinnerOfCurrencies.setSelection(mainPresenter.getCurrencies().indexOf(mainPresenter.getCurrentCurrency())); //Предварительная установка валюты

        spinnerOfCurrencies.setOnItemSelectedListener(this);
        sortRadioGroup.setOnCheckedChangeListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        mainPresenter.attachView(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkChangeReceiver);
        mainPresenter.detachView();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mainPresenter.currencyChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        mainPresenter.onChangedSortType();
    }

    @Override
    public int getSelectedCurrencyPosition() {
        return spinnerOfCurrencies.getSelectedItemPosition();
    }

    @Override
    public int getCheckedRadioButtonId() {
        return sortRadioGroup.getCheckedRadioButtonId();
    }


    public void onClickDate(View view) {
        datePickerFragment.show(getSupportFragmentManager(), "DataPicker");
    }


    @Override
    public void refreshSpinner() {
        spinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void spinnerSetSelection(int position) {
        spinnerOfCurrencies.setSelection(position);
    }

    @Override
    public void refreshAdapterModelDate() {
        adapterModelData.refresh();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message, Toast.LENGTH_SHORT).show();
    }

    public static ActivityComponent getActivityComponent() {
        return activityComponent;
    }

}
