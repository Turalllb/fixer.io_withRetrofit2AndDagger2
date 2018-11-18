package mobiledimension.exchangerates.presenter.base;

import mobiledimension.exchangerates.ui.base.BaseView;

public interface MvpPresenter<V extends BaseView> {

    void attachView(V view);

    void detachView();
}
