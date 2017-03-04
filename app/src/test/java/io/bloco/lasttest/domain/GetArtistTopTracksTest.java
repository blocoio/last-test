package io.bloco.lasttest.domain;

import io.bloco.faker.Faker;
import io.bloco.lasttest.data.ArtistRepository;
import io.bloco.lasttest.data.TrackRepository;
import io.bloco.lasttest.data.api.LastFmApi;
import io.bloco.lasttest.data.api.response.TopTracksResponse;
import io.bloco.lasttest.data.model.Artist;
import io.bloco.lasttest.data.model.Track;
import io.bloco.lasttest.domain.common.DomainCallback;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import okhttp3.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetArtistTopTracksTest {

  @Mock LastFmApi lastFmApi;
  @Mock ArtistRepository artistRepository;
  @Mock TrackRepository trackRepository;
  @InjectMocks GetArtistTopTracks getArtistTopTracks;

  @Test
  public void get_fromApi() throws Exception {
    long artistId = 123;
    Artist artist = new Artist();
    artist.setName(new Faker().name.name());

    TopTracksResponse topTracksResponse = new TopTracksResponse();
    TopTracksResponse.TopTrack topTracks = new TopTracksResponse.TopTrack();
    topTracksResponse.setTopTracks(topTracks);
    List<Track> trackList = Collections.singletonList(new Track());
    topTracks.setTracks(trackList);

    when(trackRepository.getByArtist(eq(artistId))).thenReturn(Collections.EMPTY_LIST);
    when(artistRepository.get(eq(artistId))).thenReturn(artist);
    when(lastFmApi.getArtistTopTracks(eq(artist.name()))).thenReturn(buildCall(topTracksResponse));

    DomainCallback<List<Track>> domainCallback = mock(DomainCallback.class);
    getArtistTopTracks.get(artistId, domainCallback);

    verify(domainCallback).onSuccess(eq(trackList));
  }

  private Call<TopTracksResponse> buildCall(final TopTracksResponse topTracksResponse) {
    return new Call<TopTracksResponse>() {
      @Override public Response<TopTracksResponse> execute() throws IOException {
        return null;
      }

      @Override public void enqueue(Callback<TopTracksResponse> callback) {
        callback.onResponse(this, Response.success(topTracksResponse));
      }

      @Override public boolean isExecuted() {
        return false;
      }

      @Override public void cancel() {
      }

      @Override public boolean isCanceled() {
        return false;
      }

      @Override public Call<TopTracksResponse> clone() {
        return null;
      }

      @Override public Request request() {
        return null;
      }
    };
  }
}