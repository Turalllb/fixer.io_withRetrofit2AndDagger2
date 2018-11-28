package mobiledimension.exchangerates.presenter.MainMenu;

import java.util.List;

import javax.inject.Singleton;

import mobiledimension.exchangerates.data.db.model.ModelData;
import mobiledimension.exchangerates.presenter.base.MvpPresenter;
import mobiledimension.exchangerates.ui.MainMenu.MainView;


public interface MainPresenter<V extends MainView> extends MvpPresenter<V> {

    void onChangedSortType(int checkedId);

    List<String> getCurrencies();

    List<ModelData> getRates();

    void currencyChanged(int position);

    void onDatePicked(String formattedDate);

    void setCurrentDate(String currentDate);

    String getCurrentCurrency();

}
