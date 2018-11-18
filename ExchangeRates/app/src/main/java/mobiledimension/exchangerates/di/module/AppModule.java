package mobiledimension.exchangerates.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mobiledimension.exchangerates.presenter.MainMenu.MainMenuPresenter;
import mobiledimension.exchangerates.presenter.MainMenu.MainPresenter;
import mobiledimension.exchangerates.presenter.SocialNetwork.SocialNetwork;
import mobiledimension.exchangerates.presenter.SocialNetwork.SocialNetworkPresenter;
import mobiledimension.exchangerates.ui.MainMenu.MainView;
import mobiledimension.exchangerates.ui.SocialNetwork.FragmentSocialNetworks;
import mobiledimension.exchangerates.ui.SocialNetwork.SocialNetworkView;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    public MainPresenter<MainView> provideMainPresenter(MainMenuPresenter<MainView> presenter) {
        return presenter;
    }

    @Provides
    @Singleton
    SocialNetworkPresenter<SocialNetworkView> provideSocialNetworkPresenter(SocialNetwork<SocialNetworkView> fragmentSocialNetworks){
        return fragmentSocialNetworks;
    }

}
