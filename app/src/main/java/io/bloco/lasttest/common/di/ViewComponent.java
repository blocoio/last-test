package io.bloco.lasttest.common.di;

import dagger.Subcomponent;
import io.bloco.lasttest.ui.artist.TrackItemView;
import io.bloco.lasttest.ui.main.ArtistItemView;

@PerView @Subcomponent(modules = ViewModule.class) public interface ViewComponent {
  void inject(ArtistItemView artistItemView);

  void inject(TrackItemView trackItemView);
}