package io.bloco.lasttest.common.di;

import dagger.Subcomponent;
import io.bloco.lasttest.ui.artist.ArtistActivity;
import io.bloco.lasttest.ui.login.LoginActivity;
import io.bloco.lasttest.ui.main.MainActivity;

@PerActivity @Subcomponent(modules = ActivityModule.class) public interface ActivityComponent {
  ViewComponent plus(ViewModule viewModule);

  void inject(MainActivity activity);

  void inject(LoginActivity loginActivity);

  void inject(ArtistActivity artistActivity);
}
