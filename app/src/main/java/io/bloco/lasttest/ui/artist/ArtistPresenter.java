package io.bloco.lasttest.ui.artist;

import io.bloco.lasttest.data.model.Artist;
import io.bloco.lasttest.data.model.Track;
import io.bloco.lasttest.domain.GetArtist;
import io.bloco.lasttest.domain.GetArtistTopTracks;
import io.bloco.lasttest.domain.common.DomainCallback;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

class ArtistPresenter {

  private final GetArtist getArtist;
  private final GetArtistTopTracks getArtistTopTracks;
  private View view;
  private Artist artist;

  @Inject ArtistPresenter(GetArtist getArtist, GetArtistTopTracks getArtistTopTracks) {
    this.getArtist = getArtist;
    this.getArtistTopTracks = getArtistTopTracks;
  }

  void start(View view, long artistId) {
    this.view = view;

    artist = getArtist.get(artistId);
    view.showArtist(artist);

    getTracks(artistId);
  }

  private void getTracks(long artistId) {
    getArtistTopTracks.get(artistId, new DomainCallback<List<Track>>() {
      @Override public void onSuccess(List<Track> tracks) {
        if (tracks.isEmpty()) {
          view.showTracksEmpty();
        } else {
          view.showTracks(tracks);
        }
      }

      @Override public void onError(Throwable throwable) {
        Timber.e(throwable);
        view.showTracksError();
      }
    });
  }

  void onOpenClicked() {
    view.openArtistUrl(artist.url());
  }

  interface View {
    void showArtist(Artist artist);

    void showTracks(List<Track> tracks);

    void showTracksEmpty();

    void showTracksError();

    void openArtistUrl(String artistUrl);
  }
}
