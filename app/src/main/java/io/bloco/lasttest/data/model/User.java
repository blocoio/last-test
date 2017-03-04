package io.bloco.lasttest.data.model;

import com.google.gson.annotations.SerializedName;
import io.bloco.lasttest.data.api.response.Image;
import java.util.List;

public class User {

  private long id;
  private String name;
  @SerializedName("realname") private String realName;
  private String url;
  private String country;
  @SerializedName("playcount") private long playCount;
  @SerializedName("image") private List<Image> images;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public long getPlayCount() {
    return playCount;
  }

  public void setPlayCount(long playCount) {
    this.playCount = playCount;
  }

  public List<Image> getImages() {
    return images;
  }

  public void setImages(List<Image> images) {
    this.images = images;
  }

  public String getImageUrl() {
    if (images == null || images.isEmpty()) {
      return null;
    }
    return images.get(images.size()-1).getUrl();
  }

  public String getBestName() {
    if (realName != null) {
      return realName;
    } else {
      return name;
    }
  }
}
