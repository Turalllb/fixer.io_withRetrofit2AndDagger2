package mobiledimension.exchangerates.ui.MainMenu;


import mobiledimension.exchangerates.ui.base.BaseView;

public interface MainView extends BaseView {

    void refreshSpinner();

    void spinnerSetSelection(int position);

    void refreshAdapterModelDate();

    void showToast(String message);
}
