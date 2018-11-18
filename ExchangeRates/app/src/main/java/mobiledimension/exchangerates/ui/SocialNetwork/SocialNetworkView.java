package mobiledimension.exchangerates.ui.SocialNetwork;

import android.graphics.Bitmap;

import com.github.gorbin.asne.core.SocialNetworkManager;

import mobiledimension.exchangerates.ui.base.BaseView;

public interface SocialNetworkView extends BaseView {


    Bitmap getScreenshot();

    void showToast(String message);

    SocialNetworkManager getSocialNetworkManager();

}
