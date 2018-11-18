package mobiledimension.exchangerates.ui.MainMenu;

import mobiledimension.exchangerates.ui.DataPickerFragment.DatePickerFragment;
import mobiledimension.exchangerates.ui.base.BaseView;

public interface MainView extends BaseView {


    void refreshSpinner();

    void spinnerSetSelection(int position);

    int getCheckedRadioButtonId();

    void refreshAdapterModelDate();

    void showToast(String message);

    int getSelectedCurrencyPosition();

}
