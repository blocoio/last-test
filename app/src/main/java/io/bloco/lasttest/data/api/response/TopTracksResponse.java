package io.bloco.lasttest.data.api.response;

import com.google.gson.annotations.SerializedName;
import io.bloco.lasttest.data.model.Track;
import java.util.List;

public class TopTracksResponse {
  @SerializedName("toptracks")
  private TopTrack topTracks;

  public TopTrack getTopTracks() {
    return topTracks;
  }

  public static class TopTrack {
    @SerializedName("track")
    private List<Track> tracks;

    public List<Track> getTracks() {
      return tracks;
    }
  }
}
