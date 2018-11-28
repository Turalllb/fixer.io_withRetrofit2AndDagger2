package mobiledimension.exchangerates.di;

import dagger.Component;
import mobiledimension.exchangerates.data.network.ApiFixer;
import mobiledimension.exchangerates.di.module.ActivityModule;
import mobiledimension.exchangerates.di.module.DbModule;
import mobiledimension.exchangerates.di.module.PathModule;
import mobiledimension.exchangerates.ui.MainMenu.MainMenu;
import mobiledimension.exchangerates.ui.SocialNetwork.FragmentSocialNetworks;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(MainMenu mainMenu);

}
