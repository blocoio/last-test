package io.bloco.lasttest.data.api.response;

import com.google.gson.annotations.SerializedName;
import io.bloco.lasttest.data.model.Artist;
import java.util.List;

public class TopArtistsResponse {
  @SerializedName("topartists")
  private TopArtists topArtists;

  public TopArtists getTopArtists() {
    return topArtists;
  }

  public static class TopArtists {
    @SerializedName("artist")
    private List<Artist> artists;

    public List<Artist> getArtists() {
      return artists;
    }
  }
}
