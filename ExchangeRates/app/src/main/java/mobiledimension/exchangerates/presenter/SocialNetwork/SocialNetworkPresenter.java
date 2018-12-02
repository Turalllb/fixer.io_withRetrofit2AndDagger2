package mobiledimension.exchangerates.presenter.SocialNetwork;

import android.graphics.Bitmap;

import com.github.gorbin.asne.core.SocialNetworkManager;

import mobiledimension.exchangerates.presenter.base.MvpPresenter;
import mobiledimension.exchangerates.ui.SocialNetwork.SocialNetworkView;

public interface SocialNetworkPresenter<V extends SocialNetworkView> extends MvpPresenter<V> {

    void vkShare(Bitmap screenshot, SocialNetworkManager socialNetworkManager);

    String getVK_KEY ();

    String[] getVkScope();


}
