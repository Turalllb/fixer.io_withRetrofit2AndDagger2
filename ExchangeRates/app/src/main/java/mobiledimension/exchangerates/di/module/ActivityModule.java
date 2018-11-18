package mobiledimension.exchangerates.di.module;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mobiledimension.exchangerates.Utils.NetworkChangeReceiver;
import mobiledimension.exchangerates.di.PerActivity;
import mobiledimension.exchangerates.presenter.MainMenu.MainMenuPresenter;
import mobiledimension.exchangerates.presenter.MainMenu.MainPresenter;
import mobiledimension.exchangerates.ui.MainMenu.MainView;

@Module
public class ActivityModule {

    @Provides
    public NetworkChangeReceiver provideNetworkChangeReceiver(){
        return new NetworkChangeReceiver();
    }

    @Provides
    public DatePickerDialog.OnDateSetListener provideOnDateSetListener(MainMenuPresenter<MainView> onDateSetListener){
        return onDateSetListener;
    }

}
