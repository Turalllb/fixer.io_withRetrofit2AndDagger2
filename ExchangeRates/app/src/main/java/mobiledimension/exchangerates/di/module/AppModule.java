package mobiledimension.exchangerates.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mobiledimension.exchangerates.presenter.MainMenu.MainMenuPresenter;
import mobiledimension.exchangerates.presenter.MainMenu.MainPresenter;
import mobiledimension.exchangerates.ui.MainMenu.MainView;

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



}
