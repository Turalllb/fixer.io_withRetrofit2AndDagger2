package mobiledimension.exchangerates.presenter.MainMenu;

import mobiledimension.exchangerates.ui.MainMenu.MainView;

//Implementation of the pattern Null object
public class NoView implements MainView {


    public NoView() {
        try {
            throw new NullPointerException("View is null");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void refreshSpinner() {
    }

    @Override
    public void spinnerSetSelection(int position) {
    }

    @Override
    public void refreshAdapterModelDate() {
    }

    @Override
    public void showToast(String message) {
    }
}
