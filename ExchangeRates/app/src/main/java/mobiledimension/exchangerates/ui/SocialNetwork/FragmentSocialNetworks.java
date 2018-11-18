package mobiledimension.exchangerates.ui.SocialNetwork;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.SocialNetworkManager;
import com.github.gorbin.asne.vk.VkSocialNetwork;

import java.util.List;

import javax.inject.Inject;

import mobiledimension.exchangerates.R;
import mobiledimension.exchangerates.presenter.SocialNetwork.SocialNetworkPresenter;

import static mobiledimension.exchangerates.ui.MainMenu.MainMenu.getActivityComponent;

/**
 * Created by Турал on 11.12.2017.
 */

public class FragmentSocialNetworks extends Fragment implements SocialNetworkView,  SocialNetworkManager.OnInitializationCompleteListener {

    @Inject
    SocialNetworkPresenter<SocialNetworkView> socialNetworkPresenter;
    SocialNetworkManager socialNetworkManager;
    private final String SOCIAL_NETWORK_TAG = "SocialIntegrationMain.SOCIAL_NETWORK_TAG";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment, null);

        getActivityComponent().inject(this);


        socialNetworkManager = (SocialNetworkManager) getFragmentManager().findFragmentByTag(SOCIAL_NETWORK_TAG);
        if (socialNetworkManager == null) {
            socialNetworkManager = new SocialNetworkManager();

            //Init and add to manager VkSocialNetwork
            VkSocialNetwork vkNetwork = new VkSocialNetwork(this,socialNetworkPresenter.getVK_KEY(), socialNetworkPresenter.getVkScope());
            socialNetworkManager.addSocialNetwork(vkNetwork);

            //Initiate every network from socialNetworkManager
            getFragmentManager().beginTransaction().add(socialNetworkManager, SOCIAL_NETWORK_TAG).detach(socialNetworkManager).commit();

            socialNetworkManager.setOnInitializationCompleteListener(this);
        } else {
            //if manager exist - get and setup login only for initialized SocialNetworks
            if (!socialNetworkManager.getInitializedSocialNetworks().isEmpty()) {
                List<SocialNetwork> socialNetworks = socialNetworkManager.getInitializedSocialNetworks();
                for (com.github.gorbin.asne.core.SocialNetwork socialNetwork : socialNetworks) {
                    //socialNetwork.setOnLoginCompleteListener(this);
                    initSocialNetwork(socialNetwork);
                }
            }
        }

        Button VK_share = (Button) v.findViewById(R.id.VK_share);
        VK_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socialNetworkPresenter.vkShare();
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        socialNetworkPresenter.attachView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        socialNetworkPresenter.detachView();
    }


    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }



    @Override
    public Bitmap getScreenshot() {
        View rootView = getActivity().findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    @Override
    public void onSocialNetworkManagerInitialized() {
        //when init SocialNetworks - get and setup login only for initialized SocialNetworks
        for (com.github.gorbin.asne.core.SocialNetwork socialNetwork : socialNetworkManager.getInitializedSocialNetworks()) {
            initSocialNetwork(socialNetwork);
        }
    }

    private void initSocialNetwork(com.github.gorbin.asne.core.SocialNetwork socialNetwork) {
        if (socialNetwork.isConnected()) {
            switch (socialNetwork.getID()) {
                case VkSocialNetwork.ID:
                    // vk_share.setText("Show VK profile");
                    break;
            }
        }
    }

    public SocialNetworkManager getSocialNetworkManager() {
        return socialNetworkManager;
    }


}


