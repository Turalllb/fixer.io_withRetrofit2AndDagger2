package mobiledimension.exchangerates.presenter.MainMenu;

import android.app.DatePickerDialog;
import android.text.format.DateFormat;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import mobiledimension.exchangerates.R;
import mobiledimension.exchangerates.data.DataManager;
import mobiledimension.exchangerates.data.db.model.ModelData;
import mobiledimension.exchangerates.data.db.model.PostModel;
import mobiledimension.exchangerates.presenter.base.BasePresenter;
import mobiledimension.exchangerates.ui.DataPickerFragment.DatePickerFragment;
import mobiledimension.exchangerates.ui.MainMenu.MainView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static mobiledimension.exchangerates.data.db.model.ModelData.COMPARATOR_NAME;
import static mobiledimension.exchangerates.data.db.model.ModelData.COMPARATOR_VALUE_ASCENDING;
import static mobiledimension.exchangerates.data.db.model.ModelData.COMPARATOR_VALUE_DESCENDING;
import static mobiledimension.exchangerates.ui.DataPickerFragment.DatePickerFragment.isFirstCall;


public class MainMenuPresenter<V extends MainView> extends BasePresenter<V>
        implements MainPresenter<V>, DatePickerDialog.OnDateSetListener {

    public static final String LOG_TAG = "myLogs";
    private final String ACCESS_KEY = "0cd4416cd335bb08486b95e597b8c6b3"; //Для доступа к апи сайта. Есть ограничения в бесплатной версии.
    private String currentDate;
    private String currentCurrency = "EUR";
    private List<ModelData> ratesArrayList = new ArrayList<>(); //список из моделей (валюта курс)
    private List<String> currenciesArrayList = new ArrayList<>(Arrays.asList("EUR")); //Список валют для спиннера
    private DataManager dataManager;



    @Inject
    MainMenuPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
        currentDate = DateFormat.format("yyyy-MM-dd", new Date()).toString();
    }


    @Override
    public String getCurrentDate() {
        return currentDate;
    }

    @Override
    public void currencyChanged() {
        currentCurrency = currenciesArrayList.get(getView().getSelectedCurrencyPosition());
        uploadData();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (isFirstCall) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(calendar.getTime());
            getView().setDate(formattedDate);
            currentDate = formattedDate;
            uploadData();

            isFirstCall = false;
        }
    }


    private void uploadData() {
        ratesArrayList.clear(); //очищаю взарание список валют
        final PostModel postModel = dataManager.downloadFromDataBase(currentDate, currentCurrency);
        if (postModel != null) {
            setData(postModel);
        } else {
            dataManager.downloadDataFromNetwork(currentDate, ACCESS_KEY, currentCurrency, new Callback<PostModel>() {
                @Override
                public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                    PostModel postModel = response.body();
                    validationOfData(postModel);
                }

                @Override
                public void onFailure(Call<PostModel> call, Throwable t) {
                    getView().showToast("Сетевая ошибка");
                }
            });
        }
    }

    //Некоторые специфические для Fixer апи проверки
    private void validationOfData(PostModel postModel) {
        if (postModel.getDate() == null) {
            getView().refreshAdapterModelDate();
            getView().showToast("В бесплатной версии доступны только курсы по отношению к EUR");
        } else if (!postModel.getDate().equals(currentDate)) {
            getView().refreshAdapterModelDate(); //обновляю, чтобы показать List без результатов
            getView().showToast("Курсы обновляются в рабочие дни после 16.00 по msk");
        } else {
            //Если всё в порядке
            setData(postModel);
            //Сохраняю в БД
            dataManager.setDataBase(postModel);
        }
    }

    private void setData(PostModel postModel) {
        ratesArrayList.addAll(postModel.getRates());
        currenciesArrayList.clear();
        currenciesArrayList.addAll(postModel.getCurrenciesArrayList());
        Collections.sort(currenciesArrayList);
        getView().refreshSpinner();
        /* Не всегда в спиннере после обновления будет стоять валюта по которой сделан запрос,
           так как список спиннера тоже всегда обновляется, поэтому вручную устанавливаю текущую валюту*/
        getView().spinnerSetSelection(currenciesArrayList.indexOf(currentCurrency));
        sorting();
        getView().refreshAdapterModelDate();
    }

    @Override
    public void onDatePicked() {

    }


    public void onChangedSortType() {
        sorting();
        getView().refreshAdapterModelDate();
    }

    private void sorting() {
        switch (getView().getCheckedRadioButtonId()) {
            case R.id.radioButton1:
                Collections.sort(ratesArrayList, COMPARATOR_NAME);
                break;
            case R.id.radioButton2:
                Collections.sort(ratesArrayList, COMPARATOR_VALUE_ASCENDING);
                break;
            case R.id.radioButton3:
                Collections.sort(ratesArrayList, COMPARATOR_VALUE_DESCENDING);
                break;
        }
    }

    @Override
    public List<String> getCurrencies() {
        return currenciesArrayList;
    }

    @Override
    public List<ModelData> getRates() {
        return ratesArrayList;
    }

    @Override
    public String getCurrentCurrency() {
        return currentCurrency;
    }



}
