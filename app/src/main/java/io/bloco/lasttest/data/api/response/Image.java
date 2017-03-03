package io.bloco.lasttest.data.api.response;

import com.google.gson.annotations.SerializedName;

public class Image {
 @SerializedName("#text") private String url;
  private String size;

  public String getUrl() {
    return url;
  }

  public String getSize() {
    return size;
  }
}
