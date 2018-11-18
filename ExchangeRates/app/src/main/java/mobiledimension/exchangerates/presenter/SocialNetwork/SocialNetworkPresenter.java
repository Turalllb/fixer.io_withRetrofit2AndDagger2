package mobiledimension.exchangerates.presenter.SocialNetwork;

import mobiledimension.exchangerates.presenter.base.MvpPresenter;
import mobiledimension.exchangerates.ui.SocialNetwork.SocialNetworkView;

public interface SocialNetworkPresenter<V extends SocialNetworkView> extends MvpPresenter<V> {

    void vkShare();

    String getVK_KEY ();

    public String[] getVkScope();


}
