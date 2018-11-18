package mobiledimension.exchangerates.presenter.SocialNetwork;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.github.gorbin.asne.core.listener.OnPostingCompleteListener;
import com.github.gorbin.asne.vk.VkSocialNetwork;
import com.vk.sdk.VKScope;

import javax.inject.Inject;

import mobiledimension.exchangerates.presenter.base.BasePresenter;
import mobiledimension.exchangerates.ui.SocialNetwork.SocialNetworkView;


public class SocialNetwork<V extends SocialNetworkView> extends BasePresenter<V> implements SocialNetworkPresenter<V> {

    private final String VK_KEY = "6040457";
    private String[] vkScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.STATUS,
    };
    private OnPostingCompleteListener postingComplete = new OnPostingCompleteListener() {
        @Override
        public void onPostSuccessfully(int socialNetworkID) {
            try {
                //нет необходимости
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
            getView().showToast("Error while sending: " + errorMessage);
        }
    };

    @Inject
    public SocialNetwork() {
    }

    @Override
    public void vkShare() {
        int networkId = 0; //на случай если будут кнопки от других соц сетей
        networkId = VkSocialNetwork.ID;
        Bitmap screenshot = getView().getScreenshot();
        com.github.gorbin.asne.core.SocialNetwork socialNetwork = getView().getSocialNetworkManager().getSocialNetwork(networkId);
        if (!socialNetwork.isConnected()) {
            if (networkId != 0) {
                socialNetwork.requestLogin();
                getView().showToast("После авторизации нажмите повторно на публикацию");
            } else {
                getView().showToast("Wrong networkId");
            }
        } else {
            Bundle postParams = new Bundle();
            postParams.putString(com.github.gorbin.asne.core.SocialNetwork.BUNDLE_LINK, "https://fixer.io");

            socialNetwork.requestPostPhotoMessageLink(screenshot, postParams, "ExchangeRates", postingComplete);
            getView().showToast("Опубликовано");
        }
    }


    public String getVK_KEY() {
        return VK_KEY;
    }

    public String[] getVkScope() {
        return vkScope;
    }


}
