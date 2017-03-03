package io.bloco.lasttest.data.api;

import io.bloco.lasttest.data.api.response.TopArtistsResponse;
import io.bloco.lasttest.data.api.response.TopTracksResponse;
import io.bloco.lasttest.data.api.response.UserResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface LastFmApi {

  String API_KEY = "d8a23ad63769cd9784d086ca2aaa64b5";

  String PERIOD_MONTH = "1month";

  @GET("?method=user.getinfo&format=json&api_key=" + API_KEY)
  Call<UserResponse> getUserInfo(@Query("user") String user);

  @GET("?method=user.gettopartists&format=json&api_key=" + API_KEY)
  Observable<TopArtistsResponse> getUserTopArtists(
      @Query("user") String user, @Query("period") String period);

  @GET("?method=artist.gettoptracks&format=json&api_key=" + API_KEY)
  Call<TopTracksResponse> getArtistTopTracks(@Query("artist") String artistName);
}
