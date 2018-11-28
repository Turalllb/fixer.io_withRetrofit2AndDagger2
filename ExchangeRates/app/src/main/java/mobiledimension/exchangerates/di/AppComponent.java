package mobiledimension.exchangerates.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import mobiledimension.exchangerates.MyApplication;
import mobiledimension.exchangerates.data.network.ApiFixer;
import mobiledimension.exchangerates.di.module.AppModule;
import mobiledimension.exchangerates.di.module.DbModule;
import mobiledimension.exchangerates.di.module.PathModule;
import mobiledimension.exchangerates.presenter.MainMenu.MainMenuPresenter;
import mobiledimension.exchangerates.presenter.MainMenu.MainPresenter;
import mobiledimension.exchangerates.presenter.SocialNetwork.SocialNetworkPresenter;
import mobiledimension.exchangerates.ui.MainMenu.MainMenu;
import mobiledimension.exchangerates.ui.MainMenu.MainView;
import mobiledimension.exchangerates.ui.SocialNetwork.SocialNetworkView;

@Singleton
@Component(modules = {AppModule.class, DbModule.class, ApiFixer.class, PathModule.class})
public interface AppComponent {

    Context context();

    MainPresenter<MainView> mainPresenter();

    MainMenuPresenter<MainView> mainMenuPresenter();

}
