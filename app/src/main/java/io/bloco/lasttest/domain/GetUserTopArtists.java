package io.bloco.lasttest.domain;

import io.bloco.lasttest.common.di.PerApplication;
import io.bloco.lasttest.data.ArtistRepository;
import io.bloco.lasttest.data.UserRepository;
import io.bloco.lasttest.data.api.LastFmApi;
import io.bloco.lasttest.data.api.response.TopArtistsResponse;
import io.bloco.lasttest.data.model.Artist;
import io.bloco.lasttest.data.model.User;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@PerApplication
public class GetUserTopArtists {

  private final LastFmApi lastFmApi;
  private final UserRepository userRepository;
  private final ArtistRepository artistRepository;

  @Inject public GetUserTopArtists(LastFmApi lastFmApi, UserRepository userRepository,
      ArtistRepository artistRepository) {
    this.lastFmApi = lastFmApi;
    this.userRepository = userRepository;
    this.artistRepository = artistRepository;
  }

  public Observable<List<Artist>> get(boolean refresh) {
    if (refresh) {
      return loadFromApi();
    } else {
      List<Artist> artists = artistRepository.getAll();
      if (artists.isEmpty()) {
        return loadFromApi();
      } else {
        return Observable.just(artists);
      }
    }
  }

  private Observable<List<Artist>> loadFromApi() {
    return lastFmApi.getUserTopArtists(getUser().getName(), LastFmApi.PERIOD_MONTH)
        .map(new Func1<TopArtistsResponse, List<Artist>>() {
          @Override public List<Artist> call(TopArtistsResponse topArtistsResponse) {
            return topArtistsResponse.getTopArtists().getArtists();
          }
        }).doOnNext(new Action1<List<Artist>>() {
          @Override public void call(List<Artist> artists) {
            artistRepository.save(artists);
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  private User getUser() {
    return userRepository.get();
  }
}
