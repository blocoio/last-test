package io.bloco.lasttest.data.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MockApiInterceptor implements Interceptor {

  @Override
  public Response intercept(Interceptor.Chain chain) {
    Request request = chain.request();
    MockResponse mockResponse = getResponse(request);
    return new Response.Builder().request(request)
        .code(mockResponse.code)
        .protocol(Protocol.HTTP_2)
        .body(ResponseBody.create(MediaType.parse("text/json"), mockResponse.body))
        .build();
  }

  private MockResponse getResponse(Request request) {
    String requestUrl = request.url().encodedPath();
    String requestQuery = request.url().encodedQuery();

    if (requestQuery.contains("user.getinfo")) {
      return new MockResponse(loadJson("user"));
    } else if (requestQuery.contains("user.gettopartist")) {
      return new MockResponse(loadJson("artists"));
    } else if (requestQuery.contains("artist.gettoptracks")) {
      return new MockResponse(loadJson("artists"));
    } else {
      return new MockResponse("{}");
    }
  }

  private String loadJson(String fileName) {
    try {
      InputStream is =
          getClass().getClassLoader().getResourceAsStream("responses/" + fileName + ".json");
      int size = is.available();
      byte[] buffer = new byte[size];
      is.read(buffer);
      is.close();
      return new String(buffer, "UTF-8");
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  private static class MockResponse {
    int code;
    String body;

    private MockResponse(String body) {
      this(HttpURLConnection.HTTP_OK, body);
    }

    private MockResponse(int code, String body) {
      this.code = code;
      this.body = body;
    }
  }
}