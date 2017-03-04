package io.bloco.lasttest.common.di;

import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import io.bloco.lasttest.AndroidApplication;
import io.bloco.lasttest.BuildConfig;
import io.bloco.lasttest.data.api.LastFmApi;
import io.bloco.lasttest.data.api.MockApiInterceptor;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module public class ApiModule {

  public static final String LAST_FM_API_URL = "http://ws.audioscrobbler.com/2.0/";

  @Provides @PerApplication
  public OkHttpClient provideOkHttpClient(AndroidApplication.Mode appMode) {
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    if (BuildConfig.DEBUG) {
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    int timeout = 60;
    OkHttpClient.Builder builder = new OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor);

    if (appMode == AndroidApplication.Mode.TEST) {
      builder.addInterceptor(new MockApiInterceptor());
    }

    return builder
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)
        .writeTimeout(timeout, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build();
  }

  @Provides @PerApplication
  public Retrofit retrofitApp(OkHttpClient client, Gson gson) {
    return new Retrofit.Builder().client(client)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(LAST_FM_API_URL)
        .build();
  }

  @Provides @PerApplication public LastFmApi lastFmApi(Retrofit retrofit) {
    return retrofit.create(LastFmApi.class);
  }
}