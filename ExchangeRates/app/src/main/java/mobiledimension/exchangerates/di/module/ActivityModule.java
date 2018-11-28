package mobiledimension.exchangerates.di.module;

import dagger.Module;
import dagger.Provides;
import mobiledimension.exchangerates.Utils.NetworkChangeReceiver;

@Module
public class ActivityModule {

    @Provides
    public NetworkChangeReceiver provideNetworkChangeReceiver() {
        return new NetworkChangeReceiver();
    }

}
