package mobiledimension.exchangerates.presenter.base;

import mobiledimension.exchangerates.ui.base.BaseView;

public class BasePresenter<V extends BaseView> implements MvpPresenter<V> {

    private V view;



    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    protected V getView(){
        return view;
    }
}
