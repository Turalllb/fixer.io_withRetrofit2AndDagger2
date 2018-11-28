package mobiledimension.exchangerates.di;

import dagger.Component;
import mobiledimension.exchangerates.di.module.FragmentModule;
import mobiledimension.exchangerates.ui.SocialNetwork.FragmentSocialNetworks;

@PerFragment
@Component(dependencies = {AppComponent.class}, modules = {FragmentModule.class})
public interface FragmentComponent {

    void inject(FragmentSocialNetworks fragmentSocialNetworks);


}
