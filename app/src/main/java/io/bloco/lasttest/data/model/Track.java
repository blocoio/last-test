package io.bloco.lasttest.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;
import io.bloco.lasttest.data.api.response.Image;
import java.util.List;

public class Track implements TrackModel {

  private long id;
  private String mbid;
  private String name;
  private String url;
  private String imageUrl;
  @SerializedName("playcount") private Long playCount;
  private Long listeners;
  private long artistId;
  @SerializedName("image") private List<Image> images;

  @Override public long id() {
    return id;
  }

  @NonNull @Override public String mbid() {
    return mbid;
  }

  @Nullable @Override public String name() {
    return name;
  }

  @Nullable @Override public String url() {
    return url;
  }

  @Nullable @Override public String imageUrl() {
    if (imageUrl == null) {
      imageUrl = images.get(images.size() - 1).getUrl();
    }
    return imageUrl;
  }

  @Nullable @Override public Long playCount() {
    return playCount;
  }

  @Nullable @Override public Long listeners() {
    return listeners;
  }

  @NonNull @Override public long artistId() {
    return artistId;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setMbid(String mbid) {
    this.mbid = mbid;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setImage(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public void setPlayCount(Long playCount) {
    this.playCount = playCount;
  }

  public void setListeners(Long listeners) {
    this.listeners = listeners;
  }

  public void setArtistId(long artistId) {
    this.artistId = artistId;
  }

  public static final Factory<Track> FACTORY = new Factory<>(new Creator<Track>() {
    @Override
    public Track create(long id, @NonNull String mbid, @Nullable String name, @Nullable String url,
        @Nullable String image, @Nullable Long playCount, @Nullable Long listeners, long artistId) {
      Track track = new Track();
      track.setId(id);
      track.setMbid(mbid);
      track.setName(name);
      track.setUrl(url);
      track.setImage(image);
      track.setPlayCount(playCount);
      track.setListeners(listeners);
      track.setArtistId(artistId);
      return track;
    }
  });
}
