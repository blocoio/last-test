package io.bloco.lasttest.ui.main;

import io.bloco.lasttest.common.di.PerActivity;
import io.bloco.lasttest.data.model.Artist;
import io.bloco.lasttest.data.model.User;
import io.bloco.lasttest.domain.GetUser;
import io.bloco.lasttest.domain.GetUserTopArtists;
import io.bloco.lasttest.domain.Logout;
import java.util.List;
import javax.inject.Inject;
import rx.Subscription;
import rx.functions.Action1;
import timber.log.Timber;

@PerActivity class MainPresenter {

  private final GetUser getUser;
  private final GetUserTopArtists getUserTopArtists;
  private final Logout logout;
  private View view;
  private Subscription artistsSubscription;

  @Inject MainPresenter(GetUser getUser, GetUserTopArtists getUserTopArtists, Logout logout) {
    this.getUser = getUser;
    this.getUserTopArtists = getUserTopArtists;
    this.logout = logout;
  }

  void start(View view) {
    this.view = view;

    User user = getUser.get();
    if (user == null) {
      view.openLogin();
      view.finish();
      return;
    }

    view.showUser(user);
    getArtists(false);
  }

  void stop() {
    if (artistsSubscription != null && !artistsSubscription.isUnsubscribed()) {
      artistsSubscription.unsubscribe();
    }
  }

  void artistsRefresh() {
    getArtists(true);
  }

  void logoutClick() {
    logout.logout();
    view.openLogin();
    view.finish();
  }

  void artistClicked(Artist artist) {
    view.openArtist(artist);
  }

  private void getArtists(boolean refresh) {
    artistsSubscription = getUserTopArtists.get(refresh).subscribe(
        new Action1<List<Artist>>() {
          @Override public void call(List<Artist> artists) {
            if (artists.isEmpty()) {
              view.showArtistsEmpty();
            } else {
              view.showArtists(artists);
            }
          }
        }
        , new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            Timber.e(throwable);
            view.showArtistsError();
          }
        });
  }

  interface View {
    void showUser(User user);

    void showArtists(List<Artist> artists);

    void showArtistsEmpty();

    void showArtistsError();

    void openArtist(Artist artist);

    void openLogin();

    void finish();
  }
}
