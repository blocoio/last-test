package io.bloco.lasttest.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;
import io.bloco.lasttest.data.api.response.Image;
import java.util.List;

public class Artist implements ArtistModel {

  private long id;
  private String mbid;
  private String name;
  private String url;
  private String imageUrl;
  @SerializedName("playcount") private Long playCount;
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
    if (imageUrl == null && images != null && !images.isEmpty()) {
      imageUrl = images.get(images.size() - 1).getUrl();
    }
    return imageUrl;
  }

  @Nullable @Override public Long playCount() {
    return playCount;
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

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Artist artist = (Artist) o;

    return id == artist.id;
  }

  @Override public int hashCode() {
    return (int) (id ^ (id >>> 32));
  }

  public static final Factory<Artist> FACTORY = new Factory<>(new Creator<Artist>() {
    @Override
    public Artist create(@NonNull long id, @NonNull String mbid, @Nullable String name,
        @Nullable String url,
        @Nullable String image, @Nullable Long playCount) {
      Artist artist = new Artist();
      artist.setId(id);
      artist.setMbid(mbid);
      artist.setName(name);
      artist.setUrl(url);
      artist.setImage(image);
      artist.setPlayCount(playCount);
      return artist;
    }
  });
}
