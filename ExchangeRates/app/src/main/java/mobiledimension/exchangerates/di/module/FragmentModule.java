package mobiledimension.exchangerates.di.module;

import dagger.Module;
import dagger.Provides;
import mobiledimension.exchangerates.di.PerFragment;
import mobiledimension.exchangerates.presenter.SocialNetwork.SocialNetwork;
import mobiledimension.exchangerates.presenter.SocialNetwork.SocialNetworkPresenter;
import mobiledimension.exchangerates.ui.SocialNetwork.SocialNetworkView;

@Module
public class FragmentModule {

    @Provides
    @PerFragment
    SocialNetworkPresenter<SocialNetworkView> provideSocialNetworkPresenter(SocialNetwork<SocialNetworkView> fragmentSocialNetworks) {
        return fragmentSocialNetworks;
    }

}
